package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.pieces.King;

public class NullMove extends Move {

    private final MoveType moveType;

    public NullMove() {
        super(null, null, -1, -1);
        this.moveType = MoveType.ILLEGAL;
    }

    @Override
    public void execute() {
        throw new RuntimeException("Cannot execute null move!");
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public boolean validateForCheck() {
        return false;
    }

    @Override
    public String toString() {
        return "Null Move";
    }
}
