package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.board.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveLog {
    private final List<Move> moves;

    public MoveLog() {
        this.moves = new ArrayList<>();
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    public void addMove(final Move move) {
        this.moves.add(move);
    }

    public int size() {
        return this.moves.size();
    }

    public void clear(){
        this.moves.clear();
    }

    public Move removeMove(int index) {
        return this.moves.remove(index);
    }

    public boolean removeMove(final Move move) {
        return this.moves.remove(move);
    }
}
