package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Rook;

public class QueenSideCastle extends CastleMove {
    public QueenSideCastle(final Board board,
                           final King castlingKing,
                           final int castlingKingNewRow,
                           final int castlingKingNewColumn,
                           final Rook castlingRook,
                           final int castlingRookOldRow,
                           final int castlingRookOldColumn,
                           final int castlingRookNewRow,
                           final int castlingRookNewColumn) {
        super(board, castlingKing, castlingKingNewRow, castlingKingNewColumn,
                castlingRook, castlingRookOldRow, castlingRookOldColumn,
                castlingRookNewRow, castlingRookNewColumn);
    }

    @Override
    public String toString() {
        return "0-0-0";
    }
}
