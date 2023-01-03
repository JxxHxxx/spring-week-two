package com.sparta.springweektwo.comment.dto;


import com.sparta.springweektwo.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentForm {
    private String body;

    public CommentForm(Comment comment) {
        this.body = comment.getBody();
    }
}


