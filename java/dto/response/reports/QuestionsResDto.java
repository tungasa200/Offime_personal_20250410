package Offime.Offime.dto.response.reports;

import Offime.Offime.common.reports.QuestionType;
import Offime.Offime.entity.reports.Questions;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionsResDto {
    private Long id;
    private QuestionType type;
    private String questionText;
    private int order;

    @Builder
    public QuestionsResDto(Long id , QuestionType type, String questionText, int order) {
        this.id = id;
        this.type = type;
        this.questionText = questionText;
        this.order = order;
    }

    public static QuestionsResDto fromEntity(Questions questions) {
        return QuestionsResDto.builder()
                .id(questions.getId())
                .questionText(questions.getQuestionText())
                .order(questions.getOrder())
                .type(questions.getType())
                .build();
    }
}
