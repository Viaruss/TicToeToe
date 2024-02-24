package com.viarus.tictoetoe;

import com.viarus.tictoetoe.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

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
		};
	}
}
