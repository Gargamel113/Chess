package sample.model;

import sample.Cell;
import sample.logic.Game;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    int xPos, yPos, x2, y2;
    private List<Cell> moveList;

    public Knight(boolean isWhite, int x, int y) {
        super(isWhite ? "D:\\Development\\Java\\Projects\\Chess\\graphics\\JohnPablok Cburnett Chess set\\PNGs\\With Shadow\\128px\\w_knight_png_shadow_128px.png" :
                        "D:\\Development\\Java\\Projects\\Chess\\graphics\\JohnPablok Cburnett Chess set\\PNGs\\With Shadow\\128px\\b_knight_png_shadow_128px.png"
                , isWhite);
        this.yPos = x;
        this.xPos = y;
        moveList = new ArrayList<>();
        setImage(getPieceModel());
        setTranslateX(12);
        setTranslateY(10);
    }

    @Override
    public List<Cell> getMoveList() {
        return moveList;
    }

    @Override
    Piece getPiece() {
        return null;
    }

    /**
     * Method that checks if a player can move or not
     * @See calculatePossibleMoves()
     * @return true if move is possible else false
     */
    @Override
    public boolean canPieceMove() {
        calculatePossibleMoves();
        return false;
    }

    @Override
    boolean isPieceCoveringKing() {
        return false;
    }

    @Override
    boolean canCaptureEnemy(Cell enemyKingCell) {
//        calculatePossibleMoves();
//        for(Cell cell : moveList){
//            if(cell.getId().equals(enemyKingCell.getId())){
//                System.out.println("Can be cauptured");
//            } else {
//                System.out.println("Can´t be captured");
//            }
//        }
//        deleteMoveList();
        return false;
    }

    /**
     * Method that checks the input cell if it contains an
     * enemy piece. and if so adds it to the Piece enemy move list
     * @param toCell The cell to check what it contains
     * @return true if enemy piece else false
     */
    @Override
    boolean captureEnemyPiece(Cell toCell) {
        if (toCell.getPiece() != null && isEnemy(toCell)) {
            moveList.add(toCell);
            return true;
        }
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
        deleteMoveList();

        //CHECK UP_LEFT
        move(-1, -2);

        //CHECK UP_RIGHT
        move(1, -2);

        //CHECK DOWN_LEFT
        move(-1, 2);

        //CHECK DOWN_RIGHT
        move(1, 2);

        //CHECK LEFT_UP
        move(-2, -1);

        //CHECK LEFT_DOWN
        move(-2, 1);

        //CHECK RIGHT_UP
        move(2, -1);

        //CHECK RIGHT_DOWN
        move(2, 1);

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
     * The main move method. here we calculate how
     * long the piece can move until it either meats the end
     * of the board or another Piece
     * @param x The next x position to check
     * @param y The next y position to check
     */
    private void move(int x, int y){
        if (isInsideBoard(xPos + x, yPos + y)){
            Cell move = Game.cells[yPos + y][xPos + x];
            if(move.getPiece() == null){
                moveList.add(move);
            }
            captureEnemyPiece(move);
        }
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
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
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
    public String toString(){
        return "Knight";
    }
}