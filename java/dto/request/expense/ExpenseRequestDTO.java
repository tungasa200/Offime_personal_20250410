package Offime.Offime.dto.request.expense;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ExpenseRequestDTO {


    private String title;
    private String content;
    private double amount;
    private String category;
    private List<MultipartFile> images;  // 이미지 파일들
    private LocalDate expenseDate;
    private String status;

}