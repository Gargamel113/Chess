package sample.logic;

import sample.model.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private boolean playersTurn;
    private String color, name;
    List<Piece> capturedPieces;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.playersTurn = color == "White" ? true : false;
        capturedPieces = new ArrayList<>();
    }

    /**
     *
     * @return Ture if players turn, else false
     */
    public boolean isPlayersTurn() {
        return playersTurn;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    /**
     * Method to get this players currently captured Pieces.
     * @return List capturedPieces
     */
    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void setPlayersTurn() {
        this.playersTurn = !playersTurn;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
