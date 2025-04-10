package Offime.Offime.entity.reports;

import Offime.Offime.common.reports.QuestionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_ID", nullable = false)
    private Templates template;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private QuestionType type;

    @Column(columnDefinition = "TEXT", name = "QUESTION_TEXT")
    private String questionText;

    @Column(name = "`ORDER`", nullable = false)
    private int order;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Responses> responses = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Options> options = new ArrayList<>();
}
