package Offime.Offime.controller.schedule;

import Offime.Offime.dto.request.schedule.ScheduleUpdateDto;
import Offime.Offime.dto.request.schedule.ScheduleWriteDto;
import Offime.Offime.dto.response.schedule.ResScheduleDto;
import Offime.Offime.dto.response.schedule.ResScheduleWriteDto;
import Offime.Offime.entity.member.Member;
import Offime.Offime.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/list")
    public ResponseEntity<List<ResScheduleDto>> getSchedules (@AuthenticationPrincipal Member member,@RequestParam Integer year, @RequestParam Integer month) {
        Long memberId = member.getId();
        List<ResScheduleDto> listDto = scheduleService.getScheduleByMemberIdAndYearAndMonth(memberId,year,month);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @PostMapping("/write")
    public ResponseEntity<ResScheduleWriteDto> writeSchedule (@RequestBody ScheduleWriteDto dto) {
        ResScheduleWriteDto savedDto = scheduleService.write(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<ResScheduleDto> updateSchedule (@RequestBody ScheduleUpdateDto dto) {
        Long id = dto.getId();
        ResScheduleDto updateDto = scheduleService.update(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSchedule (@RequestParam Long id) {
        scheduleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Schedule deleted");
    }
}
