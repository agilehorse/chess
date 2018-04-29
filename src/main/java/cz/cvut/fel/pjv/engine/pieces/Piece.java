package cz.cvut.fel.pjv.engine.pieces;

import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;

import java.util.Collection;
import java.util.Objects;

public abstract class Piece {
    private PieceType pieceType;
    final int pieceRow;
    final int pieceColumn;
    private final Colour pieceColour;
    private boolean isFirstMove;

    public Piece(final PieceType pieceType,
                 final int pieceRow,
                 final int pieceColumn,
                 final Colour pieceColour) {
        this.pieceType = pieceType;
        this.pieceRow = pieceRow;
        this.pieceColumn = pieceColumn;
        this.pieceColour = pieceColour;
        this.isFirstMove = true;
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

    public abstract Piece moveIt(Move move);

    @Override
    public int hashCode() {
        return Objects.hash(getPieceType(), getPieceRow(), getPieceColumn(), getPieceColour(), isFirstMove());
    }
}
