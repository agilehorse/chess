package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.pieces.Piece;

//  defines new basic new, on empty tile
public class NormalMove extends Move {

    public NormalMove(final Board board,
                      final Piece movedPiece,
                      final int newRow,
                      final int newColumn) {
        super(MoveType.NORMAL, board, movedPiece, newRow, newColumn);
    }
}
