package Offime.Offime.dto.request.member;

import Offime.Offime.entity.member.MemberProfileFiles;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberFileUploadDto {
    private Long id;
    private String originFileName;
    private String fileType;
    private String filePath;

    @Builder
    public MemberFileUploadDto(Long id, String originFileName, String fileType, String filePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.fileType = fileType;
        this.filePath = filePath;
    }

    public static MemberFileUploadDto fromEntity(MemberProfileFiles memberProfileFiles){
        return MemberFileUploadDto.builder()
                .id(memberProfileFiles.getId())
                .originFileName(memberProfileFiles.getOriginFileName())
                .fileType(memberProfileFiles.getFileType())
                .filePath(memberProfileFiles.getFilePath())
                .build();
    }
}
