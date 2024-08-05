package com.study.tesma.controller;


import com.study.tesma.ApiResponse;
import com.study.tesma.entity.Board;
import com.study.tesma.entity.Comment;
import com.study.tesma.entity.File;
import com.study.tesma.entity.User;
import com.study.tesma.repository.FileRepository;
import com.study.tesma.service.BoardService;
import com.study.tesma.service.CommentService;
import com.study.tesma.service.FileService;
import com.study.tesma.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(Model model) {
        return "header";
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Board> boards = boardService.getAllboard();

        // 게시판 이름 설정
        for (Board board : boards) {
            switch (board.getBoardId()) {
                case 1 -> board.setBoardName("free");
                case 2 -> board.setBoardName("notice");
                case 3 -> board.setBoardName("question");
            }
        }

        // 처음 5개의 요소만 가져오기
        List<Board> AttBoard = new ArrayList<>();
        int size = Math.min(boards.size(), 5);

        // 리스트를 역순으로 정렬
        Collections.reverse(boards);

        for (int i = 0; i < size; i++) {
            AttBoard.add(boards.get(i));
        }

        // 모델에 AttBoard 리스트 추가
        model.addAttribute("boards", AttBoard);
        return "main";
    }

    @GetMapping("/board/{boardName}/{id}/update")
    public String updateBoard(@PathVariable String boardName, @PathVariable int id, Model model) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
            case "question" -> boardId = 3;
        }
        Optional<Board> board = boardService.getBoard(boardId, id);
        List<Comment> comments = commentService.getComments(id);
        if (comments == null || comments.isEmpty()) {
            Object commnets = null;
        }
        if (board.get().getFileId() != null) {
            File file = fileService.findById(board.get().getFileId());
            model.addAttribute("file", file);
        }
        File file = null;
        if (board.get().getFileId() != null) {
            file = fileService.findById(board.get().getFileId());
        }

        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        model.addAttribute("currentUrl", currentUrl);
        model.addAttribute("board", board.get());
        model.addAttribute("file", file);
        model.addAttribute("comments", comments);

        return "boardWrite";
    }

    @GetMapping("/grade")
    public String grade(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("user");
        if (user.getGrade() != 1) {
            return "redirect:/login";
        }
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        return "gradeMain";
    }

    @GetMapping("/board/{boardName}")
    public String board(HttpServletRequest request, Model model, @PathVariable String boardName) {
        String currentUrl = request.getRequestURL().toString();
        model.addAttribute("currentUrl", currentUrl);

        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
            case "question" -> boardId = 3;
        }

        List<Board> list = boardService.getBoardList(boardId);

        model.addAttribute("list", list);
        return "board";
    }

    @GetMapping("/board/me")
    public String me(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        int userId = user.getId();
        List<Board> boards = boardService.findByUserId(userId);
        model.addAttribute("list", boards);
        return "board";
    }

    @GetMapping("/board/{boardName}/{id}")
    public String view(@PathVariable String boardName, @PathVariable int id, Model model) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
            case "question" -> boardId = 3;
        }
        Optional<Board> board = boardService.getBoard(boardId, id);
        List<Comment> comments = commentService.getComments(id);
        if (comments == null || comments.isEmpty()) {
            Object commnets = null;
        }
        if (board.get().getFileId() != null) {
            File file = fileService.findById(board.get().getFileId());
            model.addAttribute("file", file);
        }

        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        model.addAttribute("currentUrl", currentUrl);
        model.addAttribute("board", board.get());
        model.addAttribute("comments", comments);

        return "boardView";
    }

    @GetMapping("/board/{boardName}/write")
    public String write(Model model, @PathVariable String boardName) {
        model.addAttribute("boardName", boardName);
        return "boardWrite";
    }

    @PostMapping("/board/{boardName}")
    public String write(Model model, @PathVariable String boardName, HttpServletRequest request, HttpSession session, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        Integer fileId = null;
        if (!file.isEmpty()) {
            try {
                File savedFile = fileService.storeFile(file);
                fileId = savedFile.getId();

                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int boardId = 0;

        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
            case "question" -> boardId = 3;
        }
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        boardService.write(boardId, userId, fileId, title, content);

        return "redirect:/board/" + boardName;
    }

    @PostMapping("/board/{boardName}/{id}/comment")
    public String commentWrite(Model model, @PathVariable String boardName, @PathVariable int id, HttpServletRequest request, HttpSession session) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
            case "question" -> boardId = 3;
        }
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        String content = request.getParameter("content");
        commentService.commentWrite(userId, id, content);
        return "redirect:/board/" + boardName + "/" + id;
    }

    @PatchMapping("/board/{boardName}/{id}")
    public String update(@PathVariable String boardName, @PathVariable int id, Model model, HttpServletRequest request) {
        int boardId = 0;
        switch (boardName) {
            case "free" -> boardId = 1;
            case "notice" -> boardId = 2;
            case "question" -> boardId = 3;
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
            case "question" -> boardId = 3;
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