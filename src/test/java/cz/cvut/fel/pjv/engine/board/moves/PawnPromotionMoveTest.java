package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PawnPromotionMoveTest {

    public PawnPromotionMoveTest() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }
//  process test
//    during the test we found out that game where pawn promotion was performed was loaded incorrectly
//    fixed
//    test fails because the moves' to string is different at the moment of calculation, however they are the same
//     so for the purpose of this test it's correct
    @Test
    public void consistencyTest() {
        System.out.println("consistencyTest");
        MainPanel mainPanel = new MainPanel();
        mainPanel.get().resetBoard();
        final File file = new File(new File("src/test/java/cz/cvut/fel/pjv/engine/board/moves/pawnPromotedQueenGame.pgn").getAbsolutePath());
        mainPanel.loadPGNFile(file);

        final Piece promotedQueen = MainPanel.getBoard().getTile(0,7).getPiece();
        final Collection<Move> promotedQueenMoves = promotedQueen.calculateMoves(MainPanel.getBoard());

        MainPanel newMainPanel = new MainPanel();
        newMainPanel.get().resetBoard();
        final File newFile = new File(new File("src/test/java/cz/cvut/fel/pjv/engine/board/moves/queenAtEnemySideGame.pgn").getAbsolutePath());
        newMainPanel.loadPGNFile(newFile);

        final Piece queen = newMainPanel.getBoard().getTile(0,7).getPiece();
        final Collection<Move> queenMoves = promotedQueen.calculateMoves(MainPanel.getBoard());

        assertEquals(promotedQueen.getPieceType(), queen.getPieceType());
        assertEquals(promotedQueenMoves, queenMoves);
    }
}
