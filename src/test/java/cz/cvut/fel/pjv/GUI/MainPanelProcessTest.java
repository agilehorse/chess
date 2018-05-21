package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.GUI.PGN.Writer;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainPanelProcessTest {

    private MainPanel mainPanel;
    private String moveMadeBoard;
    private MoveLog moveLog;

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
        System.out.println("resetBoard");
        final String newBoard = MainPanel.getBoard().toString();
        makeMove();
        moveLog = MainPanel.getMoveLog();
        MainPanel.get().resetBoard();
        final String resetedBoard = MainPanel.getBoard().toString();
        assertEquals(newBoard, resetedBoard);
    }

    private void makeMove() {
        final Move[] moves = MainPanel.getBoard().getCurrentPlayer().getLegalMoves().toArray(new Move[1]);
        final Move move = moves[0];
        MainPanel.getBoard().getCurrentPlayer().initiateMove(move);
        MainPanel.getMoveLog().addMove(move);
    }

    @Test
    public void saveAndLoad(){
        System.out.println("saveAndLoad");
        final File file = new File(new File("src/test/java/cz/cvut/fel/pjv/GUI/test.pgn").getAbsolutePath());
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        makeMove();
        moveMadeBoard = MainPanel.getBoard().toString();
        MainPanel.get().savePGNFile(file);
        MainPanel.get().resetBoard();
        MainPanel.loadPGNFile(file);
        assertEquals(MainPanel.getBoard().toString(), moveMadeBoard);
        file.delete();
    }
}
