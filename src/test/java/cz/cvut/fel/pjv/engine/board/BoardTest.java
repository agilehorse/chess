package cz.cvut.fel.pjv.engine.board;

import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;
import cz.cvut.fel.pjv.engine.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

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
    public void setMove() {
        System.out.println("setMove");
        final Player player = board.getCurrentPlayer();
        Board.setMove(player.getOpponent().getColour());
        board.recalculate();
        assertEquals(player.getOpponent(), board.getCurrentPlayer());
    }
// process test
    @Test
    public void calculateMoves() {
        System.out.println("calculateMoves");
        Collection<Piece> whitePieces = board.getWhitePieces();
        Collection<Move> moves = board.calculateMoves(whitePieces);
        final Move move = new ArrayList<>(board.getCurrentPlayer().getLegalMoves()).get(0);
        board.getCurrentPlayer().executeMove(move);
        board.recalculate();
        whitePieces = board.getWhitePieces();
        final Collection<Move> newMoves = board.calculateMoves(whitePieces);
        assertNotEquals(moves, newMoves);
    }
//  process test
//    during the test we found out that enPassant doesn't work right
//    neet to fix it
    @Test
    public void enPassantTest() {
        System.out.println("enPassantTest");
        final Board board = new Board();
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            int row = move.getMovedPiece().getPieceRow();
            int row2 = move.getDestinationTile().getTileRow() - 2*move.getMovedPiece().getPieceColour().getDirection() ;
            if (move.getMovedPiece().getPieceType().equals(PieceType.PAWN) &&  row==row2 ) {
                board.getCurrentPlayer().executeMove(move);
                assertNotEquals(null, board.getEnPassantPawn());
            }
        }
        System.out.println(board.toString());
    }
}
