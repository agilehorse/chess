package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;

public class IllegalMove extends Move {

    public IllegalMove() {
        super(MoveType.ILLEGAL, null, null, -1, -1);
    }

    @Override
    public Board execute() {
        throw new RuntimeException("Move is not legal!");
    }

    @Override
    public int getNewRow() {
        return -1;
    }

    @Override
    public int getNewColumn() {
        return -1;
    }
}
