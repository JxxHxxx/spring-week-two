package com.sparta.springweektwo.comment.service;

import com.sparta.springweektwo.bulletinboard.entity.BulletinBoard;
import com.sparta.springweektwo.bulletinboard.repository.BulletinBoardRepository;
import com.sparta.springweektwo.comment.dto.CommentForm;
import com.sparta.springweektwo.comment.dto.DeleteMessage;
import com.sparta.springweektwo.comment.entity.Comment;
import com.sparta.springweektwo.comment.repository.CommentRepository;
import com.sparta.springweektwo.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BulletinBoardRepository bulletinBoardRepository;
    private final JwtUtil jwtUtil;

    public CommentForm write(Long boardId, CommentForm commentForm, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (!jwtUtil.validateToken(token)) { // 유효한 토큰 검사
            return null;
        }
        BulletinBoard bulletinBoard = bulletinBoardRepository.findById(boardId).orElseThrow(); // 게시글 유무 확인

        Comment comment = new Comment(commentForm, bulletinBoard);
        commentRepository.save(comment);

        return commentForm;
    }

    @Transactional
    public CommentForm update(Long commentId, CommentForm commentForm, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (!jwtUtil.validateToken(token)) { // 유효한 토큰 검사
            return null;
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Comment updateComment = comment.update(commentForm.getBody());

        return new CommentForm(updateComment);
    }

    @Transactional
    public DeleteMessage softDelete(Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (!jwtUtil.validateToken(token)) { // 유효한 토큰 검사
            return new DeleteMessage("토큰이 유효하지 않습니다.", BAD_REQUEST);
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setSoftDelete();

        return new DeleteMessage("삭제 성공", OK);
    }

    public List<Comment> readAll(Long id) {
        List<Comment> comments = commentRepository.findByBulletinBoard_Id(id);
        comments.forEach(comment -> log.info("comment : body = {}, isDeleted = {}", comment.getBody(), comment.getIsDeleted()));
        return comments.stream().filter(comment -> comment.getIsDeleted() != true).collect(Collectors.toList());

    }
}
