package com.study.tesma.controller;

import com.study.tesma.entity.Board;
import com.study.tesma.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardAPIController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/boardAPI/{boardName}")
    public List<Board> board(HttpServletRequest request, Model model, @PathVariable String boardName) {
        List<Board> boardList = boardService.getBoardList(boardName);
        return boardList;
    }
}
