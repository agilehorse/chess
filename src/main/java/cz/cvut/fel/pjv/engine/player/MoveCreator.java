package cz.cvut.fel.pjv.engine.player;


import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.NullMove;
import cz.cvut.fel.pjv.engine.board.moves.PawnPromotionMove;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;

public class MoveCreator {

    private static final Move NULL_MOVE = new NullMove();

    public MoveCreator() {
        throw new RuntimeException("Not instantiable!");
    }

    public static Move createStandardMove(final Board board,
                                          final int oldRow,
                                          final int oldColumn,
                                          final int newRow,
                                          final int newColumn) {
        for (final Move move : board.getAllLegalMoves()) {
            if (move.getCurrentRow() == oldRow && move.getCurrentColumn() == oldColumn
                    && move.getNewRow() == newRow && move.getNewColumn() == newColumn) {
                return move;
            }
        }
        return NULL_MOVE;
    }

    public Move newPawnPromotionMove(Board board, Tile destinationTile, Pawn promotedPawn, Piece newPiece) {
        return new PawnPromotionMove(board, promotedPawn, destinationTile.getTileRow(), destinationTile.getTileColumn(), newPiece);
    }
}
