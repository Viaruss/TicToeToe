package com.viarus.tictoetoe.board;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Board {
    @Id
    private String id;
    private BoardFields fields;
    private List<String> playerNames;
    private String nowMoving;
    private boolean finished = false;

    public Board() {
    }

    public Board(BoardFields fields, List<String> playerNames, String nowMoving, boolean finished) {
        this.fields = fields;
        this.finished = finished;
        this.playerNames = playerNames;
        this.nowMoving = nowMoving;
    }

    public String getId() {
        return id;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public BoardFields getFields() {
        return fields;
    }

    public void setFields(BoardFields fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return String.format(
                """
                        board id: %s
                        %s | %s | %s
                        %s | %s | %s
                        %s | %s | %s
                        player ids:
                        %s
                        %s
                        now moving: %s""",
                id,
                fields.getA1(), fields.getA2(), fields.getA3(),
                fields.getB1(), fields.getB2(), fields.getB3(),
                fields.getC1(), fields.getC2(), fields.getC3(),
                playerNames.get(0), playerNames.get(1),
                nowMoving
        );
    }
}