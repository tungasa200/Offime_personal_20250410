package Offime.Offime.dto.request.attendance;

import Offime.Offime.entity.attendance.EventRecord;
import Offime.Offime.entity.attendance.EventType;
import Offime.Offime.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReqReturnToOfficeDto {

    private double latitude;
    private double longitude;

    public static EventRecord toEntity(Member member, EventRecord clockInRecord) {
        return EventRecord.builder()
                .member(member)
                .date(LocalDate.now())
                .requestTime(LocalDateTime.now())
                .clockIn(clockInRecord.getClockIn())
                .eventType(EventType.복귀)
                .late(clockInRecord.getLate())
                .team(member.getTeam())
                .build();
    }
}