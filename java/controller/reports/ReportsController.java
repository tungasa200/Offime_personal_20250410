package Offime.Offime.controller.reports;

import Offime.Offime.dto.request.reports.ReportsReqDto;
import Offime.Offime.dto.response.reports.QuestionsResDto;
import Offime.Offime.dto.response.reports.ReportsResDto;
import Offime.Offime.dto.response.reports.ResponseResDto;
import Offime.Offime.dto.response.reports.TemplatesResDto;
import Offime.Offime.service.reports.ReportsService;
import Offime.Offime.service.reports.TemplatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final TemplatesService templatesService;
    private final ReportsService reportsService;

    // create

    @GetMapping("/templateSelectList")
    public ResponseEntity<List<TemplatesResDto>> templateSelectList() {
        return ResponseEntity.status(200).body(templatesService.getAllTemplates());
    }

    @GetMapping("/template/{templateId}")
    public ResponseEntity<TemplatesResDto> getTemplateById (@PathVariable("templateId") Long templateId) {
        return ResponseEntity.status(200).body(templatesService.getTemplate(templateId));
    }

    @GetMapping("/template/{templateId}/questions")
    public ResponseEntity<List<QuestionsResDto>> getQuestionsByTemplateId (@PathVariable("templateId") Long templateId) {
        return ResponseEntity.status(200).body(templatesService.getQuestionsByTemplateId(templateId));
    }

    @GetMapping("/template/questions/{questionId}")
    public ResponseEntity<List<String>> getOptions(@PathVariable("questionId") Long questionId) {
        return ResponseEntity.status(200).body(templatesService.getOptions(questionId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReport (@RequestBody ReportsReqDto reportsReqDto) {
        reportsService.createReport(reportsReqDto);
        return ResponseEntity.status(200).body("success");
    }

    // update

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReport(@PathVariable("id") Long id, @RequestBody ReportsReqDto reportsReqDto) {
        reportsService.updateReport(id, reportsReqDto);
        return ResponseEntity.status(200).body("success");
    }

    // read

    @GetMapping("/read")
    public ResponseEntity<List<ReportsResDto>> readReportList() {
        return ResponseEntity.status(200).body(reportsService.getAllReports());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ReportsResDto> readReport(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(reportsService.getReport(id));
    }

    @GetMapping("/read/response/{questionId}")
    public ResponseEntity<List<ResponseResDto>> readReportResponsesListByQuestion(@PathVariable("questionId") Long questionId) {
        return ResponseEntity.status(200).body(reportsService.getReportResponsesListByQuestion(questionId));
    }

    @GetMapping("/read/response/{questionId}/{reportId}")
    public ResponseEntity<ResponseResDto> readReportResponses(@PathVariable("questionId") Long questionId, @PathVariable("reportId") Long reportId) {
        return ResponseEntity.status(200).body(reportsService.getReportResponses(questionId, reportId));
    }

    // delete

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable("id") Long id) {
        reportsService.deleteReport(id);
        return ResponseEntity.status(200).body("success");
    }

}
