package com.sparta.springweektwo.bulletinboard.entity;

import com.sparta.springweektwo.Timestamped;
import com.sparta.springweektwo.bulletinboard.dto.BulletinBoardForm;
import com.sparta.springweektwo.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class BulletinBoard extends Timestamped {

    @Id @Column(name = "BULLETIN_BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    
    public BulletinBoard(BulletinBoardForm boardForm) {
        this.body = boardForm.getBody();
        this.title = boardForm.getTitle();
    }

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
}
