package com.study.tesma.service;

import com.study.tesma.entity.Board;
import com.study.tesma.entity.User;
import com.study.tesma.repository.BoardRepository;
import com.study.tesma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Board> getBoardList(String boardName) {
        List<Board> boards = boardRepository.findTop5ByBoardNameOrderByCreateAtDesc(boardName);
        // List<Sample> findByGenderOrderByAgeDesc(String gender);
        //List<Sample> findAllByOrderByAgeDesc();

        for (Board board : boards) {
            User user = boardRepository.findByUserId(board.getUserId());
            board.setWriter(user.getName());
        }

        return boards;
    }

    public void write(String boardName, int userId, Integer fileId, String title, String content) {
        Board board = new Board();
        board.setBoardName(boardName);
        board.setUserId(userId);
        board.setFileId(fileId);
        board.setTitle(title);
        board.setContent(content);

        boardRepository.save(board);
    }

    public void delete(int id) {
        boardRepository.deleteById(id);
    }

    public Optional<Board> getBoard(String boardName, int id) {
        Optional<Board> board = boardRepository.findById(id);
        User user = boardRepository.findByUserId(board.get().getUserId());
        board.get().setWriter(user.getName());

        return board;
    }

    public void update(String title, String content, int id) {
        Optional<Board> board = boardRepository.findById(id);
        Board updateBoard = board.get();
        updateBoard.setTitle(title);
        updateBoard.setContent(content);
        boardRepository.save(updateBoard);
    }

    public List<Board> getAllboard() {
        List<Board> boards = boardRepository.findAll();
        for (Board board : boards) {
            User user = boardRepository.findByUserId(board.getUserId());
            board.setWriter(user.getName());
        }
        return boards;
    }

    public List<Board> findByUserId(int userId) {
        List<Board> boards = boardRepository.findAllByUserId(userId);
        for (Board board : boards) {
            User user = boardRepository.findByUserId(board.getUserId());
            board.setWriter(user.getName());
        }
        return boards;
    }
}
