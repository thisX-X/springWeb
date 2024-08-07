package com.study.tesma.service;

import com.study.tesma.entity.Board;
import com.study.tesma.entity.Comment;
import com.study.tesma.entity.User;
import com.study.tesma.repository.CommentRepository;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements HttpSessionListener {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getComments(int boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        for (Comment comment : comments) {
            User user = commentRepository.findByUserId(comment.getUserId());
            comment.setWriter(user.getName());
        }
        return comments;
    }

    public void commentWrite(int userId, int boardId, String content) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setBoardId(boardId);
        comment.setContent(content);
        commentRepository.save(comment);
    }


}
