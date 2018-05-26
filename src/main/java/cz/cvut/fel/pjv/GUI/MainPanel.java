package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.GUI.AI.AIObserver;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.GUI.PGN.Writer;
import cz.cvut.fel.pjv.GUI.PGN.Loader;
import cz.cvut.fel.pjv.engine.pieces.Piece;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.engine.board.BoardUtils.SET_OF_TILES;

public class MainPanel extends Observable {

    private final JFrame gameFrame;
    private static GameHistoryPanel gameHistoryPanel;
    private static TakenPiecesPanel takenPiecesPanel;
    private static MoveLog moveLog;
    private static GuiBoard guiBoard;
    private static final Dimension OUTER_FRAME_SIZE = new Dimension(730, 600);
    private static Board board;
    private static Tile sourceTile;
    private static Piece movedPiece;
    private static GameSetup gameSetup;
    private static ClockSetup clockSetup;
    private static final MainPanel SINGLETON = new MainPanel();
    private final static Logger LOGGER = Logger.getLogger(MainPanel.class.getSimpleName());

    private MainPanel() {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setJMenuBar(createMenuBar());
        this.gameFrame.setIconImage(new ImageIcon(
                "images/other/icon.png").getImage());
        this.gameFrame.setSize(OUTER_FRAME_SIZE);
        board = new Board();
        gameHistoryPanel = new GameHistoryPanel();
        takenPiecesPanel = new TakenPiecesPanel();
        guiBoard = new GuiBoard();
        moveLog = new MoveLog();
        this.addObserver(new AIObserver());
        gameSetup = new GameSetup(this.gameFrame, true);
        gameSetup.setLocation(gameFrame.getWidth()/2 - gameSetup.getWidth()/2,
                gameFrame.getHeight()/2 - gameSetup.getHeight()/2);
        clockSetup = new ClockSetup(this.gameFrame, true);
        clockSetup.setLocation(gameFrame.getWidth()/2 - gameSetup.getWidth()/2,
                gameFrame.getHeight()/2 - gameSetup.getHeight()/2);
        this.gameFrame.add(takenPiecesPanel, BorderLayout.EAST);
        this.gameFrame.add(gameHistoryPanel, BorderLayout.WEST);
        this.gameFrame.add(guiBoard, BorderLayout.CENTER);
        this.gameFrame.setResizable(false);
        this.gameFrame.setVisible(true);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static MainPanel get() {
        return SINGLETON;
    }

    public static MoveLog getMoveLog() {
        return moveLog;
    }

    public static GuiBoard getGuiBoard() {
        return guiBoard;
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
        final JMenu fileMenu = new JMenu("Game");
        final JMenuItem setupGameMenuItem = new JMenuItem("Player Setup");
        setupGameMenuItem.addActionListener(e -> {
            getGameSetup().promptUser();
            setUpUpdate(getGameSetup());
        });
        fileMenu.add(setupGameMenuItem);
        final JMenuItem setupClockMenuItem = new JMenuItem("Clock setup");
        setupClockMenuItem.addActionListener(e -> {
            getClockSetup().promptUser();
            setUpUpdate(getGameSetup());
        });
        fileMenu.add(setupClockMenuItem);

        final JMenuItem saveToPGN = new JMenuItem("Save to PGN");
        saveToPGN.addActionListener(e -> {
            final JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileFilter() {
                @Override
                public String getDescription() {
                    return ".pgn";
                }

                @Override
                public boolean accept(final File file) {
                    return file.isDirectory() ||
                            file.getName().toLowerCase().endsWith("pgn");
                }
            });
            final int option = chooser.showSaveDialog(getGameFrame());
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(".pgn")) {
                    file = new File(filePath + ".pgn");
                }
                savePGNFile(file);
            }
        });
        fileMenu.add(saveToPGN);
        final JMenuItem openPGN = new JMenuItem("Load from PGN File");
        openPGN.addActionListener(actionEvent -> {
            Object[] options = {"Load and discard",
                    "Cancel"};
            int n = JOptionPane.showOptionDialog(MainPanel.get().getGameFrame(),
                    "Do you really want to load the game? " +
                            "Your current game will be discarded if " +
                            "you haven't saved it.",
                    "Load game confirmation",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0) {
                resetBoard();
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showOpenDialog(MainPanel.get().getGameFrame());
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    if (file.getName().endsWith(".pgn")) {
                        loadPGNFile(file);
                    } else {
                        JOptionPane.showMessageDialog(MainPanel.get().gameFrame,
                                "Invalid file format!", "Input file error",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        fileMenu.add(openPGN);
        final JMenuItem resetMenuItem = new JMenuItem("Reset board");
        resetMenuItem.addActionListener(e -> resetBoard());
        fileMenu.add(resetMenuItem);
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(actionEvent -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }
//    resets everything
    public void resetBoard() {
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                Board.getTile(i, j).setPieceOnTile(null);
            }
        }
        this.gameFrame.remove(guiBoard);
        MainPanel.board = new Board();
        MainPanel.guiBoard = new GuiBoard();
        MainPanel.getMoveLog().clear();
        MainPanel.getGameHistoryPanel().redo(board, MainPanel.getMoveLog());
        MainPanel.getTakenPiecesPanel().redo(MainPanel.getMoveLog());
        Clock.resetTimer();
        this.gameFrame.add(guiBoard, BorderLayout.CENTER);
        this.gameFrame.validate();
        this.gameFrame.repaint();
        LOGGER.log(Level.INFO, "Board have been reset.");
    }

    void savePGNFile(File selectedFile) {
        Writer.writeGameToPGNFile(selectedFile, getMoveLog());
    }

    public static void loadPGNFile(final File pgnFile) {
        try {
//            resets everything and loads the file
            MainPanel.get().gameFrame.remove(guiBoard);
            MainPanel.board = null;
            MainPanel.board = new Board();
            MainPanel.board = Loader.persistPGNFile(pgnFile);
            MainPanel.guiBoard = new GuiBoard();
            MainPanel.moveLog = Loader.getMoveLog();
            MainPanel.getGameHistoryPanel().redo(board, Loader.getMoveLog());
            MainPanel.getTakenPiecesPanel().redo(Loader.getMoveLog());
            MainPanel.get().gameFrame.add(takenPiecesPanel, BorderLayout.EAST);
            MainPanel.get().gameFrame.add(gameHistoryPanel, BorderLayout.WEST);
            MainPanel.get().gameFrame.add(guiBoard, BorderLayout.CENTER);
            MainPanel.get().gameFrame.validate();
            MainPanel.get().gameFrame.repaint();
            LOGGER.log(Level.INFO, "File loaded successfully");

        } catch (final IOException e) {
            LOGGER.log(Level.INFO, "Unexpected error while loading file!");
        }
    }
//notifies AI player
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

    static Tile getSourceTile() {
        return sourceTile;
    }

    static void setSourceTile(Tile sourceTile) {
        MainPanel.sourceTile = sourceTile;
    }

    static Piece getMovedPiece() {
        return movedPiece;
    }

    static void setMovedPiece(Piece movedPiece) {
        MainPanel.movedPiece = movedPiece;
    }

    JFrame getGameFrame() {
        return gameFrame;
    }

    private static ClockSetup getClockSetup() {
        return clockSetup;
    }
}

