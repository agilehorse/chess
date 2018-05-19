package cz.cvut.fel.pjv.engine.board.moves;


import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
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
    private String performedToString = "";

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
        boolean enPassantSetNow = false;
        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
        final Tile sourceTile = this.getSourceTile();
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        sourceTile.setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        if (movedPiece.getPieceType() == PieceType.PAWN && sourceTile.getTileRow() + 2 == getNewRow()) {
            this.board.setEnPassantPawn((Pawn) movedPiece);
            enPassantSetNow = true;
        }
        this.movedPiece.setFirstMove(false);
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        if (this.board.getEnPassantPawn() != null
                && this.board.getEnPassantPawn().getPieceColour() == this.movedPiece.getPieceColour()
                && !enPassantSetNow) {
            board.setEnPassantPawn(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return getNewRow() == move.getNewRow() &&
                getNewColumn() == move.getNewColumn() &&
                Objects.equals(getBoard(), move.getBoard()) &&
                Objects.equals(getMovedPiece(), move.getMovedPiece()) &&
                Objects.equals(getDestinationTile(), move.getDestinationTile()) &&
                Objects.equals(getSourceTile(), move.getSourceTile());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBoard(), getMovedPiece(), getNewRow(), getNewColumn(), getDestinationTile(), getSourceTile());
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

    public abstract boolean freeFromCheck();

    String disambiguationTile() {
        for (final Move move : this.board.getCurrentPlayer().getLegalMoves()) {
            if (move.getDestinationTile() == this.getDestinationTile() && !this.equals(move) &&
                    this.movedPiece.getPieceType().equals(move.getMovedPiece().getPieceType())) {
                return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPieceRow(),
                        this.movedPiece.getPieceColumn()).substring(0, 1);
            }
        }
        return "";
    }

    public String getPerformedToString() {
        return performedToString;
    }

    public void setPerformedToString(String performedToString) {

        if (this.performedToString.equals("")) {
            this.performedToString = performedToString;
        } else {
            throw new RuntimeException("Move tostring cannot be set again!");
        }
    }
}
