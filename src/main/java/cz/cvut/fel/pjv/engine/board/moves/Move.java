package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

import java.util.Objects;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    private final int newRow;
    private final int newColumn;
    private Tile destinationTile;
    private Tile sourceTile;

    public Move(final Board board,
            final Piece movedPiece,
            final int newRow,
            final int newColumn) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.newRow = newRow;
        this.newColumn = newColumn;
        setDestinationTile();
        setSourceTile();
    }

    private void setDestinationTile() {
        this.destinationTile = board.getTile(newRow, newColumn);
    }

    private void setSourceTile() {
        this.sourceTile = board.getTile(movedPiece.getPieceRow(), movedPiece.getPieceColumn());
    }

    public void execute() {
        this.movedPiece.move(this);
        movedPiece.setFirstMove(false);
        if (movedPiece.getPieceType() == PieceType.PAWN && movedPiece.getPieceRow() + 2 == newRow) {
            board.setEnPassantPawn((Pawn)movedPiece);
        }
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        Board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        board.recalculate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return getNewRow() == move.getNewRow() &&
                getNewColumn() == move.getNewColumn() &&
                Objects.equals(board, move.board) &&
                Objects.equals(getMovedPiece(), move.getMovedPiece());
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, getMovedPiece(), getNewRow(), getNewColumn());
    }

    public abstract MoveType getMoveType();

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public int getNewRow() {
        return newRow;
    }

    public int getNewColumn() {
        return newColumn;
    }

    public Board getBoard() {
        return board;
    }

    public int getCurrentRow() {
        return this.movedPiece.getPieceRow();
    }

    public int getCurrentColumn() {
        return this.movedPiece.getPieceColumn();
    }

    public Tile getDestinationTile() {
        return destinationTile;
    }

    public Tile getSourceTile() {
        return sourceTile;
    }

    public void setSourceTile(Tile sourceTile) {
        this.sourceTile = sourceTile;
    }

    public Piece getAttackedPiece() {
        return null;
    }

}
