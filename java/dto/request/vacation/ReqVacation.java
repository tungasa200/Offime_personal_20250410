package Offime.Offime.dto.request.vacation;

import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.vacation.Vacation;
import Offime.Offime.entity.vacation.VacationApprovalStatus;
import Offime.Offime.entity.vacation.VacationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record ReqVacation(String type,
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate startDate,
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate endDate,
                          String reason) {

}
