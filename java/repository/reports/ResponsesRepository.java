package Offime.Offime.repository.reports;

import Offime.Offime.entity.reports.Questions;
import Offime.Offime.entity.reports.Responses;
import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponsesRepository extends JpaRepository<Responses,Long> {
    List<Responses> findAllByQuestion(Questions question);

    Optional<Responses> findByQuestionIdAndReportId(Long questionId, Long reportId);
}
