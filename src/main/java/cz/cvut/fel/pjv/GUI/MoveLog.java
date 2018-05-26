package cz.cvut.fel.pjv.GUI;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.engine.board.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveLog {

    private final List<Move> moves;

    public MoveLog() {
        this.moves = new ArrayList<>();
    }

    public List<Move> getMoves() {
        return ImmutableList.copyOf(this.moves);
    }

    public void addMove(final Move move) {
        this.moves.add(move);
    }

    int size() {
        return this.moves.size();
    }

    void clear() {
        this.moves.clear();
    }
}
