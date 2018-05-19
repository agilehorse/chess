package cz.cvut.fel.pjv.GUI.AI;

import cz.cvut.fel.pjv.GUI.Clock;
import cz.cvut.fel.pjv.GUI.GameSetup;
import cz.cvut.fel.pjv.GUI.GuiBoard;
import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.JavaFXGUI.GUIBoard;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class AIObserver implements Observer {
    @Override
    public void update(final Observable observable, final Object obj) {
        if (MainPanel.getGameSetup().isAIPlayer(MainPanel.getBoard().getCurrentPlayer())
                && !MainPanel.getBoard().getCurrentPlayer().isInCheckMate()
                && !MainPanel.getBoard().getCurrentPlayer().isInStaleMate()) {
                final AIPlayer aiPlayer = new AIPlayer();
                aiPlayer.execute();
        }
    }
}
