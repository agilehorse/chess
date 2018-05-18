package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.GUI.AI.AIObserver;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Observable;

import static cz.cvut.fel.pjv.engine.board.BoardUtils.SET_OF_TILES;

public class MainPanel extends Observable {

    private final JFrame gameFrame;
    private static GameHistoryPanel gameHistoryPanel;
    private static TakenPiecesPanel takenPiecesPanel;
    private static MoveLog moveLog;
    private static GuiBoard guiBoard;
    private static final Dimension OUTER_FRAME_SIZE = new Dimension(700, 600);
    private static Board board;
    private static Tile sourceTile;
    private static Piece movedPiece;
    private static GameSetup gameSetup;
    private static final MainPanel SINGLETON = new MainPanel();


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
        this.addObserver(new AIObserver());
        this.gameSetup = new GameSetup(this.gameFrame, true);
        this.gameFrame.add(takenPiecesPanel, BorderLayout.EAST);
        this.gameFrame.add(gameHistoryPanel, BorderLayout.WEST);
        this.gameFrame.add(guiBoard, BorderLayout.CENTER);
        this.gameFrame.setResizable(false);
        this.gameFrame.setVisible(true);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static MainPanel get() {return SINGLETON;}

    public static MoveLog getMoveLog() {
        return moveLog;
    }

    public static GuiBoard getGuiBoard() {
        return guiBoard;
    }

    public static void exit() {
        System.exit(0);
    }

    public void moveMadeUpdate(GameSetup.PlayerType computer) {
        setChanged();
        notifyObservers(computer);
    }

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game", KeyEvent.VK_S);
        setupGameMenuItem.addActionListener(e -> {
            getGameSetup().promptUser();
            setUpUpdate(getGameSetup());
        });
        fileMenu.add(setupGameMenuItem);

        final JMenuItem saveToPGN = new JMenuItem("Save Game", KeyEvent.VK_S);
        saveToPGN.addActionListener(e -> {
            final JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileFilter() {
                @Override
                public String getDescription() {
                    return ".pgn";
                }
                @Override
                public boolean accept(final File file) {
                    return file.isDirectory() || file.getName().toLowerCase().endsWith("pgn");
                }
            });
            final int option = chooser.showSaveDialog(getGameFrame());
            if (option == JFileChooser.APPROVE_OPTION) {
                savePGNFile(chooser.getSelectedFile());
            }
        });
        fileMenu.add(saveToPGN);
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(actionEvent -> System.out.println("open pgn file"));
        fileMenu.add(openPGN);
        final JMenuItem resetMenuItem = new JMenuItem("Reset board", KeyEvent.VK_P);
        resetMenuItem.addActionListener(e -> resetBoard());
        fileMenu.add(resetMenuItem);
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(actionEvent -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private void resetBoard() {
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                board.getTile(i, j).setPieceOnTile(null);
            }
        }
        MainPanel.board = null;
        MainPanel.board = new Board();
        MainPanel.guiBoard = null;
        MainPanel.guiBoard = new GuiBoard();
        MainPanel.guiBoard.drawBoard(board);
        MainPanel.getMoveLog().clear();
        MainPanel.getGameHistoryPanel().redo(board, MainPanel.getMoveLog());
        MainPanel.getTakenPiecesPanel().redo(MainPanel.getMoveLog());
        Clock.resetTimer();
    }

    private void savePGNFile(File selectedFile) {
        System.out.println("Not implemented yet!");
//        try {
//            writeGameToPGNFile(selectedFile, getMoveLog());
//        }
//        catch (final IOException e) {
//            e.printStackTrace();
//        }
    }

    private void setUpUpdate(GameSetup gameSetup) {
        setChanged();
        notifyObservers(gameSetup);
    }

    public static GameSetup getGameSetup() {
        return gameSetup;
    }

    public static GameHistoryPanel getGameHistoryPanel() {
        return gameHistoryPanel;
    }

    public static TakenPiecesPanel getTakenPiecesPanel() {
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

    private JFrame getGameFrame() {
        return gameFrame;
    }
}

