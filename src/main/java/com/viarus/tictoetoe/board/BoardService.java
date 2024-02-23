package com.viarus.tictoetoe.board;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards(){
        return boardRepository.findAll();
    }

    public Board getBoard(String boardId) {
        return boardRepository.findById(boardId).isPresent() ?
                boardRepository.findBoardById(boardId).get() : new Board();
    }
}
