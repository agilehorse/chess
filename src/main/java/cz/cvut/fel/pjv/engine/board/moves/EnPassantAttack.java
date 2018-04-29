package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardBuilder;
import cz.cvut.fel.pjv.engine.pieces.Piece;

public class EnPassantAttack extends AttackMove {
    public EnPassantAttack(final Board board,
                           final Piece movedPiece,
                           final int newRow,
                           final int newColumn,
                           final Piece attackedPiece) {
        super(board, movedPiece, newRow, newColumn, attackedPiece);
    }

    @Override
    public Board execute() {
        final BoardBuilder builder = new BoardBuilder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.putPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            if (!piece.equals(this.getAttackedPiece())) {
                builder.putPiece(piece);
            }
        }
        Piece piece = this.movedPiece.moveIt(this);
        piece.setFirstMove(false);
        builder.putPiece(piece);
        builder.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        builder.setMoveTransition(this);
        return builder.build();
    }


}
