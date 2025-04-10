package Offime.Offime.entity.expense;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Entity
@ToString(exclude = "expense")
public class ExpenseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // 수정된 부분
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    public ExpenseImage(String imageUrl, Expense expense) {
        this.imageUrl = imageUrl;
        this.expense = expense;
    }
}