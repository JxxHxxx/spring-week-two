package com.sparta.springweektwo.bulletinboard.dto;

import com.sparta.springweektwo.bulletinboard.entity.BulletinBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BulletinBoardResponseDto {

    private Long id;
    private String title;
    private String body;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Boolean isDeleted;

    public BulletinBoardResponseDto(BulletinBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.body = board.getBody();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        this.isDeleted = board.getIsDeleted();
    }
}
