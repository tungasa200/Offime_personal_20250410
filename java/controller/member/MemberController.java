package Offime.Offime.controller.member;

import Offime.Offime.dto.request.member.ChangePassword;
import Offime.Offime.dto.request.member.MemberLoginDto;
import Offime.Offime.dto.request.member.MemberRegisterDto;
import Offime.Offime.dto.response.member.MemberListDto;
import Offime.Offime.dto.response.member.MemberResponseDto;
import Offime.Offime.dto.response.member.MemberTokenDto;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.member.MemberProfileFiles;
import Offime.Offime.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<String> register(@RequestBody MemberRegisterDto memberRegisterDto){
        memberService.register(memberRegisterDto);
        return ResponseEntity.status(HttpStatus.OK).body("가입 완료");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDto memberLoginDto) {
        MemberTokenDto memberTokenDto = memberService.login(memberLoginDto);
            return ResponseEntity.status(HttpStatus.OK).header(memberTokenDto.getToken()).body(memberTokenDto);
    }


    // 모든 직원 조회
    @GetMapping("/member")
    public ResponseEntity<List<MemberListDto>> getAllMembers() {
        List<MemberListDto> memberListDtos = memberService.getAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(memberListDtos);
    }

    // 탈퇴
    @GetMapping("/unable")
    public ResponseEntity<String> userUnable(@AuthenticationPrincipal Member member) {
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
        }

        if (member.isEnable()) {
            memberService.userDisabled(member);
            return ResponseEntity.status(HttpStatus.OK).body("유저 탈퇴 성공");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("이미 탈퇴 상태입니다");
        }
    }

    // 직원 상세페이지
    @GetMapping("/member/{id}")
    public ResponseEntity<MemberResponseDto> getMemberDetail(@PathVariable Long id) {
        MemberResponseDto memberResponseDto = memberService.getMemberDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    // 내 정보 조회
    @GetMapping("/member/myInfo")
    public ResponseEntity<MemberResponseDto> getMyInfo(@AuthenticationPrincipal Member member) {
        // 프로필 이미지 URL을 가져오기 위해 MemberProfileFiles 객체를 조회
        MemberProfileFiles profileFile = member.getMemberProfileFiles(); // Member 객체의 프로필 파일 정보 조회
        String profileImageUrl = (profileFile != null) ? profileFile.getFilePath() : null;

        // MemberResponseDto를 생성할 때 profileImageUrl도 함께 전달
        MemberResponseDto myInfo = MemberResponseDto.fromEntity(member, profileImageUrl);

        return ResponseEntity.status(HttpStatus.OK).body(myInfo);
    }

    // 비밀번호 변경
    @PutMapping("/member/changePassword")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal Member member,
                                                 @RequestBody ChangePassword changePassword) {
        try {
            memberService.changePassword(member, changePassword.getCurrentPassword(), changePassword.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재 비밀번호가 일치하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
        }
    }
}
