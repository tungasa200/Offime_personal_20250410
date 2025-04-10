package Offime.Offime.dto.response.reports;

import Offime.Offime.entity.reports.Replies;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RepliesResDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long writerId;

    @Builder
    public RepliesResDto(Long id, String content, LocalDateTime createdAt, Long writerId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.writerId = writerId;
    }

    public static RepliesResDto fromEntity(Replies replies) {
        return RepliesResDto.builder()
                .id(replies.getID())
                .content(replies.getContent())
                .createdAt(replies.getCreated_at())
                .writerId(replies.getWriter().getId())
                .build();
    }
}
