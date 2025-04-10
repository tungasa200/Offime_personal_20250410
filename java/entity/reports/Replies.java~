package Offime.Offime.entity.reports;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import practice.demo.entity.member.Member;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Replies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "REPORT_ID", nullable = false)
    private Reports report;

    @ManyToOne
    @JoinColumn(name = "WRITER", nullable = false)
    private Member writer;

    @Column(columnDefinition = "TEXT", name = "CONTENT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modified_at;
}
