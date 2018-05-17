package cz.cvut.fel.pjv.GUI.AI;

import cz.cvut.fel.pjv.GUI.Clock;
import cz.cvut.fel.pjv.GUI.GameSetup;
import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.MoveType;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class AIPlayer extends SwingWorker<Move, String> {

    public AIPlayer() {
    }

    @Override
    protected Move doInBackground() throws Exception {
        final RandomMover randomMover = new RandomMover();
        Move aiMove = randomMover.get(MainPanel.getBoard());
        if (aiMove.getMoveType().equals(MoveType.ATTACK) && aiMove.getAttackedPiece().getPieceType().equals(PieceType.KING)) {
            aiMove = randomMover.get(MainPanel.getBoard());
        } else {
            return aiMove;
        }
        throw new RuntimeException("cannot reach here");
    }

    @Override
    protected void done() {
        try {
            final Board board = MainPanel.getBoard();
            final Move aiMove = get();
            final boolean done = board.getCurrentPlayer().initiateMove(aiMove);
            if (done) {
                MainPanel.getMoveLog().addMove(aiMove);
                board.recalculate(true);
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
