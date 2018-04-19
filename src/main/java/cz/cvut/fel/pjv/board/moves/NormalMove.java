package cz.cvut.fel.pjv.board.moves;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.pieces.Piece;

public final class NormalMove extends Move {

    public NormalMove(final Board board, final Piece movedPiece,
                      final int newRow, final int newColumn) {

        super(board, movedPiece, newRow, newColumn);
    }
}

