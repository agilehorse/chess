package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardBuilder;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

import java.util.Objects;

public abstract class Move {

    private MoveType moveType;
    final Board board;
    final Piece movedPiece;
    private final int newRow;
    private final int newColumn;

    public Move(
            final MoveType moveType,
            final Board board,
            final Piece movedPiece,
            final int newRow,
            final int newColumn) {
        this.moveType = moveType;
        this.board = board;
        this.movedPiece = movedPiece;
        this.newRow = newRow;
        this.newColumn = newColumn;
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
    public int hashCode() {        return Objects.hash(board, getMovedPiece(), getNewRow(), getNewColumn());
    }

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

    public Board execute() {
        final BoardBuilder builder = new BoardBuilder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.putPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.putPiece(piece);
        }
        Piece piece = this.movedPiece.moveIt(this);
        if (movedPiece.getPieceType() == PieceType.PAWN && movedPiece.getPieceRow() + 2 == newRow) {
            builder.setEnPassant((Pawn)piece);
        }
        piece.setFirstMove(false);
        builder.putPiece(piece);
        builder.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        return builder.build();
    }

    public int getCurrentRow() {
        return this.movedPiece.getPieceRow();
    }

    public int getCurrentColumn() {
        return this.movedPiece.getPieceColumn();
    }
}
