package com.sparta.springweektwo.bulletinboard.controller;

import com.sparta.springweektwo.bulletinboard.domain.BulletinBoardService;
import com.sparta.springweektwo.bulletinboard.dto.*;
import com.sparta.springweektwo.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
@Slf4j
@RestController
@RequiredArgsConstructor
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;
    private final JwtUtil jwtUtil;
    // 게시글 작성
    @PostMapping("/bulletin-boards")
    public BulletinBoardResponseDto write(@RequestBody BulletinBoardForm boardForm, HttpServletRequest request) {
        return bulletinBoardService.create(boardForm, request);
    }

    // 전체 게시글 조회
    @GetMapping("/bulletin-boards")
    public BulletinBoardListResponseDto readAll() {
        List<BulletinBoardResponseDto> boardDtos = bulletinBoardService.readAll();
        return new BulletinBoardListResponseDto(boardDtos);
    }
    // 선택 게시글 조회
    @GetMapping("/bulletin-boards/{id}")
    public BulletinBoardResponseDto readOne(@PathVariable Long id) {
        return bulletinBoardService.readOne(id);
    }

    // 선택 게시글 수정
    @PatchMapping("/bulletin-boards/{id}")
    public ResponseEntity<Message> update(@PathVariable Long id, @RequestBody BulletinBoardForm boardForm, HttpServletRequest request) {
        Message message = bulletinBoardService.update(id, boardForm, request);

        if (message.getSuccess() == false) {
            return new ResponseEntity<>(message, UNAUTHORIZED);
        }

        return new ResponseEntity<>(message, OK);
    }

    // 선택 게시글 삭제
    @DeleteMapping("/bulletin-boards/{id}")
    public ResultDto remove(@PathVariable Long id, HttpServletRequest request) {
        return bulletinBoardService.softDelete(id, request);
    }
}
