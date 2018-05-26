package cz.cvut.fel.pjv.engine.board.moves;


import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.Piece;

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

    String disambiguationFile() {
        for (final Move move : this.board.getCurrentPlayer().getLegalMoves()) {
            if (move.getDestinationTile() == this.getDestinationTile() &&
                    !this.equals(move) &&
                    this.movedPiece.getPieceType().equals(move.getMovedPiece().getPieceType())) {
                return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPieceRow(),
                        this.movedPiece.getPieceColumn()).substring(0, 1);
            }
        }
        return "";
    }

    public void setPerformedToString(String performedToString) {
        if (this.performedToString.equals("")) {
            this.performedToString = performedToString;
        } else if ((performedToString.equals("+") || performedToString.equals("#"))) {
            if (!this.performedToString.endsWith("+")) {
                this.performedToString += performedToString;
            } else {
                this.performedToString = this.performedToString.substring(0, this.performedToString.length()-1) + performedToString;
            }
        }
    }

    private void setDestinationTile() {
        this.destinationTile = Board.getTile(newRow, newColumn);
    }

    private void setSourceTile() {
        this.sourceTile = Board.getTile(movedPiece.getPieceRow(),
                movedPiece.getPieceColumn());
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
        return Objects.hash(getBoard(), getMovedPiece(), getNewRow(),
                getNewColumn(), getDestinationTile(), getSourceTile());
    }

    public abstract void execute();

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

    public String getPerformedToString() {
        return performedToString;
    }
}
