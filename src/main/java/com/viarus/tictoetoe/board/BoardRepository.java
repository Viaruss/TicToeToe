package com.viarus.tictoetoe.board;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, String> {
    Optional<Board> findBoardByPlayerNamesContaining(String playerName);
    Optional<Board> findBoardById(String id);
}
