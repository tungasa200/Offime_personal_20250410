package Offime.Offime.service.attendance;

import Offime.Offime.dto.request.attendance.ReqClockInDto;
import Offime.Offime.dto.request.attendance.ReqClockOutDto;
import Offime.Offime.dto.request.attendance.ReqOutOfOfficeDto;
import Offime.Offime.dto.request.attendance.ReqReturnToOfficeDto;
import Offime.Offime.entity.attendance.EventRecord;
import Offime.Offime.entity.member.WorkStatus;
import Offime.Offime.entity.member.Member;
import Offime.Offime.exception.MemberException;
import Offime.Offime.repository.attendance.EventRecordRepository;
import Offime.Offime.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static Offime.Offime.entity.attendance.EventType.*;
import static java.time.LocalTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final EventRecordRepository eventRecordRepository;
    private final MemberRepository memberRepository;

    private static final double EARTH_RADIUS = 6371.0088;       // 지구 평균 반지름(km)
    private static final double COMPANY_LATITUDE = 37.482175;   // 구트 아카데미 위도
    private static final double COMPANY_LONGITUDE = 126.898233; // 구트 아카데미 경도
    private static final int MAX_DISTANCE = 100000;                 // 허용할 최대 거리(m)
    private static final LocalTime COMPANY_START_TIME = LocalTime.of(9, 0);
    private static final LocalTime COMPANY_END_TIME = LocalTime.of(18, 0);

    @Transactional
    public void clockIn(Member member, ReqClockInDto dto, LocalDateTime now) {
        if (!isInDistance(dto.getLatitude(), dto.getLongitude())) {
            log.info("clockIn dto.getLatitude() = " + dto.getLatitude());
            log.info("clockIn dto.getLongitude() = " + dto.getLongitude());
            throw new IllegalArgumentException(" - " + "허용 범위를 벗어났습니다.");
        }
        Member currentMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new MemberException("확인된 사용자가 아닙니다.", HttpStatus.BAD_REQUEST)
        );
        int lateMinutes = late(now);
        EventRecord eventRecord = ReqClockInDto.toEntity(currentMember, lateMinutes);
        eventRecordRepository.save(eventRecord);
        currentMember.updateWorkStatus(WorkStatus.근무중);
    }

    @Transactional
    public void outOfOffice(Member member, ReqOutOfOfficeDto dto, LocalDateTime now) {
        Member currentMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new MemberException("확인된 사용자가 아닙니다.", HttpStatus.BAD_REQUEST)
        );
        LocalDate today = now.toLocalDate();
        EventRecord clockInRecord = eventRecordRepository.findByMemberIdAndDateAndEventType(member.getId(), today, 출근)
                .orElseThrow(() -> new IllegalStateException("오늘의 출근 기록이 없습니다."));

        EventRecord eventRecord = ReqOutOfOfficeDto.toEntity(currentMember, dto, clockInRecord);
        eventRecordRepository.save(eventRecord);
        currentMember.updateWorkStatus(WorkStatus.자리비움중);
    }

    @Transactional
    public void returnToOffice(Member member, ReqReturnToOfficeDto dto, LocalDateTime now) {
        if (!isInDistance(dto.getLatitude(), dto.getLongitude())) {
            throw new IllegalArgumentException(" - " + "허용 범위를 벗어났습니다.");
        }
        Member currentMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new MemberException("확인된 사용자가 아닙니다.", HttpStatus.BAD_REQUEST)
        );
        LocalDate today = now.toLocalDate();
        EventRecord clockInRecord = eventRecordRepository.findByMemberIdAndDateAndEventType(member.getId(), today, 출근)
                .orElseThrow(() -> new IllegalStateException("오늘의 출근 기록이 없습니다."));
        EventRecord eventRecord = ReqReturnToOfficeDto.toEntity(currentMember, clockInRecord);
        eventRecordRepository.save(eventRecord);
        currentMember.updateWorkStatus(WorkStatus.근무중);
    }

    @Transactional
    public void clockOut(Member member, ReqClockOutDto dto, LocalDateTime now) {
        Member currentMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new MemberException("확인된 사용자가 아닙니다.", HttpStatus.BAD_REQUEST)
        );
        LocalDate today = now.toLocalDate();
        EventRecord clockInRecord = eventRecordRepository.findByMemberIdAndDateAndEventType(member.getId(), today, 출근)
                .orElseThrow(() -> new IllegalStateException("오늘의 출근 기록이 없습니다."));

        int leaveEarlyMinutes = leaveEarly(now);
        EventRecord eventRecord = ReqClockOutDto.toEntity(currentMember, clockInRecord, leaveEarlyMinutes);

        eventRecordRepository.findByMemberIdAndDateAndEventType(member.getId(), today, 출근)
                .ifPresent(record -> {
                    record.updateClockOut(now());
                    record.updateLeaveEarly(leaveEarly(now));
                });
        eventRecordRepository.findByMemberIdAndDateAndEventType(member.getId(), today, 자리비움)
                .ifPresent(record -> {
                    record.updateClockOut(now());
                    record.updateLeaveEarly(leaveEarly(now));
                });
        eventRecordRepository.findByMemberIdAndDateAndEventType(member.getId(), today, 복귀)
                .ifPresent(record -> {
                    record.updateClockOut(now());
                    record.updateLeaveEarly(leaveEarly(now));
                });
        eventRecordRepository.save(eventRecord);
        currentMember.updateWorkStatus(WorkStatus.퇴근);
    }

    //private 메소드=========================================================================================
    private int late(LocalDateTime clockInTime) {
        LocalTime Time = clockInTime.toLocalTime();
        if (Time.isAfter(COMPANY_START_TIME)) {
            Duration duration = Duration.between(COMPANY_START_TIME, Time);
            return (int) duration.toMinutes();
        }
        return 0;
    }

    private int leaveEarly(LocalDateTime clockOutTime) {
        LocalTime Time = clockOutTime.toLocalTime();
        if (Time.isBefore(COMPANY_END_TIME)) {
            Duration duration = Duration.between(Time, COMPANY_END_TIME);
            return (int) duration.toMinutes();
        }
        return 0;
    }

    private boolean isInDistance(double latitude, double longitude) {
        double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, latitude, longitude);
        return distance <= MAX_DISTANCE;
    }

    // 하버사인 공식 - 구면 위 두 개의 좌표(위도, 경도) 사이의 거리를 구하는 메소드
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도 차이를 라디안으로 변환
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        // 하버사인 공식 적용
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        // 중심각 계산
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // 최종 거리 계산
        double distance = EARTH_RADIUS * c * 1000; // 미터로 변환
        log.info("거리 : "  + String.format("%.3f", distance) + "미터.");
        return distance;
    }
}