package sample.logic;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import sample.AI.Hand;
import sample.Cell;
import sample.model.*;


public class Game {
    private static Player p1;
    private static Player p2;
    Hand hand = new Hand("Black");


    BorderPane border;
    Board chessBoard;
    private GridPane playerOneGui, playerTwoGui;
    public static Cell[][] cells;

    public Game(){
        border = new BorderPane();
        chessBoard = new Board();
        cells = chessBoard.getGameCells();
        playerOneGui = new GridPane();
        playerTwoGui = new GridPane();
        border.setCenter(chessBoard.getGameBoard());
        border.setLeft(playerOneGui);
        border.setRight(playerTwoGui);
        setup();
        playerGuiSetup();
        this.p1 = new Player("Robbin", "White");
        this.p2 = new Player("Sofie", "Black");
    }

    /**
     *
     * @return The BorderPane that holds the gameboard
     * To be used to add player stats and GUI.
     */
    public BorderPane getBorder(){
        return border;
    }

    /**
     * Method for setting up all the game Pieces on the board in there correct spot
     */
    private void setup(){
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                cells[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, onClick);
                piecePlacement(i, j);
            }
        }
    }

    private void playerGuiSetup(){
        border.setTop(new Label("GAME OF CHESS"));

        border.getTop().prefHeight(200);

        playerOneGui.add(new Label("Player 1"), 0, 0, 4, 2);
        playerOneGui.setMinWidth(130);

        playerTwoGui.setMaxWidth(130);
        playerTwoGui.setPrefWidth(120);

        playerTwoGui.add(new Label("Player 2"), 0, 0, 4, 2);

    }

    //TEMPORARY METHOD

    /**
     * The method that acctually calculates were the Piece should be
     * @param i The row index in the 2d array
     * @param j The column index in the 2d array
     */
    private void piecePlacement(int i, int j) {
        //SETUP BLACK PIECES
        if (i == 0){
            if (j == 0 || j == 7){
                cells[i][j].setPiece(new Rook(false, i, j));
            } else if (j == 1 || j == 6){
                cells[i][j].setPiece(new Knight(false, i, j));
            } else if (j == 2 || j == 5){
                cells[i][j].setPiece(new Bishop(false, i, j));
            } else if (j == 3){
                cells[i][j].setPiece(new Queen(false, i, j));
            } else if (j == 4){
                cells[i][j].setPiece(new King(false, i, j));
            }
        }else if (i <= 1) {
            cells[i][j].setPiece(new Pawn(false, i, j));
        }

        //SETUP WHITE PIECES
        if (i == 7) {
            if (j == 0 || j == 7){
                cells[i][j].setPiece(new Rook(true, i, j));
            } else if (j == 1 || j == 6){
                cells[i][j].setPiece(new Knight(true, i, j));
            } else if (j == 2 || j == 5){
                cells[i][j].setPiece(new Bishop(true, i, j));
            } else if (j == 3){
                cells[i][j].setPiece(new Queen(true, i, j));
            } else if (j == 4){
                cells[i][j].setPiece(new King(true, i, j));
            }
        } else if (i == 6){
            cells[i][j].setPiece(new Pawn(true, i, j));
        }
        cells[i][j].updatePiece();
    }

    //CHECKS IF ANY OTHER THAN THE CLICKED CELL IS SELECTED
    //IF SO IT DESELECTS ALL CLICKED CELLS EXCEPT THE CURRENT.

    /**
     * Method for deselecting every cell that should not be active
     * @param cell The currently active cell not to deselect
     * @return
     */
    private void autoDeselection(Cell cell){
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                Cell temp = cells[i][j];
                if(temp.getIsSelected() && !temp.equals(cell)){
                    temp.setSelected();
                }
            }
        }
    }


    /**
     * @See Cell Class
     * Method for selecting a new cell and marking it as selected
     * @param cell The current cell to update
     * @return Only returns true if arg is not null
     */
    private boolean updateCell(Cell cell){
        if (cell.getPiece() != null){
            autoDeselection(cell);
            cell.setSelected();
            return true;
        }
        return false;
    }

    //MAIN MOUSE CLICK EVENT
    /**
     * Mouse click event to handle cell selection.
     * This method only handles when the user select a gameboard cell
     * and calculates who´s turn it is and also auto deselects everything else
     */
    EventHandler<MouseEvent> onClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Cell cell = ((Cell) event.getSource()).getCell();
            if (isClickable(cell)){

                if (getPlayersTurn() && cell.getPiece().getIsWhite()){
                    cell.getPiece().canPieceMove();
                    updateCell(cell);
                } else if (!getPlayersTurn() && !cell.getPiece().getIsWhite()){
                    cell.getPiece().canPieceMove();
                    updateCell(cell);
                }

            } else if (cell.getEventIsActive()) {

            } else {
                updateCell(cell);
            }
        }
    };

    /**
     *
     * @param cell the current cell to check if the user can press or not
     * @return Either true or false based on event, isSelected and has Piece or not
     */
    private boolean isClickable(Cell cell){
        return !cell.getEventIsActive() && !cell.getIsSelected() && cell.getPiece() != null;
    }

    /**
     * Small mehod for checking who´s turn it is
     * @return True for white, False for black
     */
    private boolean getPlayersTurn(){
        if(p1.isPlayersTurn()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * @See Player Class
     * changes the boolean playersTurn in the respective class
     */
    public static void playerMoved(){
        p1.setPlayersTurn();
        p2.setPlayersTurn();
    }
}
