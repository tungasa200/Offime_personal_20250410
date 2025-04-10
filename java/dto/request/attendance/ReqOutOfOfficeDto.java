package Offime.Offime.dto.request.attendance;

import Offime.Offime.entity.attendance.EventRecord;
import Offime.Offime.entity.attendance.EventType;
import Offime.Offime.entity.attendance.OutOfOfficeType;
import Offime.Offime.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReqOutOfOfficeDto {

    private OutOfOfficeType outOfOfficeType;

    public static EventRecord toEntity(Member member, ReqOutOfOfficeDto dto, EventRecord clockInRecord) {
        return EventRecord.builder()
                .member(member)
                .date(LocalDate.now())
                .requestTime(LocalDateTime.now())
                .clockIn(clockInRecord.getClockIn())
                .eventType(EventType.자리비움)
                .outOfOfficeType(dto.outOfOfficeType)
                .late(clockInRecord.getLate())
                .team(member.getTeam())
                .build();
    }
}