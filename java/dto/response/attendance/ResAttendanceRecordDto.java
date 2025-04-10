package Offime.Offime.dto.response.attendance;

import Offime.Offime.entity.attendance.EventRecord;
import Offime.Offime.entity.attendance.OutOfOfficeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static Offime.Offime.entity.attendance.EventType.*;

@Getter
@NoArgsConstructor
public class ResAttendanceRecordDto {

    private LocalDate date;
    private LocalTime clockIn;
    private LocalTime outOfOffice;
    private OutOfOfficeType outOfOfficeType;
    private LocalTime returnToOffice;
    private LocalTime clockOut;

    @Builder
    public ResAttendanceRecordDto(LocalDate date, LocalTime clockIn, LocalTime outOfOffice,
                                  OutOfOfficeType outOfOfficeType, LocalTime returnToOffice, LocalTime clockOut) {
        this.date = date;
        this.clockIn = clockIn;
        this.outOfOffice = outOfOffice;
        this.outOfOfficeType = outOfOfficeType;
        this.returnToOffice = returnToOffice;
        this.clockOut = clockOut;
    }

    public static ResAttendanceRecordDto fromEntity(List<EventRecord> records, LocalDate date) {
        LocalTime clockIn = null;
        LocalTime outOfOffice = null;
        OutOfOfficeType outOfOfficeType = null;
        LocalTime returnToOffice = null;
        LocalTime clockOut = null;

        for (EventRecord record : records) {
            switch (record.getEventType()) {
                case 출근:
                    clockIn = LocalTime.from(record.getRequestTime());
                    break;
                case 자리비움:
                    outOfOffice = LocalTime.from(record.getRequestTime());
                    outOfOfficeType = record.getOutOfOfficeType();
                    break;
                case 복귀:
                    returnToOffice = LocalTime.from(record.getRequestTime());
                    break;
                case 퇴근:
                    clockOut = LocalTime.from(record.getRequestTime());
                    break;
            }
        }

        return ResAttendanceRecordDto.builder()
                .date(date)
                .clockIn(clockIn)
                .outOfOffice(outOfOffice)
                .outOfOfficeType(outOfOfficeType)
                .returnToOffice(returnToOffice)
                .clockOut(clockOut)
                .build();
    }
}