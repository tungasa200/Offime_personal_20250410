package Offime.Offime.dto.response.member;

import Offime.Offime.common.Role;
import Offime.Offime.entity.member.Team;
import Offime.Offime.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberTokenDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Team team;
    private String createdDate;
    private boolean enable;
    private String token;

    @Builder
    public MemberTokenDto(Long id, String name, String email, Role role, Team team, String createdDate, String token, boolean enable) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.team = team;
        this.createdDate = createdDate;
        this.token = token;
        this.enable = enable;
    }

    public static MemberTokenDto fromEntity(Member member, String token){
        return MemberTokenDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .role(member.getRole())
                .team(member.getTeam())
                .createdDate(member.getCreatedDate())
                .enable(member.isEnable())
                .token(token)
                .build();
    }

}