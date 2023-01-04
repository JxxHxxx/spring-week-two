package com.sparta.springweektwo.member.controller;

import com.sparta.springweektwo.member.dto.AuthMessage;
import com.sparta.springweektwo.member.dto.LoginRequestDto;
import com.sparta.springweektwo.member.dto.SignUpRequestDto;
import com.sparta.springweektwo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthMessage> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        String message;
        try {
            message = memberService.signUp(signUpRequestDto);
        }
        catch (IllegalArgumentException e) {
            AuthMessage authMessage = new AuthMessage("중복된 username 입니다.", BAD_REQUEST.value());
            return new ResponseEntity<>(authMessage, BAD_REQUEST);
        }
        AuthMessage authMessage = new AuthMessage(message, OK.value());

        return new ResponseEntity<>(authMessage, OK);
    }

    @PostMapping("auth/login")
    public ResponseEntity<AuthMessage> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) {
        String memberToken;

        try {
            memberToken = memberService.login(loginDto);
        } catch (IllegalArgumentException e) {
            AuthMessage authMessage = new AuthMessage("로그인 실패", UNAUTHORIZED.value());
            return new ResponseEntity<>(authMessage, UNAUTHORIZED);
        }
        response.addHeader("Authorization", memberToken);

        AuthMessage authMessage = new AuthMessage("로그인 성공", OK.value());
        return new ResponseEntity<>(authMessage, OK);
    }
}
