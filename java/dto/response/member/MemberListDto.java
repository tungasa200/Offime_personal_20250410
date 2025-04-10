package Offime.Offime.dto.response.member;

import Offime.Offime.dto.response.member.MemberListDto;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.member.MemberProfileFiles;
import Offime.Offime.entity.member.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberListDto {

    private Long id;
    private String name;
    private Team team;
    private String profileImageUrl;
    private String workStatus;

    @Builder
    public MemberListDto(Long id, String name, Team team, String profileImageUrl, String workStatus) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.profileImageUrl = profileImageUrl;
        this.workStatus = workStatus;
    }

    public static MemberListDto fromEntity(Member member){
        return MemberListDto.builder()
                .id(member.getId())
                .name(member.getName())
                .team(member.getTeam())
                .profileImageUrl(member.getMemberProfileFiles() != null
                        ? member.getMemberProfileFiles().getFilePath()
                        : null)
                .build();
    }
}
