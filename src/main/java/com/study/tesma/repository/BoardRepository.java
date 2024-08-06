package com.study.tesma.repository;

import com.study.tesma.entity.Board;
import com.study.tesma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT b FROM Board b WHERE b.boardName = :boardName")
    List<Board> findAllByBoardName(@Param("boardName") String boardName);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findByUserId(@Param("userId") int userId);

    List<Board> findTop5ByBoardNameOrderByCreateAtDesc(String boardName);

    @Query("SELECT b FROM Board b WHERE b.userId = :userId")
    List<Board> findAllByUserId(@Param("userId") int userId);
}
