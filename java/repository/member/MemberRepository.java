package Offime.Offime.repository.member;

import Offime.Offime.common.Role;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.member.SignUpStatus;
import Offime.Offime.entity.member.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findBySignUpStatus(SignUpStatus signUpStatus);
    List<Member> findByRole(Role role);
    long countByTeam(Team team);
    List<Member> findByTeam(Team team);
}