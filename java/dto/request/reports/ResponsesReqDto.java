package Offime.Offime.dto.request.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ResponsesReqDto {
    private Long questionId;

    private String answerText;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate startDate;

    private LocalDate endDate;

    private String fileUrl;
}
