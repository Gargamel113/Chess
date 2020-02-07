package sample.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Cell;

import java.util.List;


public abstract class Piece extends ImageView {

    private final String pieceModelpath;
    private final Image pieceModel;
    private final boolean isWhite;
    private boolean isSelected, hasMoved;

    protected Piece(String pieceModelpath, boolean isWhite) {
        this.isWhite = isWhite;
        this.isSelected = false;
        this.hasMoved = false;
        this.pieceModelpath = pieceModelpath;
        this.pieceModel = new Image("File:///" + pieceModelpath, 64, 64, true, true);
    }

    public Image getPieceModel() {
        return pieceModel;
    }

    public boolean getIsWhite() {
        return isWhite;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    abstract public List<Cell> getMoveList();

    public abstract boolean canPieceMove();

    public void setHasMoved(boolean b){
        hasMoved = b;
    }

    abstract Piece getPiece();

    abstract boolean isPieceCoveringKing();

    abstract boolean canCaptureEnemy(Cell toCell);

    abstract boolean captureEnemyPiece(Cell toCell);

    abstract void pieceCantMove();

    abstract void calculatePossibleMoves();

    abstract void displayPossibleMoves();

    abstract void alertInvalidMove();

    abstract public void movePiece(Cell toCell);

    abstract void deleteMoveList();




    //if Piece is selected
    //check if Piece is covering king
    //if covering
    //alert player that he cant move
    //else
    //check possible moves
    //display possible moves
    //wait for user to make a choice of what to do
    //if selecting a valid position
    //check if valid position holds a enemy piece
    //if containing an enemy piece, Capture enemy piece and move to position
    //Change players turn
    //else
    //move to position
    //Change players turn
    //else
    //alert player of unvalid move
    //else
    //do nothing
}
