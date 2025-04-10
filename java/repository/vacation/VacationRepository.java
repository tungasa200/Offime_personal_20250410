package Offime.Offime.repository.vacation;

import Offime.Offime.dto.response.vacation.ResVacation;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.vacation.Vacation;
import Offime.Offime.entity.vacation.VacationApprovalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

    Page<Vacation> findAllByMember(Member member, Pageable pageable);


    Vacation findByMemberAndId(Member member, Long id);

    @Query("SELECT COUNT(v) > 0 FROM Vacation v WHERE v.member = :member " +
            "AND ((v.startDate <= :endDate AND v.endDate >= :startDate))")
    boolean existsVacationOverlap(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("member") Member member);

    List<Vacation> findTop5ByMemberOrderByIdDesc(Member member);

    Page<Vacation> findAllByStatus(VacationApprovalStatus status, Pageable pageable);
}
