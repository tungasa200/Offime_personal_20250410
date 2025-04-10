package Offime.Offime.entity.member;

public enum Team {
    A("미배정"),
    B("경영팀"),
    C("인사팀"),
    D("업무팀");

    private final String status;

    // 생성자
    Team(String status) {
        this.status = status;
    }

    // 상태 반환 메서드
    public String getStatus() {
        return this.status;
    }
}
