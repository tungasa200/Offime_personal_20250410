package Offime.Offime.entity.reports;

import Offime.Offime.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.util.List;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Templates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false, name = "TITLE")
    private String title;

    @Column(name = "ICON")
    private Byte icon = 1;

    @Column(name = "COLOR")
    private Byte color = 1;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reports> reports;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Questions> questions;

}
