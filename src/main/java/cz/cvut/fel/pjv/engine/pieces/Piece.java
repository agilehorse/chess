package cz.cvut.fel.pjv.engine.pieces;

import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.board.moves.Move;

import java.util.Collection;
import java.util.Objects;

public abstract class Piece {
    private PieceType pieceType;
    int pieceRow;
    int pieceColumn;
    private final Colour pieceColour;
    private boolean isFirstMove;
    private boolean active;

    public Piece(final PieceType pieceType,
                 final int pieceRow,
                 final int pieceColumn,
                 final Colour pieceColour) {
        this.pieceType = pieceType;
        this.pieceRow = pieceRow;
        this.pieceColumn = pieceColumn;
        this.pieceColour = pieceColour;
        this.isFirstMove = true;
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public int getPieceRow() {
        return this.pieceRow;
    }

    public int getPieceColumn() {
        return this.pieceColumn;
    }

    public Colour getPieceColour(){
        return this.pieceColour;
    }

    boolean isFirstMove() {
        return this.isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public abstract Collection<Move> calculateMoves(final Board board);

    private void setPieceRow(int pieceRow) {
        this.pieceRow = pieceRow;
    }

    private void setPieceColumn(int pieceColumn) {
        this.pieceColumn = pieceColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return getPieceRow() == piece.getPieceRow() &&
                getPieceColumn() == piece.getPieceColumn() &&
                isFirstMove() == piece.isFirstMove() &&
                getPieceType() == piece.getPieceType() &&
                getPieceColour() == piece.getPieceColour();
    }

    public void move(final int newRow, final int newColumn) {
        this.setPieceRow(newRow);
        this.setPieceColumn(newColumn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPieceType(), getPieceRow(), getPieceColumn(), getPieceColour(), isFirstMove());
    }

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }
}
