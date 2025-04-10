package Offime.Offime.config.rabbitMQ;


import Offime.Offime.entity.notifications.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;


@RequiredArgsConstructor
@Configuration
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    // 휴가 메시지 발행
    public void sendVacationMessage(String routingKey,Long memberId,Long vacationId,String message) {
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setMemberId(memberId);
        notificationMessage.setType("vacation");
        notificationMessage.setTypeId(vacationId);
        notificationMessage.setStatus("request");
        notificationMessage.setMessage(message);
        notificationMessage.setIsRead(false);
        rabbitTemplate.convertAndSend(RabbitConfig.VACATION_EXCHANGE, routingKey, notificationMessage);
    }
    public void sendVacationApprovedMessage(String routingKey, Long memberId,Long vacationId,String message) {
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setMemberId(memberId);
        notificationMessage.setType("vacation");
        notificationMessage.setTypeId(vacationId);
        notificationMessage.setStatus("approved");
        notificationMessage.setMessage(message);
        notificationMessage.setIsRead(false);
        rabbitTemplate.convertAndSend(RabbitConfig.VACATION_EXCHANGE, routingKey, notificationMessage);
    }
    public void sendVacationRejectedMessage(String routingKey,Long memberId,Long vacationId,String message) {
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setMemberId(memberId);
        notificationMessage.setType("vacation");
        notificationMessage.setTypeId(vacationId);
        notificationMessage.setStatus("rejected");
        notificationMessage.setMessage(message);
        notificationMessage.setIsRead(false);
        rabbitTemplate.convertAndSend(RabbitConfig.VACATION_EXCHANGE, routingKey, notificationMessage);
    }

    // 비용 메시지 발행
    public void sendCostMessage(String routingKey ,String message) {

        rabbitTemplate.convertAndSend(RabbitConfig.COST_EXCHANGE, routingKey, message);
    }

}