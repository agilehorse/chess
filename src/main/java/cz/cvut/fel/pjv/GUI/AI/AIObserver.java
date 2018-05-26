package cz.cvut.fel.pjv.GUI.AI;

import cz.cvut.fel.pjv.GUI.Clock;
import cz.cvut.fel.pjv.GUI.MainPanel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class AIObserver implements Observer {

    @Override
    public void update(final Observable observable, final Object obj) {
//      ends game if player is in checkmate
        if (MainPanel.getBoard().getCurrentPlayer().isInCheckMate()) {
            Clock.stop();
            JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                    "Game Over: Player " + MainPanel.getBoard().getCurrentPlayer().toString()
                            + " is in checkmate!", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
//            ends game if player is in stalemate
        } else if (MainPanel.getBoard().getCurrentPlayer().isInStaleMate()) {
            Clock.stop();
            JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                    "Game Over: Player " + MainPanel.getBoard().getCurrentPlayer().toString()
                            + " is in stalemate!", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
//            ends game if only kings are left, so the game won't go for ever
        } else if (MainPanel.getBoard().getWhitePieces().size() <= 1 &&
                MainPanel.getBoard().getBlackPieces().size() <= 1) {
            JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                    "Game Over. Game can't be ended", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            //        if player is AI, isn't in checkmate nor stalemate, execute the move
        } else if (MainPanel.getGameSetup().isAIPlayer(MainPanel.getBoard().getCurrentPlayer())) {
            final AIPlayer aiPlayer = new AIPlayer();
            aiPlayer.execute();
        }
    }
}
