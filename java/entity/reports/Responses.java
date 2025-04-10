package Offime.Offime.entity.reports;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Responses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "REPORT_ID", nullable = false)
    private Reports report;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private Questions question;

    @Column(columnDefinition = "TEXT", name = "ANSWER_TEXT")
    private String answerText;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "FILE_URL")
    private String fileUrl;
}
