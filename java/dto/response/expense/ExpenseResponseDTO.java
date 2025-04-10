package Offime.Offime.dto.response.expense;

import Offime.Offime.entity.expense.Expense;
import Offime.Offime.entity.expense.ExpenseImage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ExpenseResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String username;
    private double amount;
    private String category;
    private LocalDate expenseDate;
    private List<String> imageUrls;  // 이미지는 URL로 응답
    private String status;

    public static ExpenseResponseDTO fromEntity(Expense expense, List<ExpenseImage> expenseImages) {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();
        responseDTO.setId(expense.getId());
        responseDTO.setTitle(expense.getTitle());
        responseDTO.setContent(expense.getContent());
        responseDTO.setUsername(expense.getUsername());
        responseDTO.setAmount(expense.getAmount());
        responseDTO.setCategory(expense.getCategory());
        responseDTO.setStatus(expense.getStatus().toString());
        responseDTO.setExpenseDate(expense.getExpenseDate());
        responseDTO.setImageUrls(expenseImages.stream()
                .map(ExpenseImage::getImageUrl)
                .collect(Collectors.toList()));
        return responseDTO;
    }

}
