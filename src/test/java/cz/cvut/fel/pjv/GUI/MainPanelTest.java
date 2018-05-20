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

public class MainPanelTest {

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
        final String newBoard = mainPanel.getBoard().toString();
        makeMove();
        moveLog = mainPanel.getMoveLog();
        mainPanel.get().resetBoard();
        final String resetedBoard = mainPanel.getBoard().toString();
        assertEquals(newBoard, resetedBoard);
    }

    private void makeMove() {
        final Move[] moves = mainPanel.getBoard().getCurrentPlayer().getLegalMoves().toArray(new Move[1]);
        final Move move = moves[0];
        mainPanel.getBoard().getCurrentPlayer().initiateMove(move);
        mainPanel.getMoveLog().addMove(move);
    }

    @Test
    public void saveAndLoad(){
        System.out.println("saveAndLoad");
        File file = new File("C:/Users/13dvz/OneDrive/Documents/NetBeansProjects/bulkodav/chess/src/test/java/cz/cvut/fel/pjv/GUI/test.pgn");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        makeMove();
        moveMadeBoard = mainPanel.getBoard().toString();

        System.out.println(moveMadeBoard);
        MainPanel.get().savePGNFile(file);

        mainPanel.get().loadPGNFile(file);
        assertEquals(true, mainPanel.get().getBoard().toString().equals(moveMadeBoard));
    }
}
