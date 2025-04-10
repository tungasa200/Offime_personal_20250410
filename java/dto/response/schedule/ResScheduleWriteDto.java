package Offime.Offime.dto.response.schedule;

import Offime.Offime.entity.schedule.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ResScheduleWriteDto {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime breakTime;
    private String memo;
    private String color;

    @Builder
    public ResScheduleWriteDto(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, LocalTime breakTime, String memo, String color) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakTime = breakTime;
        this.memo = memo;
        this.color = color;
    }

    public static ResScheduleWriteDto fromEntity(Schedule savedSchedule) {
        return ResScheduleWriteDto.builder()
                .id(savedSchedule.getId())
                .date(savedSchedule.getDate())
                .startTime(savedSchedule.getStartTime())
                .endTime(savedSchedule.getEndTime())
                .breakTime(savedSchedule.getBreakTime())
                .memo(savedSchedule.getMemo())
                .color(savedSchedule.getColor())
                .build();
    }
}
