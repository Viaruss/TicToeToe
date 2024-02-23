package com.viarus.tictoetoe;

import com.viarus.tictoetoe.board.Board;
import com.viarus.tictoetoe.board.BoardFields;
import com.viarus.tictoetoe.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class TicToeToeApplication {

	@Autowired
	BoardRepository boardRepository;
	public static void main(String[] args) {
		SpringApplication.run(TicToeToeApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(){
		return args -> {
			Board board = new Board(
					new BoardFields("x","o","x","o","x","o","x","o","x"),
					List.of("abc653", "awn531"),
					"X",
					false
			);
			//boardRepository.insert(board);
			boardRepository.findBoardById("65d7d3b6e3ca2f5099722ada")
					.ifPresent(s -> System.out.println(s));
		};
	}
}
