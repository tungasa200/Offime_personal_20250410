package Offime.Offime.dto.request.schedule;

import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.schedule.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleWriteDto {
    private Long id;
    private Long memberId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime breakTime;
    private String memo;
    private String color;

    @Builder
    public ScheduleWriteDto(Long id, Long memberId, LocalDate date, LocalTime startTime, LocalTime endTime, LocalTime breakTime, String memo, String color) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakTime = breakTime;
        this.memo = memo;
        this.color = color;
    }

    public static Schedule ofEntity(Member member, ScheduleWriteDto dto) {
        return Schedule.builder()
                .id(dto.getId())
                .member(member)
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .breakTime(dto.getBreakTime())
                .memo(dto.getMemo())
                .color(dto.getColor())
                .build();
    }
}
