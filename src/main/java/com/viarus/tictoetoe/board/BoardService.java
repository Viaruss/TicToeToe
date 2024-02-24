package com.viarus.tictoetoe.board;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards(){
        return boardRepository.findAll();
    }

    public Optional<Board> getBoardFromId(String boardId) {
        return boardRepository.findBoardById(boardId);
    }
    public Optional<Board> getBoardFromPlayer(String playerName) {
        return boardRepository.findBoardByPlayerNamesContaining(playerName);
    }
    public boolean doesBoardExist(String playerName) {
        return boardRepository.findBoardByPlayerNamesContaining(playerName).isPresent();
    }
    public void createBoard(Board board){
        boardRepository.insert(board);
    }
    public void joinBoard(Board board){
        boardRepository.save(board);
    }
}
