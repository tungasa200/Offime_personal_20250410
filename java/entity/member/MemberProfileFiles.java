package Offime.Offime.entity.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemberProfileFiles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_path")
    private String filePath;

    public MemberProfileFiles(Long id, Member member, String originFileName, String fileType, String filePath) {
        this.id = id;
        this.member = member;
        this.originFileName = originFileName;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}
