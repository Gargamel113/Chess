package sample.AI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sample.Cell;
import sample.logic.Board;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hand {
    String color;
    Cell[][] board;
    List<Cell> piecesAlive;
    Robot bot;
    public Hand(String color){
        this.color = color;
        this.board = Board.getGameCells();
        this.piecesAlive = new ArrayList<>();
        try {
            bot = new Robot();
        } catch (AWTException e){

        }
    }

    public void makeMove(){
        findOwnPieces();
        System.out.println("CALCULATE MOVE");
        //Make random move
        int ran = (int) (Math.random()*piecesAlive.size());
        Cell cell = piecesAlive.get(ran);
        System.out.println(cell.getId());
        cell.setSelected();


    }

    public void getMouseEvent(EventHandler<MouseEvent> m) {

    }

    private void findOwnPieces(){
        piecesAlive.removeAll(piecesAlive);

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j].getPiece() != null){

                    if(color.equals("White")){
                        if (board[i][j].getPiece().getIsWhite()){
                            piecesAlive.add(board[i][j]);
                        }
                    } else {
                        if (!board[i][j].getPiece().getIsWhite()){
                            piecesAlive.add(board[i][j]);
                        }
                    }

                }

            }
        }

    }

}
