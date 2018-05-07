package cz.cvut.fel.pjv.engine.board.moves;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    private final int newRow;
    private final int newColumn;
    private Tile destinationTile;
    private Tile sourceTile;
    private static boolean executed;

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
        final Tile oldTile = this.board.getTile(movedPiece.getPieceRow(), movedPiece.getPieceColumn());
        this.movedPiece.move(this.newRow, this.newColumn);
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        setExecuted(true);
        final King king = this.board.getCurrentPlayer().getPlayersKing();
        final Tile kingTile = this.board.getTile(king.getPieceRow(), king.getPieceColumn());
        this.board.recalculate();
        for (final Move move : this.board.getCurrentPlayer().getOpponent().getLegalMoves()) {
            if (move.getDestinationTile() == kingTile) {
                this.movedPiece.move(oldTile.getTileRow(), oldTile.getTileColumn());
                this.sourceTile.setPieceOnTile(movedPiece);
                this.destinationTile.setPieceOnTile(null);
                setExecuted(false);
            }
        }
        if (isExecuted()) {
            if (movedPiece.getPieceType() == PieceType.PAWN && oldTile.getTileRow() + 2 == newRow) {
                this.board.setEnPassantPawn((Pawn) movedPiece);
            }
            this.movedPiece.setFirstMove(false);
            this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        }
        this.board.recalculate();
    }

    public static boolean isExecuted() {
        return executed;
    }

    static void setExecuted(boolean executed) {
        Move.executed = executed;
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
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.newRow, this.newColumn);
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

    public Piece getAttackedPiece() {
        return null;
    }

}
