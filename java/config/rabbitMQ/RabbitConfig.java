package Offime.Offime.config.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableRabbit
public class RabbitConfig {


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();  // JSON 직렬화/역직렬화 설정
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);  // JSON 변환기를 설정
        return template;
    }

    // 큐와 Exchange 이름 정의
    public static final String VACATION_EXCHANGE = "vacationExchange";
    public static final String COST_EXCHANGE = "costExchange";

    // 큐 이름
    public static final String VACATION_QUEUE = "vacationQueue";
    public static final String COST_QUEUE = "costQueue";

    // 1. 휴가 큐와 Exchange 생성
    @Bean
    public Queue vacationQueue() {
        return new Queue(VACATION_QUEUE, true); // 큐 이름과 지속성 설정
    }

    @Bean
    public TopicExchange vacationExchange() {
        return new TopicExchange(VACATION_EXCHANGE, true, true); // Exchange 이름, 지속성, 내구성 설정
    }

    @Bean
    public Binding vacationBinding(Queue vacationQueue, TopicExchange vacationExchange) {
        return BindingBuilder.bind(vacationQueue)
                .to(vacationExchange)
                .with("vacation.*"); // 라우팅 키 예: vacation.request, vacation.approve
    }

    // 2. 비용 큐와 Exchange 생성
    @Bean
    public Queue costQueue() {
        return new Queue(COST_QUEUE, true);
    }

    @Bean
    public TopicExchange costExchange() {
        return new TopicExchange(COST_EXCHANGE, true, true);
    }

    @Bean
    public Binding costBinding(Queue costQueue, TopicExchange costExchange) {
        return BindingBuilder.bind(costQueue)
                .to(costExchange)
                .with("cost.*"); // 라우팅 키 예: cost.approve, cost.deny
    }

}
