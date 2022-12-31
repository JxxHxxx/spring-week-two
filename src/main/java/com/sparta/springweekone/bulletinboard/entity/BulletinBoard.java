package com.sparta.springweekone.bulletinboard.entity;

import com.sparta.springweekone.Timestamped;
import com.sparta.springweekone.bulletinboard.dto.BulletinBoardForm;
import com.sparta.springweekone.member.entity.Member;
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
    private String password;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    
    public BulletinBoard(BulletinBoardForm boardForm) {
        this.password = boardForm.getPassword();
        this.body = boardForm.getBody();
        this.title = boardForm.getTitle();
    }

    public void update(BulletinBoardForm bulletinBoardDto) {
        this.title = bulletinBoardDto.getTitle();
        this.body = bulletinBoardDto.getBody();
    }
}
