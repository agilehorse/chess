package cz.cvut.fel.pjv.engine.board;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class BoardTest {

    private final Board board;
    private Collection<Move> moves;

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
    public void setMove() {
        System.out.println("setMove");
        final Player player = board.getCurrentPlayer();
        Board.setMove(player.getOpponent().getColour());
        board.recalculate(true);
        assertEquals(player.getOpponent(), board.getCurrentPlayer());
    }
// process test
    @Test
    public void calculateMoves() {
        System.out.println("calculateMoves");
        Collection<Piece> whitePieces = board.getWhitePieces();
        this.moves = board.calculateMoves(whitePieces);
        final Move move = new ArrayList<>(board.getCurrentPlayer().getLegalMoves()).get(0);
        board.getCurrentPlayer().initiateMove(move);
        board.recalculate(true);
        whitePieces = board.getWhitePieces();
        final Collection<Move> newMoves = board.calculateMoves(whitePieces);
        assertNotEquals(this.moves, newMoves);
    }
}
