package Offime.Offime.repository.notification;

import Offime.Offime.entity.notifications.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long> {
    boolean existsByMemberId(Long memberId);

    List<NotificationMessage> findAllByMemberId(Long memberId);

    List<NotificationMessage> findByStatus(String status);

    List<NotificationMessage> findAllByStatus(String status);

    List<NotificationMessage> findByMemberIdAndStatus(Long memberId, String status);

}
