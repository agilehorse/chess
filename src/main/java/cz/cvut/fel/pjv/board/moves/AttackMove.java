package cz.cvut.fel.pjv.board.moves;


import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.pieces.Piece;

//  defines new attack move, on an occupied tile
public final class AttackMove extends Move {

    private final Piece attackedPiece;

    public AttackMove(final Board board,
                      final Piece movedPiece,
                      final int newRow,
                      final int newColumn,
                      final Piece attackedPiece) {

        super(board, movedPiece, newRow, newColumn);
        this.attackedPiece = attackedPiece;
    }
}