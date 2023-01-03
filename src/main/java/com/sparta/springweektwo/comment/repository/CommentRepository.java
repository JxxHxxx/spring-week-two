package com.sparta.springweektwo.comment.repository;

import com.sparta.springweektwo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBulletinBoard_Id(Long id);
}
