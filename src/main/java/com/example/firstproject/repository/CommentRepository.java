package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조희
    @Query(value = "SELECT * FROM carcamping.comment where article_id = :articleId", nativeQuery = true)
    List<Comment> findByArticleId(@Param("articleId") Long articleId);

    // 특정 닉네임의 모든 댓글 조희
    List<Comment> findByNickname(@Param("nickname") String nickname);


}

