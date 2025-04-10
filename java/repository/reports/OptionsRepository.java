package Offime.Offime.repository.reports;

import Offime.Offime.entity.reports.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionsRepository extends JpaRepository<Options,Long> {
    @Query("SELECT o.optionText FROM Options o WHERE o.question.id = :questionId")
    List<String> getAllOptionTextByQuestionId(@Param("questionId") Long questionId);

}
