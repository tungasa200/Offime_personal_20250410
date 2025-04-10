package Offime.Offime.entity.member;

import Offime.Offime.common.BaseTimeEntity;
import Offime.Offime.common.Role;
import Offime.Offime.entity.member.WorkStatus;
//import Offime.Offime.entity.vacation.Vacation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private boolean enable;

    @Setter
    @Column(precision = 4,scale=2)
    private BigDecimal availableLeaveDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "WORK_STATUS")
    private WorkStatus workStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Team team;

    @Enumerated(EnumType.STRING)
    private SignUpStatus signUpStatus;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private MemberProfileFiles memberProfileFiles;


//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Vacation> vacations;



    @Builder
    public Member(Long id, String name, String email, String password, String phone, BigDecimal availableLeaveDays, WorkStatus workStatus, Role role, Team team, boolean enable, SignUpStatus signUpStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.availableLeaveDays = availableLeaveDays;
        this.workStatus = workStatus;
        this.role = role;
        this.team = team;
        this.enable = enable;
        this.signUpStatus = signUpStatus;
    }
    
    // 회원가입에 오류가 있어 주석 처리
//    @PrePersist
//    public void prePersist() {
//        if (this.availableLeaveDays.compareTo(BigDecimal.ZERO) == 0) { this.availableLeaveDays = new BigDecimal("12.00"); }
//        if (this.role == null) { this.role = Role.USER; }
//    }

    // 회원가입 가능한 상태를 임시로 만들어서 사용 중. 추후 제거
    @PrePersist
    public void prePersist() {
        if (this.availableLeaveDays == null) {
            this.availableLeaveDays = new BigDecimal("12.00");
        } else if (this.availableLeaveDays.compareTo(BigDecimal.ZERO) == 0) {
            this.availableLeaveDays = new BigDecimal("12.00");
        }
        if (this.role == null) {
            this.role = Role.USER;
        }
        if (!this.enable) {
            this.enable = true;
        }
        if (this.team == null){
            this.team = Team.A;
        }
        if (this.workStatus == null){
            this.workStatus = workStatus;
        }
    }

    public void updateWorkStatus(WorkStatus workStatus){
        this.workStatus = workStatus;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //Authentication 객체에 부여된 권한을 String으로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_" + this.role.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}