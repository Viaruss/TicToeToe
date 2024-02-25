package com.viarus.tictoetoe.board;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Board {
    @Id
    private String id;
    private List<String> fields;
    private List<String> playerNames;
    private String nowMoving;
    private boolean finished = false;

    public Board() {
    }

    public Board(List<String> fields, List<String> playerNames, String nowMoving, boolean finished) {
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

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getNowMoving() {
        return nowMoving;
    }

    public void setNowMoving(String nowMoving) {
        this.nowMoving = nowMoving;
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
                fields.get(0), fields.get(1), fields.get(2),
                fields.get(3), fields.get(4), fields.get(5),
                fields.get(6), fields.get(7), fields.get(8),
                playerNames.get(0), playerNames.get(1),
                nowMoving
        );
    }

    public void makeMove(String fieldIndex) {
        fields.set(Integer.valueOf(fieldIndex), nowMoving);
        nowMoving = nowMoving.equals("X") ? "O" : "X";
        System.out.println("made move at index " + fieldIndex + " now moving: " + nowMoving);
    }
}