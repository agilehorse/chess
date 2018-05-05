package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.pieces.Piece;

//  defines new basic new, on empty tile
public class NormalMove extends Move {

    private MoveType moveType;

    public NormalMove(final Board board,
                      final Piece movedPiece,
                      final int newRow,
                      final int newColumn) {
        super(board, movedPiece, newRow, newColumn);
        this.moveType = MoveType.NORMAL;
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }
}
