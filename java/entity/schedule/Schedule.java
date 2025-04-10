package Offime.Offime.entity.schedule;

import Offime.Offime.common.BaseTimeEntity;
import Offime.Offime.dto.request.schedule.ScheduleUpdateDto;
import Offime.Offime.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_SCHEDULE_MEMBER"))
    private Member member;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "START_TIME", nullable = false)
    private LocalTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalTime endTime;

    @Column(name = "BREAK_TIME", nullable = false)
    private LocalTime breakTime;

    @Column(name = "MEMO", columnDefinition = "TEXT")
    private String memo;

    @Column(name = "COLOR", nullable = false)
    private String color;

    @Builder
    public Schedule(Long id, Member member, LocalDate date, LocalTime startTime, LocalTime endTime, LocalTime breakTime, String memo, String color) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakTime = breakTime;
        this.memo = memo;
        this.color = color;
    }


    public void update(ScheduleUpdateDto dto) {
        this.date = dto.getDate();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.breakTime = dto.getBreakTime();
        this.memo = dto.getMemo();
        this.color = dto.getColor();
    }
}
