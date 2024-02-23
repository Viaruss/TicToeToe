package com.viarus.tictoetoe.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping(path = "get/{boardId}")
    public Board getBoard(@PathVariable("boardId") String boardId){
        return boardService.getBoard(boardId);
    }

}
