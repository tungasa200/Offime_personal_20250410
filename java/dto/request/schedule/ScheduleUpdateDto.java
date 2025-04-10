package Offime.Offime.dto.request.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ScheduleUpdateDto {
    private Long id;
    private Long memberId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime breakTime;
    private String memo;
    private String color;

    @Builder
    public ScheduleUpdateDto(Long id, Long memberId, LocalDate date, LocalTime startTime, LocalTime endTime, LocalTime breakTime, String memo, String color) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakTime = breakTime;
        this.memo = memo;
        this.color = color;
    }
}
