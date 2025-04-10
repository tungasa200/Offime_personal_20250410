package Offime.Offime.controller.attendance;

import Offime.Offime.dto.response.attendance.ResAttendanceHistoryForEmployeeDto;
import Offime.Offime.dto.response.attendance.ResAttendanceRecordDto;
import Offime.Offime.entity.member.Member;
import Offime.Offime.service.attendance.AttendanceManagerForEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class AttendanceManagerForEmployeeController {

    private final AttendanceManagerForEmployeeService employeeAttendanceManagerService;

    @GetMapping("/daily")
    public ResponseEntity<ResAttendanceRecordDto> getDailyAttendanceRecord(
            @AuthenticationPrincipal Member member,
            @RequestParam LocalDate date) {
        ResAttendanceRecordDto records = employeeAttendanceManagerService.getDailyAttendanceRecord(
                member, date);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/weekly")
    public ResponseEntity<ResAttendanceHistoryForEmployeeDto> getWeeklyAttendance(
            @AuthenticationPrincipal Member member,
            @RequestParam LocalDate date) {
        ResAttendanceHistoryForEmployeeDto weeklyStats = employeeAttendanceManagerService.getWeeklyAttendanceHistory(
                member, date);
        return ResponseEntity.ok(weeklyStats);
    }

    @GetMapping("/monthly")
    public ResponseEntity<ResAttendanceHistoryForEmployeeDto> getMonthlyAttendance(
            @AuthenticationPrincipal Member member,
            @RequestParam int year,
            @RequestParam int month) {

        ResAttendanceHistoryForEmployeeDto monthlyStats = employeeAttendanceManagerService.getMonthlyAttendanceHistory(
                member, year, month);
        return ResponseEntity.ok(monthlyStats);
    }
}