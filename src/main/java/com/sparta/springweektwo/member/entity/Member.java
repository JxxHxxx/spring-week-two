package com.sparta.springweektwo.member.entity;

import com.sparta.springweektwo.Timestamped;
import com.sparta.springweektwo.member.dto.SignUpRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

import static com.sparta.springweektwo.member.entity.MemberGrade.USER;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@Validated
public class Member extends Timestamped {

    @Id @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    public Member(SignUpRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.grade = USER;
    }
}
