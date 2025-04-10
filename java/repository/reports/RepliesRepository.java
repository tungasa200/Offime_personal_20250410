package Offime.Offime.repository.reports;

import Offime.Offime.entity.reports.Replies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RepliesRepository extends JpaRepository<Replies,Long> {
    List<Replies> findAllByReportId(Long reportId);
}
