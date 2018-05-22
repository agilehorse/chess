package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class enPassantTest {

    public enPassantTest() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void enPassant() {
        MainPanel mainPanel = new MainPanel();
        mainPanel.get().resetBoard();
        final File file = new File("C:/Users/13dvz/OneDrive/Documents/enPassantTest.pgn");
        mainPanel.loadPGNFile(file);
        final Board board = mainPanel.get().getBoard();
        final ArrayList<Move> moves = new ArrayList<>(board.getTile(4,2).getPiece().calculateMoves(board));
        final ArrayList<String> expected = new ArrayList<>();
        expected.add("c3");
        expected.add("cxd3");
        assertEquals(expected.toString(), moves.toString());
    }
}
