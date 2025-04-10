//package Offime.Offime.controller.vacation;
//
//import Offime.Offime.dto.request.vacation.ReqVacation;
//import Offime.Offime.dto.response.vacation.ResVacation;
//import Offime.Offime.entity.member.Member;
//import Offime.Offime.entity.vacation.Vacation;
//import Offime.Offime.entity.vacation.VacationApprovalStatus;
//import Offime.Offime.service.vacation.VacationService;
//import lombok.RequiredArgsConstructor;
//import org.apache.coyote.Response;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/vacation")
//public class VacationController {
//    private final VacationService vacationService;
//
//    @GetMapping
//    public ResponseEntity<?> getAll(@AuthenticationPrincipal Member member,
//            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<ResVacation> list = vacationService.getAllVacations(member, pageable);
//        return ResponseEntity.ok().body(list);
//    }
//    @GetMapping("/wait")
//    public ResponseEntity<?> getPending(@AuthenticationPrincipal Member member,
//                                    @RequestParam VacationApprovalStatus status,
//                                    @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        System.out.println("받은 상태값: " + status);
//        Page<ResVacation> list = vacationService.getPendingVacations(status,pageable);
//        return ResponseEntity.ok().body(list);
//    }
//
//
//    @GetMapping("/latest")
//    public ResponseEntity<?> getLatestVacation(@AuthenticationPrincipal Member member) {
//        List<ResVacation> list = vacationService.getFiveLatestVacation(member);
//        return ResponseEntity.ok().body(list);
//    }
//
//    // 특정 휴가 상세 조회
//    @GetMapping("/{vacationId}")
//    public ResponseEntity<?> getVacationById(@AuthenticationPrincipal Member member,
//            @PathVariable("vacationId") Long vacationId) {
//        ResVacation resVacation = vacationService.getVacationById(member, vacationId);
//        return ResponseEntity.ok().body(resVacation);
//    }
//
//    // 휴가 신청 (POST)
//    @PostMapping
//    public ResponseEntity<?> create(@AuthenticationPrincipal Member member, @RequestBody ReqVacation reqVacation) {
//        ResVacation resVacation = vacationService.applyVacation(member, reqVacation);
//        return ResponseEntity.status(HttpStatus.CREATED).body(resVacation);
//    }
//
//    // 휴가 삭제
//    @DeleteMapping("/{vacationId}")
//    public ResponseEntity<?> delete(@AuthenticationPrincipal Member member,
//            @PathVariable("vacationId") Long vacationId) {
//        vacationService.deleteVacation(member, vacationId);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
//    @PostMapping("/{vacationId}/approve")
//    public ResponseEntity<?> approve(@AuthenticationPrincipal Member member,
//            @PathVariable("vacationId") Long vacationId) {
//        return ResponseEntity.status(HttpStatus.OK).body(vacationService.approveVacation(member, vacationId));
//    }
//
//    @PostMapping("/{vacationId}/reject")
//    public ResponseEntity<?> reject(@AuthenticationPrincipal Member member,
//            @PathVariable("vacationId") Long vacationId) {
//        return ResponseEntity.status(HttpStatus.OK).body(vacationService.rejectVacation(member, vacationId));
//    }
//
//}
