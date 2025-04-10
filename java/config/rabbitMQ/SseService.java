package Offime.Offime.config.rabbitMQ;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    // 클라이언트 연결을 위한 메서드
    public SseEmitter subToNotifications(Long memberId) {
        SseEmitter emitter = new SseEmitter(60_000L*5);  // 5분 타임아웃
        emitters.put(memberId, emitter);

        // 연결 종료 및 타임아웃 시 처리
        emitter.onCompletion(() -> emitters.remove(memberId));
        emitter.onTimeout(() -> emitters.remove(memberId));

        return emitter;
    }

    @Async
    public CompletableFuture<Void> sendNotificationAsync(Long memberId, String notification) {
        SseEmitter emitter = emitters.get(memberId);
        if (emitter != null) {
            try {
                emitter.send(notification);  // 클라이언트에게 알림 전송
            } catch (IOException e) {
                emitter.completeWithError(e);  // 전송 중 오류 발생 시
                emitters.remove(memberId);  // 오류 발생 시 해당 emitter 제거
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    // 추가적으로 모든 클라이언트에게 알림을 보내는 기능
    public void sendNotificationToAll(String notification) {
        for (Map.Entry<Long, SseEmitter> entry : emitters.entrySet()) {
            Long memberId = entry.getKey();
            SseEmitter emitter = entry.getValue();
            try {
                emitter.send(notification);
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(memberId);  // 오류 발생 시 해당 emitter 제거
            }
        }
    }
}