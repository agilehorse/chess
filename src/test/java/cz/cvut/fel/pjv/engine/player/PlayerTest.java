package cz.cvut.fel.pjv.engine.player;

import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.MoveType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertTrue;

public class PlayerTest {

    public PlayerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        
    }

    @Test
    public void isCastled() {
        MainPanel mainPanel = new MainPanel();
        MainPanel.get().resetBoard();
        System.out.println("isCastled");
        final File file = new File(new File("src/test/java/cz/cvut/fel/pjv/engine/player/boardBeforeCastle.pgn").getAbsolutePath());
        MainPanel.loadPGNFile(file);
        final Board board = MainPanel.getBoard();
        assertTrue(!board.getCurrentPlayer().isCastled());
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            if (move.getMoveType().equals(MoveType.CASTLE)) {
                board.getCurrentPlayer().initiateMove(move);
                board.recalculate(true);
                assertTrue(board.getCurrentPlayer().getOpponent().isCastled());
                break;
            }
        }
    }
    @Test
    public void isInCheckMate() {
        MainPanel mainPanel = new MainPanel();
        System.out.println("isInCheckmate");
        MainPanel.get().resetBoard();
        final File file = new File(new File("src/test/java/cz/cvut/fel/pjv/engine/player/checkMateGame.pgn").getAbsolutePath());
        MainPanel.loadPGNFile(file);
        MainPanel.getBoard().recalculate(true);
        assertTrue(MainPanel.getBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void isInStaleMate() {
        MainPanel mainPanel = new MainPanel();
        System.out.println("isInStaleMate");
        MainPanel.get().resetBoard();
        final File newfile = new File(new File("src/test/java/cz/cvut/fel/pjv/engine/player/staleMateGame.pgn").getAbsolutePath());
        MainPanel.loadPGNFile(newfile);
        MainPanel.getBoard().recalculate(true);
        assertTrue(MainPanel.getBoard().getCurrentPlayer().isInStaleMate());
    }
}