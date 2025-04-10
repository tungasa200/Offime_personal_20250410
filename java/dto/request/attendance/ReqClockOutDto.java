package Offime.Offime.dto.request.attendance;

import Offime.Offime.entity.attendance.EventRecord;
import Offime.Offime.entity.attendance.EventType;
import Offime.Offime.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ReqClockOutDto {

    public static EventRecord toEntity(Member member, EventRecord clockInRecord, int leaveEarly) {
        return EventRecord.builder()
                .member(member)
                .date(LocalDate.now())
                .requestTime(LocalDateTime.now())
                .clockIn(clockInRecord.getClockIn())
                .clockOut(LocalTime.now())
                .eventType(EventType.퇴근)
                .late(clockInRecord.getLate())
                .leaveEarly(leaveEarly)
                .team(member.getTeam())
                .build();
    }
}