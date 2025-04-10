package Offime.Offime.service.reports;

import Offime.Offime.dto.request.reports.QuestionsReqDto;
import Offime.Offime.dto.request.reports.TemplatesReqDto;
import Offime.Offime.dto.response.reports.QuestionsResDto;
import Offime.Offime.dto.response.reports.TemplatesResDto;
import Offime.Offime.entity.reports.Options;
import Offime.Offime.entity.reports.Questions;
import Offime.Offime.entity.reports.Templates;
import Offime.Offime.repository.member.MemberRepository;
import Offime.Offime.repository.reports.OptionsRepository;
import Offime.Offime.repository.reports.QuestionsRepository;
import Offime.Offime.repository.reports.TemplatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TemplatesService {

    private final TemplatesRepository templatesRepository;
    private final MemberRepository memberRepository;
    private final QuestionsRepository questionsRepository;
    private final OptionsRepository optionsRepository;

    // 템플릿 만들기

    public void createTemplate(TemplatesReqDto templatesReqDto) {

        Templates template = new Templates();
        template.setTitle(templatesReqDto.getTitle());
        template.setColor(templatesReqDto.getColor());
        template.setIcon(templatesReqDto.getIcon());

        templatesRepository.save(template);

        createQuestions(template, templatesReqDto.getQuestionList());
    }

    private void createQuestions(Templates template, List<QuestionsReqDto> questionList) {

        for (QuestionsReqDto questionData : questionList) {
            Questions question = new Questions();
            question.setOrder(questionData.getOrder());
            question.setType(questionData.getType());
            question.setQuestionText(questionData.getContent());
            question.setTemplate(template);

            questionsRepository.save(question);

            createOptions(question, questionData.getOptionList());
        }
    }

    private void createOptions(Questions question, List<String> optionList) {

        for (String option : optionList) {
            Options options = new Options();
            options.setOptionText(option);
            options.setQuestion(question);

            optionsRepository.save(options);
        }
    }

    public List<TemplatesResDto> getAllTemplates() {
        return templatesRepository.findAll().stream().map(TemplatesResDto::fromEntity).toList();
    }

    public TemplatesResDto getTemplate(Long templateId) {
        return templatesRepository.findById(templateId).map(TemplatesResDto::fromEntity).orElseThrow(() -> new NoSuchElementException());
    }

    public List<QuestionsResDto> getQuestionsByTemplateId(Long templateId) {
        return questionsRepository.getAllByTemplateId(templateId).stream().map(QuestionsResDto::fromEntity).toList();
    }

    public List<String> getOptions(Long questionId) {
        return optionsRepository.getAllOptionTextByQuestionId(questionId);
    }

    public void deleteTemplate(Long id) {
        templatesRepository.deleteById(id);
    }
}
