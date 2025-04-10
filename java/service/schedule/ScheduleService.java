package Offime.Offime.service.schedule;

import Offime.Offime.dto.request.schedule.ScheduleUpdateDto;
import Offime.Offime.dto.request.schedule.ScheduleWriteDto;
import Offime.Offime.dto.response.schedule.ResScheduleDto;
import Offime.Offime.dto.response.schedule.ResScheduleWriteDto;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.schedule.Schedule;
import Offime.Offime.repository.member.MemberRepository;
import Offime.Offime.repository.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    public List<ResScheduleDto> getScheduleByMemberIdAndYearAndMonth(Long memberId,Integer year, Integer month) {
        List<Schedule> schedules = scheduleRepository.findByMemberIdAndYearAndMonth(memberId, year, month);
        List<ResScheduleDto> ListDto = schedules.stream()
                .map(ResScheduleDto::fromEntity)
                .collect(Collectors.toList());
        return ListDto;
    }

    public ResScheduleWriteDto write(ScheduleWriteDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(()-> new IllegalArgumentException("Member not found"));
        Schedule schedule = ScheduleWriteDto.ofEntity(member,dto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ResScheduleWriteDto.fromEntity(savedSchedule);
    }

    public ResScheduleDto update(Long id, ScheduleUpdateDto dto) {
        Schedule updateSchedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Schedule not found")
        );
        updateSchedule.update(dto);
        return ResScheduleDto.fromEntity(updateSchedule);
    }
    public void delete(Long id) { scheduleRepository.deleteById(id); }
}
