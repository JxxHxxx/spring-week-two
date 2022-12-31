package com.sparta.springweekone.bulletinboard.dto;

import com.sparta.springweekone.bulletinboard.entity.BulletinBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BulletinBoardDto {

    private Long id;
    private String title;
    private String mainText;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public BulletinBoardDto(BulletinBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.mainText = board.getBody();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
