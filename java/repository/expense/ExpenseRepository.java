package Offime.Offime.repository.expense;

import Offime.Offime.entity.expense.Expense;
import Offime.Offime.entity.common.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUsername(String username);
    List<Expense> findByStatus(RequestStatus status);

    // 검색 기능을 위한 쿼리 메서드 (특정 상태의 경비만 검색)
    @Query("SELECT e FROM Expense e WHERE " +
            "(:searchTerm IS NULL OR " +
            "e.title LIKE %:searchTerm% OR " +
            "e.username LIKE %:searchTerm% OR " +
            "e.category LIKE %:searchTerm% OR " +
            "e.content LIKE %:searchTerm% OR " +
            "CAST(e.amount AS string) LIKE %:searchTerm% OR " +
            "CAST(e.expenseDate AS string) LIKE %:searchTerm% OR " +
            "(CASE " +
            "   WHEN e.status = 'PENDING' THEN '대기' " +
            "   WHEN e.status = 'APPROVED' THEN '승인' " +
            "   WHEN e.status = 'REJECTED' THEN '거절' " +
            "   ELSE CAST(e.status AS string) " +
            " END) LIKE %:searchTerm%)")
    List<Expense> searchExpenses(@Param("searchTerm") String searchTerm);
}