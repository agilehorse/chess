package cz.cvut.fel.pjv.engine.board.moves;

import java.util.Objects;

public class NullMove extends Move {

    private final MoveType moveType;

    public NullMove() {
        super(null, null, -1, -1);
        this.moveType = MoveType.ILLEGAL;
    }

    @Override
    public void execute() {
        throw new RuntimeException("Cannot get null move!");
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public boolean freeFromCheck() {
        return false;
    }

    @Override
    public String toString() {
        return "Null Move";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NullMove)) return false;
        if (!super.equals(o)) return false;
        NullMove nullMove = (NullMove) o;
        return getMoveType() == nullMove.getMoveType();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getMoveType());
    }
}
