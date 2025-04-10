package Offime.Offime.controller.reports;

import Offime.Offime.dto.request.reports.TemplatesReqDto;
import Offime.Offime.service.reports.TemplatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplatesController {

    private final TemplatesService templatesService;

    @PostMapping("/create")
    public ResponseEntity<String> createTemplate(@RequestBody TemplatesReqDto templatesReqDto) {
        templatesService.createTemplate(templatesReqDto);
        return ResponseEntity.status(HttpStatus.OK).body("템플릿 생성 완료");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable("id") Long id) {
        templatesService.deleteTemplate(id);
        return ResponseEntity.status(200).body("success");
    }


}
