//package Offime.Offime.config.rabbitMQ;
//
//import Offime.Offime.dto.request.vacation.ReqVacation;
//import Offime.Offime.entity.member.Member;
//import Offime.Offime.entity.vacation.Vacation;
////import Offime.Offime.exception.MemberException;
//import Offime.Offime.repository.member.MemberRepository;
//import Offime.Offime.repository.vacation.VacationRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//
//@Slf4j
//public class MessageConvertor {
//
//        // 휴가 신청 메시지 생성
//        public static String convertApplyVacationMessage(Long memberId, ReqVacation reqVacation,
//                        MemberRepository memberRepository) {
//                Member member = memberRepository.findById(memberId)
//                                .orElseThrow(() -> new MemberException("해당 멤버가 없습니다.", HttpStatus.BAD_REQUEST)); // 예외
//                                                                                                                 // 처리
//
//                String memberName = member.getName();
//
//                // 날짜 포맷팅
//                String formattedDates = dateFormatting(reqVacation.startDate(), reqVacation.endDate());
//
//                // 최종 메시지 생성
//                log.info("{}의 {} 휴가 신청 건이 있습니다.", memberName, formattedDates);
//                return memberName + "님의 " + formattedDates + " 의 휴가 신청 건이 있습니다.";
//        }
//
//        // 휴가 승인 메시지 생성
//        public static String convertApproveVacationMessage(Vacation vacation) {
//                Member member = vacation.getMember();
//                String memberName = member.getName();
//
//                // 날짜 포맷팅
//                String formattedDates = dateFormatting(vacation.getStartDate(), vacation.getEndDate());
//
//                // 최종 메시지 생성
//                log.info("{}의 {} 휴가가 승인 되었습니다.", memberName, formattedDates);
//                return memberName + "님의 " + formattedDates + " 의 휴가가 승인 되었습니다.";
//        }
//
//        public static String convertRejectVacationMessage(Vacation vacation) {
//                Member member = vacation.getMember();
//                String memberName = member.getName();
//                String formattedDates = dateFormatting(vacation.getStartDate(), vacation.getEndDate());
//
//                log.info("{}의 {} 휴가가 반려되었습니다.", memberName, formattedDates);
//                return memberName + "님의 " + formattedDates + " 의 휴가가 반려 되었습니다.";
//        }
//
//        // 날짜 포맷팅 및 휴가 일수 계산
//        private static String dateFormatting(LocalDate startDate, LocalDate endDate) {
//
//                // 날짜 차이를 계산하여 휴가 일수 구하기
//                long leaveDays = ChronoUnit.DAYS.between(startDate, endDate) + 1L;
//
//                // 포맷된 날짜와 휴가 일수 반환
//                return startDate + "부터 " + endDate + "까지 총 " + leaveDays + "일";
//        }
//
//}