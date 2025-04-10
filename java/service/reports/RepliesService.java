package Offime.Offime.service.reports;

import Offime.Offime.dto.request.reports.RepliesReqDto;
import Offime.Offime.dto.response.reports.RepliesResDto;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.reports.Replies;
import Offime.Offime.entity.reports.Reports;
import Offime.Offime.repository.member.MemberRepository;
import Offime.Offime.repository.reports.RepliesRepository;
import Offime.Offime.repository.reports.ReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepliesService {

    private final RepliesRepository repliesRepository;
    private final MemberRepository memberRepository;
    private final ReportsRepository reportsRepository;

    // create

    public void createReply (RepliesReqDto repliesReqDto) {

        Replies replies = new Replies();

        Reports reports = reportsRepository.findById(repliesReqDto.getReportId()).get();
        replies.setReport(reports);

        Member writer = memberRepository.findById(repliesReqDto.getWriterId()).get();
        replies.setWriter(writer);

        replies.setContent(repliesReqDto.getContent());

        repliesRepository.save(replies);
    }

    // read

    public List<RepliesResDto> getRepliesByReportId(Long reportId) {
        return repliesRepository.findAllByReportId(reportId).stream().map(RepliesResDto::fromEntity).toList();
    }

    // update

    public void updateReply(Long replyId, RepliesReqDto repliesReqDto) {
        Replies replies = repliesRepository.findById(replyId).get();
        replies.setContent(repliesReqDto.getContent());
        repliesRepository.save(replies);
    }

    // delete

    public void deleteReply(Long replyId) {
        repliesRepository.deleteById(replyId);
    }

}
