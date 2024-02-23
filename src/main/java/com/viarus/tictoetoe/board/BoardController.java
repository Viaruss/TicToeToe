package com.viarus.tictoetoe.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/board")
public class BoardController {

    private final BoardService boardService;
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping(path = "getAll")
    public List<Board> getAllBoards(){
        return boardService.getAllBoards();
    }
    @GetMapping(path = "get/fromId/{boardId}")
    public Board getBoardFromId(@PathVariable("boardId") String boardId){
        Optional<Board> board = boardService.getBoardFromId(boardId);
        return board.orElse(null);
    }

    @GetMapping(path = "get/fromPlayer/{playerName}")
    public Board getBoardFromPlayer(@PathVariable("playerName") String playerName){
        Optional<Board> board = boardService.getBoardFromPlayer(playerName);
        return board.orElse(null);
    }
}
