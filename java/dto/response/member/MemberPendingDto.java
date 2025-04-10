package Offime.Offime.dto.response.member;

import Offime.Offime.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberPendingDto {

    private String email;
    private String name;

    @Builder
    public MemberPendingDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static MemberPendingDto fromEntity(Member member){
        return MemberPendingDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
