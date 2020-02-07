package sample;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.events.EventException;
import sample.logic.Game;
import sample.model.Piece;
import java.util.ArrayList;
import java.util.List;

public class Cell extends VBox {

    private Piece piece;
    private Group group = new Group();
    private Group baseTile = new Group();
    private Group pieceDisplay = new Group();
    private Rectangle tile;
    public Circle movePointer;
    private String cellId;
    private boolean isSelected, eventIsActive;
    private List<Cell> triggeredEvents;
    private int x, y;

    public Cell(int width, int height, Color color, String id, int x, int y) {
        this.cellId = id;
        this.isSelected = false;
        this.eventIsActive = false;
        this.piece = null;
        this.x = x;
        this.y = y;
        initCell(width, height, color);
        triggeredEvents = new ArrayList<>();
    }

    /**
     * Method for initializeing cell
     * @param width The width of each cell
     * @param height The height of each cell
     * @param color The color of each cell
     */
    private void initCell(int width, int height, Color color) {
        this.setId(cellId);

        tile = new Rectangle(width, height, color);
        tile.setStroke(Color.BLACK);
        tile.setStrokeWidth(1);

        movePointer = new Circle(20, Color.rgb(0, 0, 0, 0.5));
        movePointer.setTranslateX(40);
        movePointer.setTranslateY(40);
        movePointer.setOpacity(0);

        baseTile.getChildren().add(tile);
        baseTile.getChildren().add(movePointer);
        baseTile.getChildren().add(new Label(cellId));

        group.getChildren().addAll(baseTile, pieceDisplay);
        getChildren().add(group);
    }


    public Cell getCell() {
        return this;
    }

    /**
     * @return The X position of the cell. The X position is in the 2d array
     */
    public int getX(){
        return x;
    }

    /**
     *
     * @return The Y position of the cell. The Y position is in the 2d array
     */
    public int getY(){
        return y;
    }

    /**
     * @return Return if the cell is selected
     */
    public boolean getIsSelected() {
        return isSelected;
    }

    /**
     *
     * @param piece The new piece to set to this cell
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    /**
     * @return True if this cell is selected. False if not
     */
    public boolean getEventIsActive(){
        return eventIsActive;
    }

    /**
     * Method for setting this cell to be selected
     * @See update()
     */
    public void setSelected() {
        isSelected = !isSelected;
        this.update();
    }

    /**
     * Method for changing border to show that the current
     * cell is selected.
     */
    public void update() {
        if (isSelected) {
            tile.setStrokeWidth(1);
            tile.setStroke(Color.YELLOW);

            for(Cell cell : piece.getMoveList()){
                cell.addEvent();
            }
        } else {
            tile.setStrokeWidth(1);
            tile.setStroke(Color.BLACK);

            for(Cell cell : piece.getMoveList()){
                cell.removeEvent();
            }
        }
    }

    /**
     * Adds eventhandler to this cell and shows a circle where player can move
     * @See moveTo()
     */
    public void addEvent(){
        eventIsActive = true;
        movePointer.setOpacity(0.15);               //KANSKE LÄGGA TILL KNAPP FÖR AV OCH PÅ
        addEventFilter(MouseEvent.MOUSE_CLICKED, moveTo);
    }

    /**
     * Removes eventhandler if precent. and hides circle that shows player move
     */
    public void removeEvent(){
        eventIsActive = false;
        movePointer.setOpacity(0);
        try {
            removeEventFilter(MouseEvent.MOUSE_CLICKED, moveTo);
        } catch (EventException e){

        }
    }

    /**
     * Method checks if there is a piece in the cell or not
     * clears the children. this is so the pieceDisplay group is empty
     * The adds new piece to the group
     */
    public void updatePiece() {
        if (slotContainsPiece()) {
            pieceDisplay.getChildren().clear();
            pieceDisplay.getChildren().add(piece);
        }
    }

    /**
     * Eventhandler  Mouseevent
     * Method that gets added to every Cell depending on Selected Piece moves
     * @See findSelectedCell()
     * @See deleteAllEvents()
     * @See Game.playerMoved()
     */
    EventHandler<MouseEvent> moveTo = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Cell oldCell = findSelectedCell();
            oldCell.piece.movePiece((Cell) event.getSource());
            oldCell.setSelected();
            oldCell.setPiece(null);
            deleteAllEvents();
            Game.playerMoved();
        }
    };

    /**
     * Runs A loop trough every cell in a 2d array
     * and finds the currently active cell
     * @return The currently active cell
     */
    private Cell findSelectedCell(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (Game.cells[i][j].getIsSelected()){
                    return Game.cells[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Checks if cell contains Piece or not
     * @return Piece if present.
     */
    private boolean slotContainsPiece(){
        if (piece != null) {
            return true;
        }
        return false;
    }

    /**
     * This methods deletes all move events
     * I.e the events that get added to every cell that is within
     * the selected Pieces moves
     */
    private void deleteAllEvents(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Cell cell = Game.cells[i][j];
                if (cell.getEventIsActive()){
                    cell.removeEvent();
                }
            }
        }
    }

}
