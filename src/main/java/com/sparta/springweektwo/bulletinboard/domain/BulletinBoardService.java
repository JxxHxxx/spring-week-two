package com.sparta.springweektwo.bulletinboard.domain;
import com.sparta.springweektwo.bulletinboard.dto.*;
import com.sparta.springweektwo.bulletinboard.entity.BulletinBoard;
import com.sparta.springweektwo.bulletinboard.repository.BulletinBoardRepository;
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
    private final JwtUtil jwtUtil;

    public BulletinBoardDto create(BulletinBoardForm boardForm, HttpServletRequest request) {
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

        return new BulletinBoardDto(saveBoard);
    }

    public List<BulletinBoardDto> readAll() {
        List<BulletinBoard> boards = bulletinBoardRepository.findAllByOrderByCreateAtDesc();

        return boards.stream().map(bulletinBoard -> new BulletinBoardDto(bulletinBoard)).collect(Collectors.toList());
    }

    public BulletinBoardDto readOne(Long id) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return new BulletinBoardDto(board);
    }

    public ResultDto delete(Long id, PasswordDto passwordDto) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

//        if (isNotSame(passwordDto.getPassword(), board.getPassword())) {
//            return new ResultDto(false);
//        }

        bulletinBoardRepository.deleteById(id);
        return new ResultDto(true);
    }

    @Transactional
    public Message update(Long id, BulletinBoardForm boardForm) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

//        if (isNotSame(boardForm.getPassword(), board.getPassword())) {
//            return new Message(false, null);
//        }

        board.update(boardForm);

        return new Message(true, new BulletinBoardDto(board));
    }

    private boolean isNotSame(String passwordOfDto, String passwordOfEntity) {
        return !passwordOfEntity.equals(passwordOfDto);
    }

}
