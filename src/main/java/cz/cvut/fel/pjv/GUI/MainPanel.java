package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainPanel {

    private final JFrame gameFrame;
    private static GameHistoryPanel gameHistoryPanel;
    private static TakenPiecesPanel takenPiecesPanel;
    private static MoveLog moveLog;
    private static GuiBoard guiBoard;
    private final static Dimension OUTER_FRAME_SIZE = new Dimension(700, 600);
    private static Board board;
    private static Tile sourceTile;
    private static Piece movedPiece;


    public MainPanel() {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setJMenuBar(createMenuBar());
        this.gameFrame.setIconImage(new ImageIcon("images/other/icon.png").getImage());
        this.gameFrame.setSize(OUTER_FRAME_SIZE);
        board = new Board();
        gameHistoryPanel = new GameHistoryPanel();
        takenPiecesPanel = new TakenPiecesPanel();
        guiBoard = new GuiBoard();
        moveLog = new MoveLog();
        this.gameFrame.add(takenPiecesPanel, BorderLayout.EAST);
        this.gameFrame.add(gameHistoryPanel, BorderLayout.WEST);
        this.gameFrame.add(guiBoard, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);

    }

    static MoveLog getMoveLog() {
        return moveLog;
    }

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(actionEvent -> System.out.println("open pgn file"));
        fileMenu.add(openPGN);
        final JMenuItem resetMenuItem = new JMenuItem("New Game", KeyEvent.VK_P);
        resetMenuItem.addActionListener(e -> {
            MainPanel.board = null;
                    MainPanel.board = new Board();
            MainPanel.guiBoard = null;
            MainPanel.guiBoard = new GuiBoard();
            MainPanel.guiBoard.drawBoard(board);
        });
        fileMenu.add(resetMenuItem);
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(actionEvent -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    static GameHistoryPanel getGameHistoryPanel() {
        return gameHistoryPanel;
    }

    static TakenPiecesPanel getTakenPiecesPanel() {
        return takenPiecesPanel;
    }


    public static Board getBoard() {
        return board;
    }

    static Tile getSourceTile() { return sourceTile; }

    static void setSourceTile(Tile sourceTile) {
        MainPanel.sourceTile = sourceTile;
    }

    static Piece getMovedPiece() {
        return movedPiece;
    }

    static void setMovedPiece(Piece movedPiece) {
        MainPanel.movedPiece = movedPiece;
    }
}
