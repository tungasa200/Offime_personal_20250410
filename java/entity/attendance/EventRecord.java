package Offime.Offime.entity.attendance;

import Offime.Offime.common.LocalDateTimeConverter;
import Offime.Offime.common.LocalTimeConverter;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.member.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "EVENT_RECORD")
public class EventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "REQUEST_TIME", nullable = false, columnDefinition = "DATETIME(0)")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime requestTime;

    @Column(name = "CLOCK_IN", columnDefinition = "TIME(0)")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime clockIn;

    @Column(name = "CLOCK_OUT", columnDefinition = "TIME(0)")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime clockOut;

    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT_TYPE", nullable = false)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "OUT_OF_OFFICE_TYPE")
    private OutOfOfficeType outOfOfficeType;

    @Column(name = "LATE")
    private int late;

    @Column(name = "LEAVE_EARLY")
    private int leaveEarly;

    @Column(name = "TEAM")
    @Enumerated(EnumType.STRING)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Builder
    public EventRecord(Long id, LocalDate date, LocalDateTime requestTime, LocalTime clockIn, LocalTime clockOut, EventType eventType,
                       OutOfOfficeType outOfOfficeType, int late, int leaveEarly, Team team, Member member) {
        this.id = id;
        this.date = date;
        this.requestTime = requestTime;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.eventType = eventType;
        this.outOfOfficeType = outOfOfficeType;
        this.late = late;
        this.leaveEarly = leaveEarly;
        this.team = team;
        this.member = member;
    }

    public void updateClockOut(LocalTime clockOut) {
        this.clockOut = clockOut;
    }

    public void updateLeaveEarly(int leaveEarly) {
        this.leaveEarly = leaveEarly;
    }
}