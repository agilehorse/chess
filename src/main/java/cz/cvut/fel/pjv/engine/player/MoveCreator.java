package cz.cvut.fel.pjv.engine.player;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.IllegalMove;
import cz.cvut.fel.pjv.engine.board.moves.Move;

public class MoveCreator {

    private static final Move NULL_MOVE = new IllegalMove();

    private MoveCreator() {
        throw new RuntimeException("Not instantiatable!");
    }

    public static Move getNullMove() {
        return NULL_MOVE;
    }

    public static Move createMove(final Board board,
                                  final int currentRow,
                                  final int currentColumn,
                                  final int newRow,
                                  final int newColumn) {
        for (final Move move : board.getAllLegalMoves()) {
            if (move.getCurrentRow() == currentRow &&
                    move.getCurrentColumn() == currentRow &&
                    move.getNewRow() == newRow &&
                    move.getNewColumn() == newColumn) {
                return move;
            }
        }
        return NULL_MOVE;
    }
}
