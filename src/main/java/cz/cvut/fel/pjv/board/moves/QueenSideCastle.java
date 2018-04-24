package cz.cvut.fel.pjv.board.moves;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.pieces.King;
import cz.cvut.fel.pjv.pieces.Rook;

public class QueenSideCastle extends CastleMove {
    public QueenSideCastle(Board board, King castlingKing, int castlingKingNewRow, int castlingKingNewColumn,
                           Rook castlingRook, int castlingRookOldRow, int castlingRookOldColumn,
                           int castlingRookNewRow, int castlingRookNewColumn) {
        super(board, castlingKing, castlingKingNewRow, castlingKingNewColumn,
                castlingRook, castlingRookOldRow, castlingRookOldColumn,
                castlingRookNewRow, castlingRookNewColumn);
    }

    @Override
    public String toString() {
        return "0-0-0";
    }
}
