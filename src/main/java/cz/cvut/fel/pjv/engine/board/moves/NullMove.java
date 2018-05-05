package cz.cvut.fel.pjv.engine.board.moves;

public class NullMove extends Move {

    private final MoveType moveType;

    public NullMove() {
        super(null, null, -1, -1);
        this.moveType = MoveType.ILLEGAL;
    }

    @Override
    public void execute() {
        throw new RuntimeException("cannot execute null move!");
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public String toString() {
        return "Null Move";
    }
}
