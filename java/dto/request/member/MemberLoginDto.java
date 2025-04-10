package Offime.Offime.dto.request.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginDto {
    private String email;
    private String password;

    @Builder
    public MemberLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
