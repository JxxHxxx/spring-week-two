package com.sparta.springweektwo.comment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.springweektwo.Timestamped;
import com.sparta.springweektwo.bulletinboard.entity.BulletinBoard;
import com.sparta.springweektwo.comment.dto.CommentForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String body;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "BULLETIN_BOARD_ID")
    @JsonProperty(access = WRITE_ONLY)
    private BulletinBoard bulletinBoard;

    public Comment(CommentForm commentForm, BulletinBoard bulletinBoard) {
        this.body = commentForm.getBody();
        this.bulletinBoard = bulletinBoard;
        this.isDeleted = false;
    }

    public Comment update(String body) {
        this.body = body;

        return this;
    }

    public void setSoftDelete() {
        this.isDeleted = true;
    }
}
