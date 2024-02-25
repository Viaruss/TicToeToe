package com.viarus.tictoetoe;

import com.viarus.tictoetoe.board.BoardRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TicToeToeApplication {

	final BoardRepository boardRepository;
	public TicToeToeApplication(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(TicToeToeApplication.class, args);
	}
}
