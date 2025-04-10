package Offime.Offime.repository.attendance;

import Offime.Offime.entity.attendance.EventRecord;
import Offime.Offime.entity.attendance.EventType;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.member.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface EventRecordRepository extends JpaRepository<EventRecord, Long> {

    Optional<EventRecord> findByMemberIdAndDateAndEventType(Long memberId, LocalDate date, EventType eventType);

    List<EventRecord> findByMemberAndDateBetween(Member member, LocalDate startDate, LocalDate endDate);

    List<EventRecord> findByMemberAndDate(Member member, LocalDate date);

    List<EventRecord> findByDate(LocalDate date);

    List<EventRecord> findByDateAndTeam(LocalDate date, Team team);

    boolean existsByMemberAndDate(Member member, LocalDate today);
}