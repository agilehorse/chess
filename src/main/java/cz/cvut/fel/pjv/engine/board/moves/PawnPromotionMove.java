package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardBuilder;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;

import java.util.Objects;

public class PawnPromotionMove extends Move{

    final Board board;
    private final Pawn promotedPawn;
    private final int newRow;
    private final int newColumn;
    private final Piece newPiece;

    public PawnPromotionMove( final Board board,
                              final Pawn promotedPawn,
                              final int newRow,
                              final int newColumn,
                              final Piece newPiece) {
        super(MoveType.NORMAL, board, promotedPawn, newRow, newColumn);
        this.board = board;
        this.promotedPawn = promotedPawn;
        this.newRow = newRow;
        this.newColumn = newColumn;
        this.newPiece = newPiece;
    }

    @Override
    public Board execute() {
        final BoardBuilder builder = new BoardBuilder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.promotedPawn.equals(piece)) {
                builder.putPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.putPiece(piece);
        }
        builder.putPiece(this.newPiece.moveIt(this));
        builder.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        return builder.build();
    }

    @Override
    public Piece getMovedPiece() {
        return this.newPiece;
    }
}
