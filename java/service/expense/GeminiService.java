package Offime.Offime.service.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getResponse(String message) {
        String url = GEMINI_URL + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", message))))
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("candidates")) {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            if (!candidates.isEmpty()) {
                Map<String, Object> firstCandidate = candidates.get(0);
                if (firstCandidate.containsKey("content")) {
                    Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                    if (content.containsKey("parts")) {
                        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                        if (!parts.isEmpty()) {
                            Map<String, Object> firstPart = parts.get(0);
                            if (firstPart.containsKey("text")) {
                                return (String) firstPart.get("text");
                            }
                        }
                    }
                }
            }
        }
        return "응답 없음";
    }
}