package Offime.Offime.dto.response.vacation;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ResVacation(Long id, String type, LocalDate startDate, LocalDate endDate, String reason, String status,String memberName,
    String modifiedDate, String createdDate) {
}
