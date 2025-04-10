package Offime.Offime.repository.reports;

import Offime.Offime.entity.reports.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplatesRepository extends JpaRepository<Templates, Long> {
}
