package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.pieces.Piece;

public class EnPassantAttack extends AttackMove {
    public EnPassantAttack(final Board board,
                           final Piece movedPiece,
                           final int newRow,
                           final int newColumn,
                           final Piece attackedPiece) {
        super(board, movedPiece, newRow, newColumn, attackedPiece);
    }
}
