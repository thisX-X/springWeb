package com.study.tesma.service;

import com.study.tesma.entity.Board;
import com.study.tesma.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> getBoardList(int boardId) {
        return boardRepository.findAllByBoardId(boardId);
    }

    public void write(int boardId, String title, String content) {
        Board board = new Board();
        board.setBoardId(boardId);
        board.setTitle(title);
        board.setContent(content);

        boardRepository.save(board);
    }

    public void delete(int id) {
        boardRepository.deleteById(id);
    }

    public Optional<Board> getBoard(int boardId, int id) {

        return boardRepository.findById(id);
    }

    public void update(String title, String content, int id) {
        Optional<Board> board = boardRepository.findById(id);
        Board updateBoard = board.get();
        updateBoard.setTitle(title);
        updateBoard.setContent(content);
        boardRepository.save(updateBoard);
    }
}
