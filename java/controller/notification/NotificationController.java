
package Offime.Offime.controller.notification;

import Offime.Offime.config.rabbitMQ.NotificationService;
import Offime.Offime.config.rabbitMQ.SseService;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.notifications.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final SseService sseService;
    private final NotificationService notificationService;

    @GetMapping("/subscribe")
    public SseEmitter subscribeNotifications(@AuthenticationPrincipal Member member) {
        Long memberId = member.getId();
        return sseService.subToNotifications(memberId);
    }

    @GetMapping("/notification")
    public ResponseEntity<?> listNotifications(@AuthenticationPrincipal Member member) {
        List<NotificationMessage> sortedNotifications = notificationService.getAll(member)
                .stream()
                .sorted(Comparator.comparing(NotificationMessage::getId).reversed()) // 최신순 정렬
                .toList();
        return ResponseEntity.ok(sortedNotifications);
    }

    @GetMapping("/notification/{notificationId}")
    public ResponseEntity<?> getNotification(@AuthenticationPrincipal Member member,
            @PathVariable Long notificationId) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getById(notificationId));
    }

    @PatchMapping("/notification/{notificationId}")
    public ResponseEntity<?> readNotification(@AuthenticationPrincipal Member member,
            @PathVariable Long notificationId) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getById(notificationId));
    }

}
