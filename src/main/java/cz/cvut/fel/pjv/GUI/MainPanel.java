package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel {

    private final JFrame gameFrame;
    private final GuiBoard guiBoard;
    private final static Dimension OUTER_FRAME_SIZE = new Dimension(600, 600);
    private static Board board;
    private static Tile sourceTile;
    private static Tile destinationTile;
    private static Piece movedPiece;


    public MainPanel() {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setJMenuBar(createMenuBar());
        this.gameFrame.setIconImage(new ImageIcon("images/other/icon.png").getImage());
        this.gameFrame.setSize(OUTER_FRAME_SIZE);
        board = new Board();
        this.guiBoard = new GuiBoard();
        this.gameFrame.add(this.guiBoard, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
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
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(actionEvent -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    public static void setBoard(Board board) {
        MainPanel.board = board;
    }

    public static Board getBoard() {
        return board;
    }

    static Tile getSourceTile() {
        return sourceTile;
    }

    static void setSourceTile(Tile sourceTile) {
        MainPanel.sourceTile = sourceTile;
    }

    static Tile getDestinationTile() {
        return destinationTile;
    }

    static void setDestinationTile(Tile destinationTile) {
        MainPanel.destinationTile = destinationTile;
    }

    static Piece getMovedPiece() {
        return movedPiece;
    }

    static void setMovedPiece(Piece movedPiece) {
        MainPanel.movedPiece = movedPiece;
    }
}
