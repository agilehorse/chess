package cz.cvut.fel.pjv.board.moves;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardBuilder;
import cz.cvut.fel.pjv.pieces.King;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.Rook;

public class CastleMove extends Move {

        private Rook castlingRook;
        private int castlingRookOldRow;
        private int castlingRookOldColumn;
        private int castlingRookNewRow;
        private int castlingRookNewColumn;

        CastleMove(Board board, King castlingKing,
                          int castlingKingNewRow, int castlingKingNewColumn,
                          Rook castlingRook, int castlingRookOldRow, int castlingRookOldColumn,
                          int castlingRookNewRow, int castlingRookNewColumn) {
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

    public Rook getCastlingRook() {
            return castlingRook;
        }
}
