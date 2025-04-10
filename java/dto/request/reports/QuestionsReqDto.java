package Offime.Offime.dto.request.reports;

import Offime.Offime.common.reports.QuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionsReqDto {
    private QuestionType type;
    private String content;
    private int order;
    private List<String> optionList;
}
