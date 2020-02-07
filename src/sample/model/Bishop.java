package sample.model;

import sample.Cell;
import sample.logic.Game;
import sample.logic.Movement;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    int xPos, yPos, x2, y2;
    private List<Cell> moveList;

    public Bishop(boolean isWhite, int x, int y) {
        super(isWhite ? "D:\\Development\\Java\\Projects\\Chess\\graphics\\JohnPablok Cburnett Chess set\\PNGs\\With Shadow\\128px\\w_bishop_png_shadow_128px.png" :
                        "D:\\Development\\Java\\Projects\\Chess\\graphics\\JohnPablok Cburnett Chess set\\PNGs\\With Shadow\\128px\\b_bishop_png_shadow_128px.png"
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
        calculatePossibleMoves();
        return false;
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
        return false;
    }

    @Override
    void pieceCantMove() {

    }

    @Override
    void calculatePossibleMoves() {
        deleteMoveList();

        //CHECK UP_RIGHT
        move(1, -1);

        //CHECK UP_LEFT
        move(-1, -1);

        //CHECK DOWN_RIGHT
        move(1, 1);

        //CHECK DOWN_LEFT
        move(-1, 1);

    }

    @Override
    void displayPossibleMoves() {

    }

    @Override
    void alertInvalidMove() {

    }

    @Override
    public void movePiece(Cell toCell) {
        toCell.setPiece(this);
        updatePosition(toCell);
        deleteMoveList();
    }

    private void updatePosition(Cell toCell) {
        toCell.updatePiece();
        this.yPos = toCell.getY();
        this.xPos = toCell.getX();
        setHasMoved(true);
    }

    private void move(int x, int y) {
        x2 = xPos + x;
        y2 = yPos + y;
        if (isInsideBoard(x2, y2)) {
            Cell move = Game.cells[y2][x2];
            while (move.getPiece() == null) {
                moveList.add(move);
                if (isInsideBoard(x2, y2)) {
                    move = Game.cells[y2][x2];
                    x2 += x;
                    y2 += y;
                } else {
                    break;
                }
            }
            canCaptureEnemy(move);
        }
    }

    @Override
    void deleteMoveList() {
        moveList.removeAll(moveList);
    }

    private boolean isInsideBoard(int x, int y) {
        return x > -1 && x < 8 && y > -1 && y < 8;
    }

    private boolean isEnemy(Cell toCell) {
        return toCell.getPiece().getIsWhite() != this.getIsWhite();
    }

    @Override
    public String toString() {
        return "Bishop";
    }

}
