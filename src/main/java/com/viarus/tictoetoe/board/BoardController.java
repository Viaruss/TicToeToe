package com.viarus.tictoetoe.board;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/board")
public class BoardController {

    private final BoardService boardService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    public BoardController(BoardService boardService, SimpMessagingTemplate simpMessagingTemplate) {
        this.boardService = boardService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    @GetMapping(path = "{playerName}")
    public Board getBoardFromPlayer(@PathVariable("playerName") String playerName){
        Optional<Board> board = boardService.getBoardFromPlayer(playerName);
        return board.orElse(null);
    }
    @GetMapping(path = "exist/{playerName}")
    public boolean checkIfExists(@PathVariable("playerName") String playerName) {
        return boardService.doesBoardExist(playerName);
    }

    @PostMapping()
    public boolean createBoard(@RequestBody String playerName){
        Board board = new Board(
                List.of("","","","","","","","",""),
				List.of(playerName.substring(1, playerName.length()-1)),
				"X",
				""
        );
        boardService.createBoard(board);
        return true;
    }
    @PostMapping(path = "/{playerName}")
    public boolean joinGame(@PathVariable("playerName") String playerName, @RequestBody() String joiningPlayerName) {
        joiningPlayerName = joiningPlayerName.substring(1, joiningPlayerName.length()-1);
        try{
            Board board = boardService.getBoardFromPlayer(playerName).orElseThrow();
            board.setPlayerNames(List.of(playerName, joiningPlayerName));
            boardService.updateBoard(board);
            simpMessagingTemplate.convertAndSend("/topic/gameplay/" + board.getId(), board);
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }
    @PostMapping(path = "/move/{boardId}")
    public boolean makeMove(@PathVariable("boardId") String boardId, @RequestBody() String fieldIndex) {
        fieldIndex = fieldIndex.substring(1, fieldIndex.length()-1);
        try{
            Board board = boardService.getBoardFromId(boardId).orElseThrow();
            board.makeMove(fieldIndex);
            boardService.updateBoard(board);
            simpMessagingTemplate.convertAndSend("/topic/gameplay/" + boardId, board);
            if(!board.getState().isEmpty()) boardService.deleteBoard(board.getId());
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }
}
