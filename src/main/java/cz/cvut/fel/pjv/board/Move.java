package cz.cvut.fel.pjv.board;

import cz.cvut.fel.pjv.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int newRow;
    final int newColumn;

    public Move(final Board board, final Piece movedPiece,
                final int newRow, final int newColumn) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.newRow = newRow;
        this.newColumn = newColumn;
    }
//  defines new basic new, on empty tile
    public static final class NormalMove extends Move {

        public NormalMove(final Board board, final Piece movedPiece,
                         final int newRow, final int newColumn) {

            super(board, movedPiece, newRow, newColumn);
        }
    }
//  defines new attack move, on an occupied tile
    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece,
                          final int newRow, final int newColumn, final Piece attackedPiece) {
            super(board, movedPiece, newRow, newColumn);
            this.attackedPiece = attackedPiece;
        }
    }
}
