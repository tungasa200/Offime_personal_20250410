package Offime.Offime.entity.common;

import lombok.Getter;

@Getter
public enum RequestStatus {
    PENDING("대기"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String label;

    RequestStatus(String label) {
        this.label = label;
    }

}