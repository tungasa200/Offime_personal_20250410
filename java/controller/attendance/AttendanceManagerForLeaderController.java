package Offime.Offime.controller.attendance;

import Offime.Offime.dto.response.attendance.ResAttendanceHistoryForLeaderDto;
import Offime.Offime.entity.member.Team;
import Offime.Offime.service.attendance.AttendanceManagerForLeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class AttendanceManagerForLeaderController {

    private final AttendanceManagerForLeaderService attendanceManagerForLeaderService;

    @GetMapping("/forAll")
    public ResponseEntity<ResAttendanceHistoryForLeaderDto> getDailyAttendanceForAll(
            @RequestParam LocalDate date) {
        ResAttendanceHistoryForLeaderDto statsForAll = attendanceManagerForLeaderService.getDailyAttendanceForAll(date);
        return ResponseEntity.ok(statsForAll);
    }

    @GetMapping("/forTeam")
    public ResponseEntity<ResAttendanceHistoryForLeaderDto> getDailyAttendanceForTeam(
            @RequestParam LocalDate date,
            @RequestParam Team team) {
        ResAttendanceHistoryForLeaderDto statsForTeam = attendanceManagerForLeaderService.getDailyAttendanceForTeam(date, team);
        return ResponseEntity.ok(statsForTeam);
    }

    // 전체 직원 수 조회
    @GetMapping("total")
    public long getTotalEmployees() {
        return attendanceManagerForLeaderService.getTotalEmployeeCount();
    }

    // 팀별 직원 수 조회
    @GetMapping("totalByTeam")
    public long getEmployeesByTeam(@RequestParam Team team) {
        return attendanceManagerForLeaderService.getEmployeeCountByTeam(team);
    }
}