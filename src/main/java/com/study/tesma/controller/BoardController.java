package com.study.tesma.controller;


import com.study.tesma.ApiResponse;
import com.study.tesma.entity.Board;
import com.study.tesma.entity.Comment;
import com.study.tesma.entity.User;
import com.study.tesma.service.BoardService;
import com.study.tesma.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(Model model) {
        return "main";
    }

    @GetMapping("/board/{boardName}")
    public String board(Model model, @PathVariable String boardName) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
        }

        List<Board> list = boardService.getBoardList(boardId);

        model.addAttribute("list", list);
        return "board";
    }

    @GetMapping("/board/{boardName}/{id}")
    public String view(@PathVariable String boardName, @PathVariable int id, Model model) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
        }
        Optional<Board> board = boardService.getBoard(boardId, id);
        List<Comment> comments = commentService.getComments(id);
        if (comments == null || comments.isEmpty()) {
            Object commnets = null;
        }

        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        model.addAttribute("currentUrl", currentUrl);
        model.addAttribute("board", board.get());
        model.addAttribute("comments", comments);

        return "boardView";
    }

    @PostMapping("/board/{boardName}")
    public String write(Model model, @PathVariable String boardName, HttpServletRequest request, HttpSession session) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
        }
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        boardService.write(boardId, userId, title, content);

        return "redirect:/board/" + boardName;
    }

    @PatchMapping("/board/{boardName}/{id}")
    public String update(@PathVariable String boardName, @PathVariable int id, Model model, HttpServletRequest request) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
        }
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        boardService.update(title, content, id);

        return "redirect:/board/"+boardName+"/"+id;
    }

    @DeleteMapping("/board/{boardName}/{id}")
    public String delete(Model model, @PathVariable String boardName, @PathVariable int id) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
        }
        boardService.delete(id);

        return "redirect:/board/" + boardName;
    }

    /* 상태코드와 메시지로만(url이동 x) 전송하는 방법
    @DeleteMapping("/board/{boardName}/{id}")
    public ResponseEntity<ApiResponse> delete(Model model, @PathVariable String boardName, @PathVariable int id) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
            default -> {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Invalid board name"), HttpStatus.BAD_REQUEST);
            }
        }

        boardService.delete(id);

        ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Deleted successfully from " + boardName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    */
}