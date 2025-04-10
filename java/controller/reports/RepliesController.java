package Offime.Offime.controller.reports;

import Offime.Offime.dto.request.reports.RepliesReqDto;
import Offime.Offime.dto.response.reports.RepliesResDto;
import Offime.Offime.service.reports.RepliesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class RepliesController {

     private final RepliesService repliesService;

     @PostMapping("/create")
     public ResponseEntity<String> createReply(@RequestBody RepliesReqDto repliesReqDto) {
         repliesService.createReply(repliesReqDto);
         return ResponseEntity.status(200).body("success");
     }

     @GetMapping("/read/{reportId}")
     public ResponseEntity<List<RepliesResDto>> readReplies(@PathVariable("reportId") Long reportId) {
         return ResponseEntity.status(200).body(repliesService.getRepliesByReportId(reportId));
     }

     @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deleteReply(@PathVariable("id") Long id) {
         repliesService.deleteReply(id);
         return ResponseEntity.status(200).body("success");
     }

     @PutMapping("/update/{id}")
     public ResponseEntity<String> updateReply(@PathVariable("id") Long id, @RequestBody RepliesReqDto repliesReqDto) {
         repliesService.updateReply(id, repliesReqDto);
         return ResponseEntity.status(200).body("success");
     }
}
