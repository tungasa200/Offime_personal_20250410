package Offime.Offime.entity.vacation;

import Offime.Offime.common.BaseTimeEntity;
import Offime.Offime.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vacation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private VacationType type;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    @Enumerated(EnumType.STRING)
    @Setter
    private VacationApprovalStatus status;
}
