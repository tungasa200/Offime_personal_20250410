package Offime.Offime.entity.vacation;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum VacationApprovalStatus {
    WAITING("대기"),
    APPROVED("승인"),
    REJECTED("반려");


    private final String description;

    VacationApprovalStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}