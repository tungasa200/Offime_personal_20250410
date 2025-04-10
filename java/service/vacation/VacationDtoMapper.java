package Offime.Offime.service.vacation;

import Offime.Offime.dto.request.vacation.ReqVacation;
import Offime.Offime.dto.response.vacation.ResVacation;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.vacation.Vacation;
import Offime.Offime.entity.vacation.VacationApprovalStatus;
import Offime.Offime.entity.vacation.VacationType;
import org.springframework.stereotype.Service;

@Service
public class VacationDtoMapper {
    public Vacation toEntity(Member member, ReqVacation reqVacation) {
        return Vacation.builder()
                .member(member)
                .type(VacationType.fromDescription(reqVacation.type()))
                .startDate(reqVacation.startDate())
                .endDate(reqVacation.endDate())
                .reason(reqVacation.reason())
                .status(VacationApprovalStatus.WAITING)
                .build();
    }

    public ResVacation fromEntity(Vacation vacation) {
        return ResVacation.builder()
                .id(vacation.getId())
                .type(vacation.getType().getDescription())
                .startDate(vacation.getStartDate())
                .memberName(vacation.getMember().getName())
                .endDate(vacation.getEndDate())
                .reason(vacation.getReason())
                .status(vacation.getStatus().getDescription())
                .modifiedDate(vacation.getModifiedDate())
                .createdDate(vacation.getCreatedDate())
                .build();
    }
}
