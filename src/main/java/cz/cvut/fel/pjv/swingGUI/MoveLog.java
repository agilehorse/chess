package cz.cvut.fel.pjv.swingGUI;

import cz.cvut.fel.pjv.engine.board.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveLog {

    private final List<Move> moves;

    MoveLog() {
        this.moves = new ArrayList<>();
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    void addMove(final Move move) {
        this.moves.add(move);
    }

    public int size() {
        return this.moves.size();
    }

    void clear() {
        this.moves.clear();
    }

    Move removeMove(final int index) {
        return this.moves.remove(index);
    }

    boolean removeMove(final Move move) {
        return this.moves.remove(move);
    }
}
