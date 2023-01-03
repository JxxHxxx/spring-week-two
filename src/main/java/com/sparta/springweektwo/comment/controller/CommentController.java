package com.sparta.springweektwo.comment.controller;

import com.sparta.springweektwo.comment.dto.CommentForm;
import com.sparta.springweektwo.comment.dto.DeleteMessage;
import com.sparta.springweektwo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/bulletin-boards/{board-id}/comments")
    public CommentForm write(@PathVariable(name = "board-id") Long boardId, @RequestBody CommentForm commentForm, HttpServletRequest request) {
        return commentService.write(boardId, commentForm, request);
    }

    //댓글 수정
    @PatchMapping("/bulletin-boards/{board-id}/comments/{comment-id}")
    public CommentForm update(@PathVariable(name = "comment-id") Long commentId, @RequestBody CommentForm commentForm, HttpServletRequest request) {
        return commentService.update(commentId, commentForm, request);
    }

    //댓글 삭제
    @DeleteMapping("/bulletin-boards/{board-id}/comments/{comment-id}")
    public ResponseEntity<DeleteMessage> softDelete(@PathVariable(name = "comment-id") Long commentId, HttpServletRequest request) {
        DeleteMessage deleteMessage = commentService.softDelete(commentId, request);
        return new ResponseEntity<>(deleteMessage, deleteMessage.getHttpStatus());
    }
}
