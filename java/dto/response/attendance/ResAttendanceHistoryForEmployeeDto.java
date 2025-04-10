package Offime.Offime.dto.response.attendance;

import Offime.Offime.entity.attendance.EventRecord;
import Offime.Offime.entity.attendance.EventType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ResAttendanceHistoryForEmployeeDto {

    private int clockInCount;
    private int absentCount;
    private int lateCount;
    private int totalLateMinutes;
    private int leaveEarlyCount;
    private int totalLeaveEarlyMinutes;
    private int totalWeekdays;

    @Builder
    public ResAttendanceHistoryForEmployeeDto(
            int clockInCount, int absentCount, int lateCount, int totalLateMinutes,
            int leaveEarlyCount, int totalLeaveEarlyMinutes, int totalWeekdays) {
        this.clockInCount = clockInCount;
        this.absentCount = absentCount;
        this.lateCount = lateCount;
        this.totalLateMinutes = totalLateMinutes;
        this.leaveEarlyCount = leaveEarlyCount;
        this.totalLeaveEarlyMinutes = totalLeaveEarlyMinutes;
        this.totalWeekdays = totalWeekdays;
    }

    public static ResAttendanceHistoryForEmployeeDto fromEntity(List<EventRecord> eventRecord, List<LocalDate> weekdays, LocalDate today) {
        List<LocalDate> clockInDates = eventRecord.stream()
                .filter(r -> r.getEventType() == EventType.출근)
                .map(EventRecord::getDate)
                .toList();

        long absentCount = weekdays.stream()
                .filter(weekday -> !clockInDates.contains(weekday) && !weekday.isAfter(today))
                .count();

        return ResAttendanceHistoryForEmployeeDto.builder()
                .clockInCount(clockInDates.size())
                .absentCount((int) absentCount)
                .lateCount((int) eventRecord.stream()
                        .filter(r -> r.getEventType() == EventType.출근)
                        .filter(r -> r.getLate() > 0)
                        .count())
                .totalLateMinutes(eventRecord.stream()
                        .filter(r -> r.getEventType() == EventType.출근)
                        .mapToInt(EventRecord::getLate)
                        .sum())
                .leaveEarlyCount((int) eventRecord.stream()
                        .filter(r -> r.getEventType() == EventType.퇴근)
                        .filter(r -> r.getLeaveEarly() > 0)
                        .count())
                .totalLeaveEarlyMinutes(eventRecord.stream()
                        .filter(r -> r.getEventType() == EventType.퇴근)
                        .mapToInt(EventRecord::getLeaveEarly)
                        .sum())
                .totalWeekdays(weekdays.size())
                .build();
    }
}