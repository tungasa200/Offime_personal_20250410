package Offime.Offime.repository.expense;

import Offime.Offime.entity.expense.ExpenseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ExpenseImageRepository extends JpaRepository<ExpenseImage, Long> {
    // 기존의 다른 메서드들

    // 이미지 URL로 ExpenseImage 삭제
    @Transactional
    void deleteByImageUrl(String imageUrl);

    // expenseId로 ExpenseImage 목록 조회
    List<ExpenseImage> findByExpenseId(Long expenseId);
}
