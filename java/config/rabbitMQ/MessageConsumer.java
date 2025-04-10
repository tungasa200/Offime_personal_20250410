//package Offime.Offime.config.rabbitMQ;
//
//
//import Offime.Offime.common.Role;
//import Offime.Offime.entity.member.Member;
//import Offime.Offime.entity.notifications.NotificationMessage;
////import Offime.Offime.exception.MemberException;
//import Offime.Offime.repository.member.MemberRepository;
//import Offime.Offime.repository.notification.NotificationMessageRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class MessageConsumer {
//    private final SseService sseService;
//    private final NotificationMessageRepository notificationMessageRepository;
//    private final MemberRepository memberRepository;
//
//    // 휴가 관련 메시지 수신
//    @RabbitListener(queues = RabbitConfig.VACATION_QUEUE)
//    public void handleVacationMessage(NotificationMessage notificationMessage) {
//        Long memberId = notificationMessage.getMemberId();
//        String status = notificationMessage.getStatus();
//        String message = notificationMessage.getMessage();
//        notificationMessageRepository.save(notificationMessage);
//        if (status.equals("request")){
//            Member admin = memberRepository.findByRole(Role.ADMIN).stream().findAny().orElseThrow(()->new MemberException("관리자가 없습니다.", HttpStatus.NOT_FOUND));
//            sseService.sendNotificationAsync(admin.getId(),message);
//        }else {sseService.sendNotificationAsync(memberId, message);}
//        log.info("휴가 관련 메시지 : {}",  message);
//    }
//
//    // 비용 관련 메시지 수신
//    @RabbitListener(queues = RabbitConfig.COST_QUEUE)
//    public void handleCostMessage(String message) {
//        log.info("비용 관련 메시지 : {}",  message);
//    }
//
//}