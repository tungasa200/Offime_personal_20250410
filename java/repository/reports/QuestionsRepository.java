package Offime.Offime.repository.reports;

import Offime.Offime.entity.reports.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions,Long> {
    List<Questions> getAllByTemplateId(Long templateId);
}
