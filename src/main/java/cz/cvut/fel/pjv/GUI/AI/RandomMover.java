package cz.cvut.fel.pjv.GUI.AI;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomMover {

    private final static Logger LOGGER = Logger.getLogger(RandomMover.class.getSimpleName());

    public static Move get(final Board board) {
//        creates list of moves which are 100% legal
        ArrayList<Move> legalMoves = new ArrayList<>();
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            if (move.freeFromCheck()) {
                legalMoves.add(move);
            }
        }
//        if the are any legal moves, random one is chosen and returned
        if (legalMoves.size() > 0) {
            return legalMoves.get(new Random().nextInt(legalMoves.size()));
        }
        LOGGER.log(Level.INFO, board.getCurrentPlayer().toString()
                + " computer player has no legal moves!");
        return null;
    }
}
