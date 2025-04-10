package Offime.Offime.repository.schedule;

import Offime.Offime.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.member.id = :memberId AND FUNCTION('YEAR', s.date) = :year AND FUNCTION('MONTH', s.date) = :month")
    List<Schedule> findByMemberIdAndYearAndMonth(@Param("memberId") Long memberId,@Param("year") Integer year,  @Param("month") Integer month);
}
