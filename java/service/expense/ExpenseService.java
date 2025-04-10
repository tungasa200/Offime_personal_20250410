package Offime.Offime.service.expense;

import Offime.Offime.entity.common.RequestStatus;
import Offime.Offime.exception.ExpenseNotFoundException;
import Offime.Offime.dto.request.expense.ExpenseRequestDTO;
import Offime.Offime.dto.response.expense.ExpenseResponseDTO;
import Offime.Offime.entity.expense.Expense;
import Offime.Offime.entity.expense.ExpenseImage;
import Offime.Offime.repository.expense.ExpenseImageRepository;
import Offime.Offime.repository.expense.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseImageRepository expenseImageRepository;

    @Value("${file.upload.path}")
    private String uploadDir;

    @Value("${file.upload.url}")
    private String serverUrl;

    public long getPendingExpensesCount() {
        List<Expense> pendingExpenses = expenseRepository.findByStatus(RequestStatus.PENDING);
        return pendingExpenses.size();
    }

    public long getRejectedExpensesCount() {
        List<Expense> rejectedExpenses = expenseRepository.findByStatus(RequestStatus.REJECTED);
        return rejectedExpenses.size();
    }


    // 게시물 검색
    public List<Expense> searchExpenses(String searchTerm) {
        return expenseRepository.searchExpenses(searchTerm);
    }
    // 대기 중인 게시물 가져오기
    public List<Expense> getPendingExpenses() {
        return expenseRepository.findByStatus(RequestStatus.PENDING);
    }

    // 게시물 승인
    public Expense approveExpense(Long id) {
        return updateExpenseStatus(id, RequestStatus.APPROVED);
    }

    // 게시물 거절
    public Expense rejectExpense(Long id) {
        return updateExpenseStatus(id, RequestStatus.REJECTED);
    }

    // 상태별 게시물 조회
    public List<Expense> getExpensesByStatus(RequestStatus status) {
        return expenseRepository.findByStatus(status);
    }

    // 게시물 저장
    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    // 게시물 생성 (이미지 포함)
    public Expense createExpense(ExpenseRequestDTO expenseDTO, List<MultipartFile> images) {
        Expense expense = new Expense();
        expense.setTitle(expenseDTO.getTitle());
        expense.setContent(expenseDTO.getContent());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setExpenseDate(expenseDTO.getExpenseDate());

        expense.setStatus(RequestStatus.PENDING);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        expense.setUsername(username);

        Expense savedExpense = expenseRepository.save(expense);

        // 이미지가 있으면 저장
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String imageUrl = saveImage(image);
                ExpenseImage expenseImage = new ExpenseImage();
                expenseImage.setImageUrl(imageUrl);
                expenseImage.setExpense(savedExpense);
                expenseImageRepository.save(expenseImage);
            }
        }

        return savedExpense;
    }

    // 이미지 저장
    private String saveImage(MultipartFile image) {
        try {
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists() && !uploadDirFile.mkdirs()) {
                logger.error("이미지 업로드 디렉토리 생성 실패: {}", uploadDirFile.getPath());
                throw new RuntimeException("이미지 업로드 디렉토리 생성 실패");
            }

            String fileName = System.currentTimeMillis() + "-" + Math.random() + "-" + image.getOriginalFilename();
            File uploadFile = new File(uploadDirFile, fileName);

            image.transferTo(uploadFile);

            return serverUrl + "/" + fileName;
        } catch (IOException e) {
            logger.error("이미지 업로드 실패: {}", e.getMessage());
            throw new RuntimeException("Image upload failed: " + e.getMessage());
        }
    }

    // 게시물 조회 (작성자 또는 관리자만 접근 가능)
    public Expense getExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!expense.getUsername().equals(authentication.getName()) && !isAdmin) {
            throw new org.springframework.security.access.AccessDeniedException("You do not have permission to access this expense.");
        }

        return expense;
    }

    // 게시물 목록 조회 (관리자는 전체, 일반 사용자는 자신의 게시물만)
    public List<Expense> getExpenses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return expenseRepository.findAll();
        } else {
            String currentUsername = authentication.getName();
            return expenseRepository.findByUsername(currentUsername);
        }
    }

    // 게시물 DTO 변환
    private ExpenseResponseDTO convertToResponseDTO(Expense expense) {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();
        responseDTO.setId(expense.getId());
        responseDTO.setTitle(expense.getTitle());
        responseDTO.setContent(expense.getContent());
        responseDTO.setUsername(expense.getUsername());
        responseDTO.setAmount(expense.getAmount());
        responseDTO.setCategory(expense.getCategory());

        List<String> imageUrls = expenseImageRepository.findByExpenseId(expense.getId()).stream()
                .map(ExpenseImage::getImageUrl)
                .collect(Collectors.toList());
        responseDTO.setImageUrls(imageUrls);

        return responseDTO;
    }

    // 게시물 수정
    @Transactional
    public Expense updateExpense(Long id, ExpenseRequestDTO expenseDTO, List<MultipartFile> images) {
        logger.info("Updating expense with id: {}", id);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + id));

        expense.setTitle(expenseDTO.getTitle());
        expense.setContent(expenseDTO.getContent());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setExpenseDate(expenseDTO.getExpenseDate());

        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String imageUrl = saveImage(image);
                ExpenseImage expenseImage = new ExpenseImage();
                expenseImage.setImageUrl(imageUrl);
                expenseImage.setExpense(expense);
                expenseImageRepository.save(expenseImage);
            }
        }

        return expenseRepository.save(expense);
    }

    // 게시물 삭제 (관리자만 가능)
    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new org.springframework.security.access.AccessDeniedException("Only administrators have permission to delete expenses.");
        }

        expenseRepository.delete(expense);
    }

    // 이미지 삭제
    @Transactional
    public void deleteImage(Long imageId) {
        logger.info("Deleting image with id: {}", imageId);
        ExpenseImage expenseImage = expenseImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        String imageUrl = expenseImage.getImageUrl();
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        File imageFile = new File(uploadDir, fileName);

        if (imageFile.exists() && !imageFile.delete()) {
            logger.error("이미지 삭제 실패: {}", imageFile.getAbsolutePath());
            throw new RuntimeException("이미지 파일 삭제 실패");
        }

        expenseImageRepository.delete(expenseImage);
    }

    // 상태 업데이트 (공통 로직)
    private Expense updateExpenseStatus(Long id, RequestStatus status) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setStatus(status);
        return expenseRepository.save(expense);
    }
}
