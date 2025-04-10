package Offime.Offime.service.reports;

import Offime.Offime.dto.request.reports.ReportsReqDto;
import Offime.Offime.dto.request.reports.ResponsesReqDto;
import Offime.Offime.dto.response.reports.ReportsResDto;
import Offime.Offime.dto.response.reports.ResponseResDto;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.reports.Questions;
import Offime.Offime.entity.reports.Reports;
import Offime.Offime.entity.reports.Responses;
import Offime.Offime.entity.reports.Templates;
import Offime.Offime.repository.member.MemberRepository;
import Offime.Offime.repository.reports.QuestionsRepository;
import Offime.Offime.repository.reports.ReportsRepository;
import Offime.Offime.repository.reports.ResponsesRepository;
import Offime.Offime.repository.reports.TemplatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReportsService {

    private final TemplatesRepository templatesRepository;
    private final MemberRepository memberRepository;
    private final ReportsRepository reportsRepository;
    private final ResponsesRepository responsesRepository;
    private final QuestionsRepository questionsRepository;

    public void createReport(ReportsReqDto reportsReqDto) {

        Reports reports = new Reports();
        reports.setTitle(reportsReqDto.getTitle());

        Member writer = memberRepository.findById(reportsReqDto.getWriterId()).orElseThrow(() -> new NoSuchElementException());

        reports.setWriter(writer);

        Templates templates = templatesRepository.findById(reportsReqDto.getTemplateId()).orElseThrow(() -> new NoSuchElementException());

        reports.setTemplate(templates);

        reportsRepository.save(reports);

        saveResponse(reports, reportsReqDto.getResponseData());
    }

    private void saveResponse(Reports reports, List<ResponsesReqDto> responseDataList) {

        for (ResponsesReqDto responseData : responseDataList) {

            Responses responses = new Responses();
            responses.setReport(reports);
            responses.setStartTime(responseData.getStartTime());
            responses.setEndTime(responseData.getEndTime());
            responses.setStartDate(responseData.getStartDate());
            responses.setEndDate(responseData.getEndDate());
            responses.setFileUrl(responseData.getFileUrl());
            responses.setAnswerText(responseData.getAnswerText());

            Questions question = questionsRepository.findById(responseData.getQuestionId()).orElseThrow(() -> new NoSuchElementException());

            responses.setQuestion(question);

            responsesRepository.save(responses);
        }
    }

    public List<ReportsResDto> getAllReports() {
        return reportsRepository.findAll().stream().map(ReportsResDto::fromEntity).toList();
    }

    public ReportsResDto getReport(Long id) {
        return reportsRepository.findById(id).map(ReportsResDto::fromEntity).orElseThrow(() -> new NoSuchElementException());
    }

    public List<ResponseResDto> getReportResponsesListByQuestion(Long questionId) {

        Questions question = questionsRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException());
        return responsesRepository.findAllByQuestion(question).stream().map(ResponseResDto::fromEntity).toList();
    }

    public void deleteReport(Long reportId) {
        reportsRepository.deleteById(reportId);
    }

    public void updateReport(Long id, ReportsReqDto reportsReqDto) {

        Reports reports = reportsRepository.findById(id).orElseThrow(() -> new NoSuchElementException());

        reports.setTitle(reportsReqDto.getTitle());

        reportsReqDto.getResponseData().forEach(responseData -> {

                    responsesRepository.findByQuestionIdAndReportId(responseData.getQuestionId(), id).ifPresent(responses -> {
                        responses.setAnswerText(responseData.getAnswerText());
                        responses.setStartTime(responseData.getStartTime());
                        responses.setEndTime(responseData.getEndTime());
                        responses.setStartDate(responseData.getStartDate());
                        responses.setEndDate(responseData.getEndDate());
                        responses.setFileUrl(responseData.getFileUrl());
                        responsesRepository.save(responses);
                    });
                });

        reportsRepository.save(reports);
    }

    public ResponseResDto getReportResponses(Long questionId, Long reportId) {

        return responsesRepository.findByQuestionIdAndReportId(questionId, reportId).map(ResponseResDto::fromEntity).orElseThrow(() -> new NoSuchElementException());
    }
}
