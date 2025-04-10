package Offime.Offime.dto.response.reports;

import Offime.Offime.entity.reports.Reports;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReportsResDto {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long writerId;
    private Long templateId;

    @Builder
    public ReportsResDto(Long id, String title, LocalDateTime createdAt, LocalDateTime modifiedAt, Long writerId, Long templateId) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerId = writerId;
        this.templateId = templateId;
    }

    public static ReportsResDto fromEntity(Reports reports) {
        return ReportsResDto.builder()
                .id(reports.getId())
                .title(reports.getTitle())
                .createdAt(reports.getCreatedAt())
                .modifiedAt(reports.getModifiedAt())
                .writerId(reports.getWriter().getId())
                .templateId(reports.getTemplate().getId())
                .build();
    }
}
