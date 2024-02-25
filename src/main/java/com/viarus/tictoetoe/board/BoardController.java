package com.viarus.tictoetoe.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
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
    @GetMapping(path = "ifExists/fromPlayer/{playerName}")
    public boolean checkIfExists(@PathVariable("playerName") String playerName) {
        return boardService.doesBoardExist(playerName);
    }

    @GetMapping(path = "get/new/{playerName}")
    public Board createBoard(@PathVariable("playerName") String playerName){
        Board board = new Board(
                List.of("","","","","","","","",""),
				List.of(playerName),
				"X",
				false
        );
        boardService.createBoard(board);
        return board;
    }
    @GetMapping(path = "/joinGame/{playerName}/{joiningPlayerName}")
    public boolean joinGame(@PathVariable("playerName") String playerName, @PathVariable("joiningPlayerName") String joiningPlayerName) {
        try{
            Board board = boardService.getBoardFromPlayer(playerName).orElseThrow();
            board.setPlayerNames(List.of(playerName, joiningPlayerName));
            boardService.updateBoard(board);
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }
    @GetMapping(path = "/move/{boardId}/{fieldIndex}")
    public Board makeMove(@PathVariable("boardId") String boardId, @PathVariable("fieldIndex") String fieldIndex) {
        try{
            Board board = boardService.getBoardFromId(boardId).orElseThrow();
            board.makeMove(fieldIndex);
            boardService.updateBoard(board);
            return board;
        } catch (NoSuchElementException e){
            return null;
        }
    }
}
