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
public class ReqClockInDto {

    private double latitude;
    private double longitude;

    public static EventRecord toEntity(Member member, int late) {
        return EventRecord.builder()
                .member(member)
                .date(LocalDate.now())
                .requestTime(LocalDateTime.now())
                .clockIn(LocalTime.now())
                .eventType(EventType.출근)
                .late(late)
                .team(member.getTeam())
                .build();
    }
}