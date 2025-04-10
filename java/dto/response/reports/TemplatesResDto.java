package Offime.Offime.dto.response.reports;


import Offime.Offime.entity.reports.Templates;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class TemplatesResDto {
    private Long id;
    private String title;
    private Byte icon;
    private Byte color;

    @Builder
    public TemplatesResDto(Long id, String title, Byte icon, Byte color) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.color = color;
    }

    public static TemplatesResDto fromEntity(Templates templates) {
        return TemplatesResDto.builder()
                .id(templates.getId())
                .title(templates.getTitle())
                .icon(templates.getIcon())
                .color(templates.getColor())
                .build();
    }
}


