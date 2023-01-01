package com.sparta.springweektwo.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "[0-9a-z]+")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "[0-9a-zA-Z]+")
    private String password;
}
