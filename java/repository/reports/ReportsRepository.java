package Offime.Offime.repository.reports;

import Offime.Offime.entity.reports.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Long> {
}
