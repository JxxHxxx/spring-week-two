package com.sparta.springweektwo.bulletinboard.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BulletinBoardListResponseDto {
    private List<BulletinBoardResponseDto> postList;

    public BulletinBoardListResponseDto(List<BulletinBoardResponseDto> postList) {
        this.postList = postList;
    }
}
