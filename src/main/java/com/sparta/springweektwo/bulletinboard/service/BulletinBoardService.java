package com.sparta.springweektwo.bulletinboard.service;
import com.sparta.springweektwo.bulletinboard.dto.*;
import com.sparta.springweektwo.bulletinboard.entity.BulletinBoard;
import com.sparta.springweektwo.bulletinboard.repository.BulletinBoardRepository;
import com.sparta.springweektwo.comment.service.CommentService;
import com.sparta.springweektwo.jwt.JwtUtil;
import com.sparta.springweektwo.member.entity.Member;
import com.sparta.springweektwo.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BulletinBoardService {

    private final BulletinBoardRepository bulletinBoardRepository;
    private final MemberRepository memberRepository;
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    public BulletinBoardResponseDto create(BulletinBoardForm boardForm, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            return null;
        }

        if (!jwtUtil.validateToken(token)) {
            return null;
        }

        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow();

        BulletinBoard board = new BulletinBoard(boardForm, member);
        BulletinBoard saveBoard = bulletinBoardRepository.save(board);

        return new BulletinBoardResponseDto(saveBoard);
    }

    public List<BulletinBoardResponseDto> readAll() {
        List<BulletinBoard> boards = bulletinBoardRepository.findAllByOrderByCreateAtDesc()
                .stream().filter(bulletinBoard -> bulletinBoard.getIsDeleted() == null).collect(Collectors.toList());

        return boards.stream().map(bulletinBoard -> new BulletinBoardResponseDto(bulletinBoard, commentService.read(bulletinBoard.getId()))).collect(Collectors.toList());

    }

    public BulletinBoardResponseDto readOne(Long id) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (board.getIsDeleted() != null) {
            throw new IllegalArgumentException("삭제된 게시글입니다.");
        }

        return new BulletinBoardResponseDto(board);
    }

    @Transactional
    public ResultDto softDelete(Long id, HttpServletRequest request) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            return null;
        }

        if (!jwtUtil.validateToken(token)) {
            return null;
        }

        if (!jwtUtil.getUserInfoFromToken(token).getSubject().equals(board.getUsername())) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        board.softDelete(true);

        return new ResultDto(true);
    }

    @Transactional
    public Message update(Long id, BulletinBoardForm boardForm, HttpServletRequest request) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        String token = jwtUtil.resolveToken(request);
        
        if (token == null) {
            return null;
        }
        
        if (!jwtUtil.validateToken(token)) {
            return null;
        }
        if (!jwtUtil.getUserInfoFromToken(token).getSubject().equals(board.getUsername())) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        board.update(boardForm);

        return new Message(true, new BulletinBoardResponseDto(board));
    }
}
