package cz.cvut.fel.pjv.GUI.AI;

import cz.cvut.fel.pjv.GUI.GameSetup;
import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class AIPlayer extends SwingWorker<Move, String> {

    AIPlayer() {
    }

    @Override
    protected Move doInBackground() {
        return RandomMover.get(MainPanel.getBoard());
    }

    @Override
    protected void done() {
        try {
            final Board board = MainPanel.getBoard();
            final Move aiMove = get();
            final boolean done = board.getCurrentPlayer().executeMove(aiMove);
            if (done) {
                MainPanel.getMoveLog().addMove(aiMove);
                board.recalculate();
                MainPanel.getGameHistoryPanel().redo(MainPanel.getBoard(), MainPanel.getMoveLog());
                MainPanel.getTakenPiecesPanel().redo(MainPanel.getMoveLog());
                MainPanel.getGuiBoard().drawBoard(MainPanel.getBoard());
                MainPanel.get().moveMadeUpdate(GameSetup.PlayerType.COMPUTER);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
