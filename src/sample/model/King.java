package sample.model;

import sample.Cell;
import sample.logic.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class King extends Piece {

    int xPos, yPos, x2, y2;
    boolean isChecked;
    private List<Cell> moveList;
    private List<Cell> possibleThreat = new ArrayList<>();

    public King(boolean isWhite, int x, int y) {
        super(isWhite ? "D:\\Development\\Java\\Projects\\Chess\\graphics\\JohnPablok Cburnett Chess set\\PNGs\\With Shadow\\128px\\w_king_png_shadow_128px.png" :
                        "D:\\Development\\Java\\Projects\\Chess\\graphics\\JohnPablok Cburnett Chess set\\PNGs\\With Shadow\\128px\\b_king_png_shadow_128px.png"
                , isWhite);
        this.yPos = x;
        this.xPos = y;
        this.isChecked = false;
        moveList = new ArrayList<>();
        setImage(getPieceModel());
        setTranslateX(12);
        setTranslateY(10);
    }

    /**
     * Calculates if there is a threat to the king or not
     * @See findThreats(int x, int y)
     */
    private void calculateThreat() {
        possibleThreat.removeAll(possibleThreat);

        //CHECK UP_RIGHT
        findThreats(1, -1);

        //CHECK UP_LEFT
        findThreats(-1, -1);

        //CHECK DOWN_RIGHT
        findThreats(1, 1);

        //CHECK DOWN_LEFT
        findThreats(-1, 1);

        //CHECK UP
        findThreats(0, -1);

        //CHECK DOWN
        findThreats(0, 1);

        //CHECK RIGHT
        findThreats(1, 0);

        //CHECK LEFT
        findThreats(-1, 0);


        System.out.println(possibleThreat.size());
    }

    @Override
    public List<Cell> getMoveList() {
        return moveList;
    }


    @Override
    Piece getPiece() {
        return this;
    }

    private boolean canGetCaptured() {
        //ÄNDRAD MÅSTE KOLLAS ÖVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for(Cell cell : possibleThreat){
            cell.getPiece().calculatePossibleMoves();
            for(Cell cell2 : cell.getPiece().getMoveList()){
                if(Game.cells[yPos][xPos].getId().equals(cell2.getId())){
                    System.out.println("Possible threat");
                    return true;
                }
            }
            cell.getPiece().deleteMoveList();
        }
        System.out.println("No threat");
        return false;
    }

    /**
     * Method that checks if a player can move or not
     * @See calculatePossibleMoves()
     * @return true if move is possible else false
     */
    @Override
    public boolean canPieceMove() {
        //DO ALL THE CALCULATIONS IN HERE LATER ON
//        calculateThreat();
        calculatePossibleMoves();
        return false;
    }

    @Override
    boolean isPieceCoveringKing() {
        calculateThreat();
        if(canGetCaptured()){
            return true;
        }
        return false;
    }

    /**
     * Method that checks the input cell if it contains an
     * enemy piece. and if so adds it to the Piece enemy move list
     * @param toCell The cell to check what it contains
     * @return true if enemy piece else false
     */
    @Override
    boolean canCaptureEnemy(Cell toCell) {
        if (toCell.getPiece() != null && isEnemy(toCell)) {
            moveList.add(toCell);
            return true;
        }
        return false;

    }

    @Override
    boolean captureEnemyPiece(Cell toCell) {
        return false;
    }

    @Override
    void pieceCantMove() {

    }

    /**
     * Calculates possible move for Piece
     * @See move(int x, int y)
     * @See deleteMoveList()
     */
    @Override
    void calculatePossibleMoves() {


        //CHECK UP_RIGHT
        move(1, -1);
//        if (isInsideBoard(xPos + 1, yPos - 1)) {
//            moveUpRight(xPos + 1, yPos - 1);
//        }

        //CHECK UP_LEFT
        move(-1, -1);
//        if (isInsideBoard(xPos - 1, yPos - 1)) {
//            moveUpLeft(xPos - 1, yPos - 1);
//        }

        //CHECK DOWN_RIGHT
        move(1, 1);
//        if (isInsideBoard(xPos + 1, yPos + 1)) {
//            moveDownRight(xPos + 1, yPos + 1);
//        }

        //CHECK DOWN_LEFT
        move(-1, 1);
//        if (isInsideBoard(xPos - 1, yPos + 1)) {
//            moveDownLeft(xPos - 1, yPos + 1);
//        }

        //CHECK UP
        move(0, -1);
//        if (isInsideBoard(xPos, yPos - 1)) {
//            moveUp(xPos, yPos - 1);
//        }

        //CHECK DOWN
        move(0, 1);
//        if (isInsideBoard(xPos, yPos + 1)) {
//            moveDown(xPos, yPos + 1);
//        }

        //CHECK RIGHT
        move(1, 0);
//        if (isInsideBoard(xPos + 1, yPos)) {
//            moveRight(xPos + 1, yPos);
//        }

        //CHECK LEFT
        move(-1, 0);
//        if (isInsideBoard(xPos - 1, yPos)) {
//            moveLeft(xPos - 1, yPos);
//        }

    }

    @Override
    void displayPossibleMoves() {

    }

    @Override
    void alertInvalidMove() {

    }

    /**
     * Method for moving Piece to new cell
     * @param toCell The new cell to add Piece to
     * @See updatePosition(Cell cell)
     * @See deleteMoveList()
     */
    @Override
    public void movePiece(Cell toCell) {
        toCell.setPiece(this);
        updatePosition(toCell);
        deleteMoveList();
    }

    /**
     * The main move method. here we calculate how
     * long the piece can move until it either meats the end
     * of the board or another Piece
     * @param x The next x position to check
     * @param y The next y position to check
     */
    private void move(int x, int y) {
        if (isInsideBoard(xPos + x, yPos + y)) {
            Cell move = Game.cells[yPos + y][xPos + x];
            if (move.getPiece() == null) {
                moveList.add(move);
            }
            canCaptureEnemy(move);
        }
    }


    private void findThreats(int x, int y) {
        //ÄNDRAD MÅSTE KOLLAS ÖVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        x2 = xPos + x;
        y2 = yPos + y;
        while (isInsideBoard(x2, y2)) {
            Cell move = Game.cells[y2][x2];
            if (move.getPiece() != null && isEnemy(move)) {
                possibleThreat.add(move);
                //canGetCaptured(move);
                break;
            }
            //if (isInsideBoard(x2, y2)) {
                move = Game.cells[y2][x2];
                x2 += x;
                y2 += y;
            //}
        }
        //canCaptureEnemy(move);
    }

    /**
     * This method changes the Piece x and y position to the
     * new cells x and y position
     * @See updatePiece()
     * @See setHasMoved()
     * @param toCell
     */
    private void updatePosition(Cell toCell) {
        toCell.updatePiece();
        this.yPos = toCell.getY();
        this.xPos = toCell.getX();
        setHasMoved(true);
    }

    /**
     * Clears this pieces move list
     */
    @Override
    void deleteMoveList() {
        moveList.removeAll(moveList);
    }

    /**
     * Method for checking if the next position the piece can take
     * is inside the board or not
     * @param x The piece next X position
     * @param y The piece next Y position
     * @return True if inside else False
     */
    private boolean isInsideBoard(int x, int y) {
        return x > -1 && x < 8 && y > -1 && y < 8;
    }

    /**
     *
     * @param toCell The cell to check if containing Enemy
     * @return True if enemy else False
     */
    private boolean isEnemy(Cell toCell) {
        return toCell.getPiece().getIsWhite() != this.getIsWhite();
    }

    @Override
    public String toString() {
        return "King";
    }
}
