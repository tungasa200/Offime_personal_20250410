package Offime.Offime.dto.response.reports;

import Offime.Offime.entity.reports.Responses;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ResponseResDto {

    private Long id;
    private String answerText;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String fileUrl;
    private Long questionId;
    private Long reportId;

    @Builder
    public ResponseResDto(Long id, String answerText, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate,String fileUrl, Long questionId, Long reportId) {
        this.id = id;
        this.answerText = answerText;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileUrl = fileUrl;
        this.questionId = questionId;
        this.reportId = reportId;
    }

    public static ResponseResDto fromEntity(Responses responses) {
        return ResponseResDto.builder()
                .id(responses.getID())
                .answerText(responses.getAnswerText())
                .startTime(responses.getStartTime())
                .endTime(responses.getEndTime())
                .startDate(responses.getStartDate())
                .endDate(responses.getEndDate())
                .fileUrl(responses.getFileUrl())
                .questionId(responses.getQuestion().getId())
                .reportId(responses.getReport().getId())
                .build();
    }
}
