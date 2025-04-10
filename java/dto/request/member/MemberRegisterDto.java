package Offime.Offime.dto.request.member;

import Offime.Offime.common.Role;
import Offime.Offime.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterDto {

    private String name;
    private String email;
    private String password;
    private String phone;

    @Builder
    public MemberRegisterDto(String name, String email, String password, String phone, int vacation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public static Member ofEntity(MemberRegisterDto dto){
        return Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();
    }
}
