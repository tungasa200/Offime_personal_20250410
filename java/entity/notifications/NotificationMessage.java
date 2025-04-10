package Offime.Offime.entity.notifications;


import Offime.Offime.common.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.auditing.CurrentDateTimeProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotificationMessage extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long memberId;
    private String type;
    private Long typeId;
    private String status;
    private String message;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isRead = false;

}
