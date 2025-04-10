package Offime.Offime.service.member;

import Offime.Offime.common.Role;
import Offime.Offime.dto.request.member.MemberLoginDto;
import Offime.Offime.dto.request.member.MemberRegisterDto;
import Offime.Offime.dto.response.member.MemberListDto;
import Offime.Offime.dto.response.member.MemberPendingDto;
import Offime.Offime.dto.response.member.MemberResponseDto;
import Offime.Offime.dto.response.member.MemberTokenDto;
import Offime.Offime.entity.member.*;
import Offime.Offime.exception.MemberException;
import Offime.Offime.exception.ResourceNotFoundException;
import Offime.Offime.exception.UnauthorizedAccessException;
import Offime.Offime.repository.member.MemberRepository;
import Offime.Offime.security.jwt.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    // 회원가입
    public MemberResponseDto register(MemberRegisterDto memberRegisterDto) {
        String encodedPassword = passwordEncoder.encode(memberRegisterDto.getPassword());
        memberRegisterDto.setPassword(encodedPassword);
        isExistUserEmail(memberRegisterDto.getEmail());

        Member saveMember = MemberRegisterDto.ofEntity(memberRegisterDto);
        if (saveMember.getWorkStatus() == null) {
            saveMember.setWorkStatus(WorkStatus.퇴근);
        }
        if (saveMember.getSignUpStatus() == null) {
            saveMember.setSignUpStatus(SignUpStatus.PENDING);
        }
        saveMember = memberRepository.save(saveMember);
        return MemberResponseDto.fromEntity(saveMember, null);
    }

    private void isExistUserEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
    }

    // 로그인
    public MemberTokenDto login (MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findByEmail(memberLoginDto.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email", memberLoginDto.getEmail()));
        authenticate(memberLoginDto.getEmail(), memberLoginDto.getPassword());
        if (member.getSignUpStatus() == SignUpStatus.PENDING) {
            throw new UnauthorizedAccessException("가입 승인 대기중입니다.");
        }
        if (!member.isEnable()) {
            throw new DisabledException("비활성화 된 계정입니다.");
        }
        String password = memberLoginDto.getPassword();
        if (passwordEncoder.matches(password, member.getPassword())) {
            String token = jwtTokenUtil.generateToken(member);
            return MemberTokenDto.fromEntity(member, token);
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

    }

    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new MemberException("인증되지 않은 아이디입니다.", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            throw new MemberException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 모든 직원 조회
    public List<MemberListDto> getAllMembers() {
        List<Member> members = memberRepository.findBySignUpStatus(SignUpStatus.ACTIVE);
        return members.stream()
                .map(MemberListDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 탈퇴
    public void userDisabled(Member member) {
        member.setEnable(false);
        if (member.getSignUpStatus() == SignUpStatus.ACTIVE) {
            member.setSignUpStatus(null);
        }
        memberRepository.save(member);
    }

    // ADMIND -> USER 삭제
    public void deleteMember(Long id) {
            memberRepository.deleteById(id);
    }

    // 직원 상세페이지
    public MemberResponseDto getMemberDetail(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("직원 정보를 찾을 수 없습니다."));

        // 프로필 이미지 URL을 가져오기 위해 MemberProfileFiles 객체를 조회
        MemberProfileFiles profileFile = member.getMemberProfileFiles(); // Member 객체가 MemberProfileFiles와 연관되어 있다고 가정
        String profileImageUrl = (profileFile != null) ? profileFile.getFilePath() : null;

        return MemberResponseDto.fromEntity(member, profileImageUrl);
    }

    // 가입 신청자 조회
    public List<MemberPendingDto> getPendingMembers() {
        List<Member> members = memberRepository.findBySignUpStatus(SignUpStatus.PENDING);

        if (members.isEmpty()) {
            return new ArrayList<>();
        }

        return members.stream()
                .map(MemberPendingDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void updateSignUpStatusToActive(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("회원", "이메일", email));
        member.setSignUpStatus(SignUpStatus.ACTIVE);
        memberRepository.save(member);
    }

    public void updateMemberRole(Long id, String newRole) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("회원", "ID", id.toString()));

        try {
            member.setRole(Role.valueOf(newRole)); // ✅ String → Enum 변환
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role value: " + newRole);
        }

        memberRepository.save(member);
    }

    public void updateTeam(Long id, String newTeam) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));
        try{
            member.setTeam(Team.valueOf(newTeam));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid team value: " + newTeam);
        }
    }

    public void changePassword(Member member, String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }
}
