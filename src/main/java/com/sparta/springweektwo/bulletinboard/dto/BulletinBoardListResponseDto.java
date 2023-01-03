package com.sparta.springweektwo.bulletinboard.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BulletinBoardListResponseDto {
    private List<BulletinBoardResponseDto> boardList;

    public BulletinBoardListResponseDto(List<BulletinBoardResponseDto> boardList) {
        this.boardList = boardList;
    }
}
