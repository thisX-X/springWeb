package com.study.tesma.repository;

import com.study.tesma.entity.Comment;
import com.study.tesma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE c.boardId = :boardId")
    List<Comment> findAllByBoardId(@Param("boardId") int boardId);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findByUserId(@Param("userId") int userId);
}
