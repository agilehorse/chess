package cz.cvut.fel.pjv.board.moves;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.pieces.Piece;

public abstract class Move {

    private final Board board;
    private final Piece movedPiece;
    private final int newRow;
    private final int newColumn;

    public Move(final Board board, final Piece movedPiece,
                final int newRow, final int newColumn) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.newRow = newRow;
        this.newColumn = newColumn;
    }
//  defines new basic new, on empty tile

//  defines new attack move, on an occupied tile

}
