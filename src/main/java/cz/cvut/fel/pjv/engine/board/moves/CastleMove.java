package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardBuilder;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.Rook;

import java.util.Objects;

public class CastleMove extends Move {

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
            super(MoveType.CASTLE, board, castlingKing, castlingKingNewRow, castlingKingNewColumn);
            this.castlingRook = castlingRook;
            this.castlingRookOldRow = castlingRookOldRow;
            this.castlingRookOldColumn = castlingRookOldColumn;
            this.castlingRookNewRow = castlingRookNewRow;
            this.castlingRookNewColumn = castlingRookNewColumn;
        }
// Creates a new board
    @Override
    public Board execute() {
        final BoardBuilder boardBuilder = new BoardBuilder();
//        puts down all the pieces of a current player except Rook and King in this particular castle move
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece) && !this.castlingRook.equals(piece)) {
                boardBuilder.putPiece(piece);
            }
        }
//        puts down all opponents pieces
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            boardBuilder.putPiece(piece);
        }
//      places a new king on the tile where the old king moved
        Piece king = this.movedPiece.moveIt(this);
        king.setFirstMove(false);
        boardBuilder.putPiece(king);
//      places a new rook on the tile where the old rook moved
        Piece rook = new Rook(this.castlingRookNewRow, this.castlingRookNewColumn,
                this.castlingRook.getPieceColour());
        rook.setFirstMove(false);
        boardBuilder.putPiece(rook);
//        sets move to the opponent
        boardBuilder.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
//        builds a new board
        return boardBuilder.build();
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
                Objects.equals(getCastlingRook(), that.getCastlingRook());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getCastlingRook(), castlingRookOldRow, castlingRookOldColumn, castlingRookNewRow, castlingRookNewColumn);
    }
}
