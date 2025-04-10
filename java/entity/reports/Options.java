package Offime.Offime.entity.reports;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private Questions question;

    @Column(name = "OPTION_TEXT" , nullable = false)
    private String optionText;
}
