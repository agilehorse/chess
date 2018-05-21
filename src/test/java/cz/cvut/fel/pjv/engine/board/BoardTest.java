package cz.cvut.fel.pjv.engine.board;

import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    private final Board board;
    public BoardTest() {
        board = new Board();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void recalculateBoard() {
        System.out.println("recalculateBoard");
        final Player player = board.getCurrentPlayer();
        Board.setMove(player.getOpponent().getColour());
        board.recalculate(true);
        assertEquals(player.getOpponent(), board.getCurrentPlayer());
    }
}
