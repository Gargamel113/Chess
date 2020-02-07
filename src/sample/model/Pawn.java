package sample.model;

import sample.Cell;
import sample.logic.Game;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private int yPos, xPos, x2, y2;
    private List<Cell> moveList;
    private String currentCell;

    public Pawn(boolean isWhite, int x, int y) {
        super(isWhite ? "D:\\Development\\Java\\Projects\\LocalChess\\graphics\\JohnPablok Cburnett Chess set\\PNGs\\With Shadow\\128px\\w_pawn_png_shadow_128px.png" :
                        "D:\\Development\\Java\\Projects\\LocalChess\\graphics\\JohnPablok Cburnett Chess set\\PNGs\\With Shadow\\128px\\b_pawn_png_shadow_128px.png"
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
        return this;
    }

    @Override
    public boolean canPieceMove() {
        //DO ALL THE CALCULATIONS IN HERE LATER ON
        displayPossibleMoves();

        calculatePossibleMoves();
        return false;
    }

    private Cell findMyKing() {
        //ÄNDRAD MÅSTE KOLLAS ÖVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for (int i = 0; i < Game.cells.length; i++) {
            for (int j = 0; j < Game.cells[i].length; j++) {
                Cell cell = Game.cells[i][j];
                if (cell.getPiece() != null && cell.getPiece().getClass().equals(King.class) && !isEnemy(cell)) {
                    System.out.println("FOUND THE KING");
                    return cell;
                }
            }
        }
        return null;
    }

    @Override
    boolean isPieceCoveringKing() {
        return false;
    }

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
        System.out.println(toCell.getPiece() + " Captured by " + this);
        toCell.setPiece(null);
        toCell.updatePiece();
        return false;
    }

    @Override
    void pieceCantMove() {

    }

    @Override
    void calculatePossibleMoves() {
        //REMOVE PREVIOUS MOVELIST
        deleteMoveList();

        //CHECK Movement
        if (!getHasMoved()) {
            doubleMove();
        } else if (isInsideBoard(xPos, dir())) {
            moveUp(xPos, dir());
        }
        //CHECK CAN CAPTURE RIGHT
        if (isInsideBoard(xPos + 1, dir())) {
            moveUpRight(xPos, dir());
        }
        //CHECK CAN CAPTURE LEFT
        if (isInsideBoard(xPos - 1, dir())) {
            moveUpLeft(xPos, dir());
        }
    }

    @Override
    void displayPossibleMoves() {
        //ÄNDRAD MÅSTE KOLLAS ÖVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
        //LÄGG TILL EVENT OCH TA BORT INNE I PAWN
        if (toCell.getPiece() != null && isEnemy(toCell)) {
            captureEnemyPiece(toCell);
        }
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
        if (!getHasMoved()) {
            setHasMoved(true);
        }
    }

    /**
     * Method specific to Pawn move Up
     * @param x2 The x position to move to
     * @param y2 The y position to move to
     */
    private void moveUp(int x2, int y2) {
        Cell move = Game.cells[y2][x2];
        if (move.getPiece() == null) {
            moveList.add(move);
        }
    }

    /**
     * Only available if Pawn has not moved yet
     * Gives Pawn the double move as first option
     */
    private void doubleMove() {
        moveUp(xPos, dir());
        moveUp(xPos, getIsWhite() ? yPos - 2 : yPos + 2);
    }

    /**
     * Method for capturing enemies that are up right
     * @param x2 next X position
     * @param y2 next Y position
     */
    private void moveUpRight(int x2, int y2) {
        canCaptureEnemy(Game.cells[y2][x2 + 1]);
    }

    /**
     * Method for capturing enemies that are up right
     * @param x2 next X position
     * @param y2 next Y position
     */
    private void moveUpLeft(int x2, int y2) {
        canCaptureEnemy(Game.cells[y2][x2 - 1]);
    }

    /**
     * Since Pawn only can move 1 cell at a time.
     * Dir makes gives us the correct move for
     * White and Black Pawns
     * @return -1 if White +1 if Black
     */
    private int dir() {
        return getIsWhite() ? yPos - 1 : yPos + 1;
    }

    /**
     * Clears this pieces move list
     */
    @Override
    public void deleteMoveList() {
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
        return "Pawn";
    }
}
