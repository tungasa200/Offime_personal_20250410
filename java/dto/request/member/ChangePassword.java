package Offime.Offime.dto.request.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassword {
    private String currentPassword;
    private String newPassword;
}
