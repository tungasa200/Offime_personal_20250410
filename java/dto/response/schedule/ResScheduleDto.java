package Offime.Offime.dto.response.schedule;

import Offime.Offime.entity.schedule.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ResScheduleDto {
    private Long id;
    private Long memberId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime breakTime;
    private String memo;
    private String color;

    @Builder
    public ResScheduleDto(Long id, Long memberId, LocalDate date, LocalTime startTime, LocalTime endTime, LocalTime breakTime, String memo, String color) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakTime = breakTime;
        this.memo = memo;
        this.color = color;
    }

    public static ResScheduleDto fromEntity(Schedule schedule) {
        return ResScheduleDto.builder()
                .id(schedule.getId())
                .memberId(schedule.getMember().getId())
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .breakTime(schedule.getBreakTime())
                .memo(schedule.getMemo())
                .color(schedule.getColor())
                .build();
    }
}
