package com.sparta.springweekone.bulletinboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BulletinBoardForm {
    private String password;
    private String title;
    private String body;

    public BulletinBoardForm(String password, String title, String body) {
        this.password = password;
        this.title = title;
        this.body = body;
    }
}
