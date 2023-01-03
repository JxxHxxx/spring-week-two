package com.sparta.springweektwo.bulletinboard.dto;

import com.sparta.springweektwo.bulletinboard.entity.BulletinBoard;
import com.sparta.springweektwo.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BulletinBoardResponseDto {

    private Long id;
    private String title;
    private String body;
    private String username;
    private List<Comment> comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Boolean isDeleted;

    public BulletinBoardResponseDto(BulletinBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.comment = board.getComments();
        this.body = board.getBody();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        this.isDeleted = board.getIsDeleted();
    }

    public BulletinBoardResponseDto(BulletinBoardResponseDto board, List<Comment> comments) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.comment = comments;
        this.body = board.getBody();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        this.isDeleted = board.getIsDeleted();
    }

}
