package Offime.Offime.entity.vacation;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VacationType {
    FULL("연차"),
    HALF("반차"),
    QUATER("반반차");



    private final String description;

    VacationType(String description) {
        this.description = description;
    }
    // 입력 받은 String 값을 enum 값으로 변환 하는 메서드
    public static VacationType fromDescription(String description) {
        return Arrays.stream(VacationType.values()).filter(v -> v.description.equals(description)).findFirst().orElseThrow(()->new IllegalArgumentException("잘못된 휴가 타입"+description));
    }
    @JsonValue
    public String getDescription() {
        return description;
    }
}