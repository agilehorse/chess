package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.*;

import java.util.Objects;

public class CastleMove extends Move {

    private MoveType moveType;
    private Rook castlingRook;
    private int castlingRookOldRow;
    private int castlingRookOldColumn;
    private int castlingRookNewRow;
    private int castlingRookNewColumn;

    CastleMove(final Board board,
               final King castlingKing,
               final int castlingKingNewRow,
               final int castlingKingNewColumn,
               final Rook castlingRook,
               final int castlingRookOldRow,
               final int castlingRookOldColumn,
               final int castlingRookNewRow,
               final int castlingRookNewColumn) {
        super(board, castlingKing, castlingKingNewRow, castlingKingNewColumn);
        this.castlingRook = castlingRook;
        this.castlingRookOldRow = castlingRookOldRow;
        this.castlingRookOldColumn = castlingRookOldColumn;
        this.castlingRookNewRow = castlingRookNewRow;
        this.castlingRookNewColumn = castlingRookNewColumn;
        this.moveType = MoveType.CASTLE;
    }

    @Override
    public void execute() {
        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.movedPiece.setFirstMove(false);
        this.castlingRook.setFirstMove(false);
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        Tile oldRookTile = this.board.getTile(castlingRook.getPieceRow(), castlingRook.getPieceColumn());
        oldRookTile.setPieceOnTile(null);
        Tile newRookTile = this.board.getTile(castlingRookNewRow, castlingRookNewColumn);
        newRookTile.setPieceOnTile(castlingRook);
        this.castlingRook.move(castlingRookNewRow, castlingRookNewColumn);
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        if (this.board.getEnPassantPawn() != null
                && this.board.getEnPassantPawn().getPieceColour() == this.movedPiece.getPieceColour()) {
            board.setEnPassantPawn(null);
        }
    }

    private Rook getCastlingRook() {
        return castlingRook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CastleMove)) return false;
        if (!super.equals(o)) return false;
        CastleMove that = (CastleMove) o;
        return castlingRookOldRow == that.castlingRookOldRow &&
                castlingRookOldColumn == that.castlingRookOldColumn &&
                castlingRookNewRow == that.castlingRookNewRow &&
                castlingRookNewColumn == that.castlingRookNewColumn &&
                getMoveType() == that.getMoveType() &&
                Objects.equals(getCastlingRook(), that.getCastlingRook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMoveType(), getCastlingRook(), castlingRookOldRow, castlingRookOldColumn, castlingRookNewRow, castlingRookNewColumn);
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public boolean freeFromCheck() {
        return true;
    }
}
