package sample.logic;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sample.Cell;

public class Board extends GridPane {

    private Group group;
    public static Cell[][] gameBoard = new Cell[8][8];

    public Board() {
        group = new Group();
        gameBoardInit();
        group.getChildren().add(this);
        setHeight(90);
        setWidth(90);
    }

    /**
     * Initializes the gameboard by creating all the cells,
     * and giving them the correct Letter, Number and Color
     */
    private void gameBoardInit() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String cellName = getCellName(j) + (8 - i);
                Cell cell = new Cell(80, 80, (i + j) % 2 == 0 ? Color.valueOf("#6A4B35") : Color.valueOf("#CDBDB1"), cellName, j, i);
                gameBoard[i][j] = cell;
                add(gameBoard[i][j].getCell(), j, i);
            }
        }
    }

    /**
     *
     * @return The current gameboard as a GridPane
     */
    public GridPane getGameBoard() {
        return this;
    }

    /**
     *
     * @return the gameboard as a 2d array
     */
    public static Cell[][] getGameCells(){
        return gameBoard;
    }

    /**
     *
     * @param index For the current column index to calcualte correct Letter.
     * @return The correct letter of the column
     */
    private String getCellName(int index) {
        switch (index) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
            default:
                return index + " unknown";
        }
    }


}
