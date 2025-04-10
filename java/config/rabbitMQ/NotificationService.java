package Offime.Offime.config.rabbitMQ;

import Offime.Offime.common.Role;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.notifications.NotificationMessage;
import Offime.Offime.repository.notification.NotificationMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMessageRepository notificationMessageRepository;

    public List<NotificationMessage> getAll(Member member) {
        if (member.getRole() == Role.ADMIN) {
            return notificationMessageRepository.findByStatus("request");
        }

        // 사용자 상태에 맞는 메시지 조회
        return getNotificationsByStatusForMember(member.getId(), "approved", "rejected");
    }

    private List<NotificationMessage> getNotificationsByStatusForMember(Long memberId, String... statuses) {
        // Stream을 사용해 여러 상태를 처리
        return Arrays.stream(statuses)
                .flatMap(status -> notificationMessageRepository.findByMemberIdAndStatus(memberId, status).stream())
                .collect(Collectors.toList())
                ;
    }
    // 알림 메시지 읽음 처리
    @Transactional  // 트랜잭션 관리
    public NotificationMessage getById(Long id){
        NotificationMessage notificationMessage = notificationMessageRepository.findById(id).orElseThrow(()->new IllegalArgumentException("없음"));

        // 읽음 상태로 업데이트
        notificationMessage.setIsRead(true);

        // 변경 사항 저장 (flush는 필요 없음)
        notificationMessageRepository.save(notificationMessage);

        // 읽은 메시지 반환
        return notificationMessage;

    }

}
