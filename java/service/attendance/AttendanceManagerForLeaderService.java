package Offime.Offime.service.attendance;

import Offime.Offime.dto.response.attendance.ResAttendanceHistoryForLeaderDto;
import Offime.Offime.entity.attendance.EventRecord;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.member.Team;
import Offime.Offime.repository.attendance.EventRecordRepository;
import Offime.Offime.repository.member.MemberRepository;
import Offime.Offime.repository.vacation.VacationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceManagerForLeaderService {

    private final EventRecordRepository eventRecordRepository;
    private final MemberRepository memberRepository;
    private final VacationRepository vacationRepository;

    private static final LocalTime COMPANY_START_TIME = LocalTime.of(9, 0);

    public boolean isOnVacation(Member member, LocalDate date) {
        return vacationRepository.existsVacationOverlap(date, date, member);
    }

    public ResAttendanceHistoryForLeaderDto getDailyAttendanceForTeam(LocalDate date, Team team) {
        List<EventRecord> records = getDailyRecordsForTeam(date, team);
        int absentPersonnelCount = 0; // 미출근 카운트
        int notYetArrivedCount = 0; // 출근 전 카운트
        List<Member> teamMembers = memberRepository.findByTeam(team);

        for (Member member : teamMembers) {
            int status = updateAttendanceStatus(member, date);
            if (status == -1) {
                notYetArrivedCount++;
            } else if (status == 1) {
                absentPersonnelCount++;
            }
        }

        int workdayPersonnel = teamMembers.size() - absentPersonnelCount; // 팀 멤버 수에서 미출근 인원 제외
        return ResAttendanceHistoryForLeaderDto.fromEntity(records, workdayPersonnel, absentPersonnelCount, date);
    }

    public ResAttendanceHistoryForLeaderDto getDailyAttendanceForAll(LocalDate date) {
        List<EventRecord> records = getDailyRecordsForAll(date);
        int absentPersonnelCount = 0; // 미출근 카운트
        int notYetArrivedCount = 0; // 출근 전 카운트
        List<Member> allMembers = memberRepository.findAll();

        for (Member member : allMembers) {
            int status = updateAttendanceStatus(member, date);
            if (status == -1) {
                notYetArrivedCount++;
            } else if (status == 1) {
                absentPersonnelCount++;
            }
        }

        int workdayPersonnel = allMembers.size() - absentPersonnelCount; // 전체 직원 수에서 미출근 인원 제외
        return ResAttendanceHistoryForLeaderDto.fromEntity(records, workdayPersonnel, absentPersonnelCount, date);
    }

    private int updateAttendanceStatus(Member member, LocalDate date) {
        boolean hasAttendanceRecord = eventRecordRepository.existsByMemberAndDate(member, date);

        if (isOnVacation(member, date)) {
            return 1;
        } else if (!hasAttendanceRecord) {
            if (date.isEqual(LocalDate.now())) { // 오늘 날짜인 경우
                if (LocalTime.now().isBefore(COMPANY_START_TIME)) { // 회사 시작 시간 이전
                    return -1; // 출근 전 상태
                } else {
                    return 1; // 미출근
                }
            } else { // 지난 날짜인 경우
                return 1; // 지난 날짜는 모두 미출근으로 처리
            }
        }
        return 0; // 출근 기록이 있는 경우
    }

    public long getTotalEmployeeCount() {
        return memberRepository.count();
    }

    public long getEmployeeCountByTeam(Team team) {
        return memberRepository.countByTeam(team);
    }

    private List<EventRecord> getDailyRecordsForAll(LocalDate date) {
        return eventRecordRepository.findByDate(date);
    }

    private List<EventRecord> getDailyRecordsForTeam(LocalDate date, Team team) {
        return eventRecordRepository.findByDateAndTeam(date, team);
    }
}