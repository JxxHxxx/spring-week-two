package com.sparta.springweektwo.bulletinboard.entity;

import com.sparta.springweektwo.Timestamped;
import com.sparta.springweektwo.bulletinboard.dto.BulletinBoardForm;
import com.sparta.springweektwo.comment.entity.Comment;
import com.sparta.springweektwo.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BulletinBoard extends Timestamped {

    @Id @Column(name = "BULLETIN_BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "bulletinBoard", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public BulletinBoard(BulletinBoardForm boardForm, Member member) {
        this.title = boardForm.getTitle();
        this.body = boardForm.getBody();
        this.member = member;
        this.isDeleted = null;
    }

    public void update(BulletinBoardForm boardForm) {
        this.title = boardForm.getTitle();
        this.body = boardForm.getBody();
    }
    public String getUsername() {
        return this.member.getUsername();
    }

    public void softDelete(Boolean bool) {
        this.isDeleted = bool;
    }
}
