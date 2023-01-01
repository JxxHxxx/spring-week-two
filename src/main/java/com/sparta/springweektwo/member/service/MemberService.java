package com.sparta.springweektwo.member.service;

import com.sparta.springweektwo.member.dto.SignUpRequestDto;
import com.sparta.springweektwo.member.entity.Member;
import com.sparta.springweektwo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final String SIGNUP_SUCCESS = "회원가입 성공";

    public String signUp(SignUpRequestDto requestDto) {
        if (memberRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저이름입니다.");
        }

        Member signUpMember = new Member(requestDto);
        memberRepository.save(signUpMember);

        return SIGNUP_SUCCESS;
    }
}
