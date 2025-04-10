package Offime.Offime.repository.member;

import Offime.Offime.entity.member.MemberProfileFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileFilesRepository extends JpaRepository<MemberProfileFiles, Long> {

}
