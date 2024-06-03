package com.example.comment_service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.comment_service.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    public Iterable<Comment> getCommentsByNewsId(Long news_id);
}
