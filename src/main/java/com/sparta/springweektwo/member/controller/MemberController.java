package com.sparta.springweektwo.member.controller;

import com.sparta.springweektwo.member.dto.AuthMessage;
import com.sparta.springweektwo.member.dto.SignUpRequestDto;
import com.sparta.springweektwo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthMessage> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        String message = memberService.signUp(signUpRequestDto);
        AuthMessage authMessage = new AuthMessage(message, OK.value());

        return new ResponseEntity<>(authMessage, OK);
    }
}