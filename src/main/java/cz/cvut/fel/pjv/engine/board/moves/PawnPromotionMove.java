package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;

public class PawnPromotionMove extends NormalMove{

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
        super(board, promotedPawn, newRow, newColumn);
        this.board = board;
        this.promotedPawn = promotedPawn;
        this.newRow = newRow;
        this.newColumn = newColumn;
        this.newPiece = newPiece;
    }

    @Override
    public Piece getMovedPiece() {
        return this.newPiece;
    }
}
