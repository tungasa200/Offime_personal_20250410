package Offime.Offime.controller.expense;


import Offime.Offime.service.expense.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final GeminiService geminiService;

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        try {
            String reply = geminiService.getResponse(message);
            return Map.of("reply", reply);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("reply", "오류가 발생했습니다.");
        }
    }
}
