package cz.cvut.fel.pjv.player;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;

class MoveMaker {
    private final Board board;
    private final Move move;
    private final MoveState moveState;

    MoveMaker(Board board, Move move,
                          MoveState moveState) {
        this.board = board;
        this.move = move;
        this.moveState = moveState;
    }

    MoveState getMoveState() {
        return this.moveState;
    }
}

