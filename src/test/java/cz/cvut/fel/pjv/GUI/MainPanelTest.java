package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainPanelTest {

    private MainPanel mainPanel;

    @BeforeClass
    public static void beforeClass(){
    }

    @AfterClass
    public static void afterClass() {
    }

    @Before
    public void setUp() {
        mainPanel = new MainPanel();
    }

    @Test
    public void resetBoard() {
        final String boardString = MainPanel.getBoard().toString();
        final Move[] moves = MainPanel.getBoard().getCurrentPlayer().getLegalMoves().toArray(new Move[1]);
        final Move move = moves[0];
        MainPanel.getBoard().getCurrentPlayer().initiateMove(move);
        MainPanel.get().resetBoard();
        final String newBoardString = MainPanel.getBoard().toString();
        assertEquals(boardString, newBoardString);
    }


}
