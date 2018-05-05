//package cz.cvut.fel.pjv.swingGUI;
//
//import javax.swing.*;
//import javax.swing.filechooser.FileFilter;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.io.File;
//import java.util.Observable;
//import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
//
//import cz.cvut.fel.pjv.engine.board.Board;
//import cz.cvut.fel.pjv.engine.board.Tile;
//import cz.cvut.fel.pjv.engine.board.moves.Move;
//import cz.cvut.fel.pjv.engine.pieces.Piece;
//import cz.cvut.fel.pjv.engine.player.Player;
//
//public final class MainPanel extends Observable {
//
//    private final JFrame gameFrame;
//    private final GameHistoryPanel gameHistoryPanel;
//    private final TakenPiecesPanel takenPiecesPanel;
//    private final DebugPanel debugPanel;
//    private final GuiBoard guiBoard;
//    private final MoveLog moveLog;
//    private final GameSetup gameSetup;
//    private Board chessBoard;
//    private Move computerMove;
//    private Tile sourceTile;
//    private Tile destinationTile;
//    private Piece humanMovedPiece;
//    private GuiBoard.BoardDirection boardDirection;
//    private String pieceIconPath;
//    private boolean highlightLegalMoves;
//    private boolean useBook;
//    private Color lightTileColor = Color.decode("#FFFACD");
//    private Color darkTileColor = Color.decode("#593E1A");
//
//    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
//    static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
//    static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
//
//    private static final MainPanel INSTANCE = new MainPanel();
//
//    private MainPanel() {
//        this.gameFrame = new JFrame("Chess");
//        this.gameFrame.setIconImage(new ImageIcon("images/other/icon.png").getImage());
//        final JMenuBar tableMenuBar = new JMenuBar();
//        populateMenuBar(tableMenuBar);
//        this.gameFrame.setJMenuBar(tableMenuBar);
//        this.gameFrame.setLayout(new BorderLayout());
//        this.chessBoard = new Board();
//        this.boardDirection = GuiBoard.BoardDirection.NORMAL;
//        this.highlightLegalMoves = false;
//        this.useBook = false;
//        this.pieceIconPath = "art/pieces/plain/";
//        this.gameHistoryPanel = new GameHistoryPanel();
//        this.debugPanel = new DebugPanel();
//        this.takenPiecesPanel = new TakenPiecesPanel();
//        this.guiBoard = new GuiBoard();
//        this.moveLog = new MoveLog();
//        this.gameSetup = new GameSetup(this.gameFrame, true);
//        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
//        this.gameFrame.add(this.guiBoard, BorderLayout.CENTER);
//        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
//        this.gameFrame.add(debugPanel, BorderLayout.SOUTH);
//        setDefaultLookAndFeelDecorated(true);
//        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
//        center(this.gameFrame);
//        this.gameFrame.setVisible(true);
//    }
//
//    public static MainPanel get() {
//        return INSTANCE;
//    }
//
//    private JFrame getGameFrame() {
//        return this.gameFrame;
//    }
//
//    private Board getGameBoard() {
//        return this.chessBoard;
//    }
//
//    private MoveLog getMoveLog() {
//        return this.moveLog;
//    }
//
//    private GuiBoard getGuiBoard() {
//        return this.guiBoard;
//    }
//
//    private GameHistoryPanel getGameHistoryPanel() {
//        return this.gameHistoryPanel;
//    }
//
//    private TakenPiecesPanel getTakenPiecesPanel() {
//        return this.takenPiecesPanel;
//    }
//
//    private DebugPanel getDebugPanel() {
//        return this.debugPanel;
//    }
//
//    private GameSetup getGameSetup() {
//        return this.gameSetup;
//    }
//
//    private boolean getHighlightLegalMoves() {
//        return this.highlightLegalMoves;
//    }
//
//    private boolean getUseBook() {
//        return this.useBook;
//    }
//
//    public void show() {
//        MainPanel.get().getMoveLog().clear();
//        MainPanel.get().getGameHistoryPanel().redo(chessBoard, MainPanel.get().getMoveLog());
//        MainPanel.get().getTakenPiecesPanel().redo(MainPanel.get().getMoveLog());
//        MainPanel.get().getGuiBoard().drawBoard(MainPanel.get().getGameBoard());
//        MainPanel.get().getDebugPanel().redo();
//    }
//
//    private void populateMenuBar(final JMenuBar tableMenuBar) {
//        tableMenuBar.add(createFileMenu());
//        tableMenuBar.add(createPreferencesMenu());
//        tableMenuBar.add(createOptionsMenu());
//    }
//
//    private static void center(final JFrame frame) {
//        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        final int w = frame.getSize().width;
//        final int h = frame.getSize().height;
//        final int x = (dim.width - w) / 2;
//        final int y = (dim.height - h) / 2;
//        frame.setLocation(x, y);
//    }
//
//    private JMenu createFileMenu() {
//        final JMenu filesMenu = new JMenu("File");
//        filesMenu.setMnemonic(KeyEvent.VK_F);
//
////        final JMenuItem openPGN = new JMenuItem("Load PGN File", KeyEvent.VK_O);
////        openPGN.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(final ActionEvent e) {
////                JFileChooser chooser = new JFileChooser();
////                int option = chooser.showOpenDialog(MainPanel.get().getGameFrame());
////                if (option == JFileChooser.APPROVE_OPTION) {
////                    loadPGNFile(chooser.getSelectedFile());
////                }
////            }
////        });
////        filesMenu.add(openPGN);
//
////        final JMenuItem openFEN = new JMenuItem("Load FEN File", KeyEvent.VK_F);
////        openFEN.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(final ActionEvent e) {
////                String fenString = JOptionPane.showInputDialog("Input FEN");
////                undoAllMoves();
////                chessBoard = FenUtilities.createGameFromFEN(fenString);
////                MainPanel.get().getGuiBoard().drawBoard(chessBoard);
////            }
////        });
////        filesMenu.add(openFEN);
//
////        final JMenuItem saveToPGN = new JMenuItem("Save Game", KeyEvent.VK_S);
////        saveToPGN.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(final ActionEvent e) {
////                final JFileChooser chooser = new JFileChooser();
////                chooser.setFileFilter(new FileFilter() {
////                    @Override
////                    public String getDescription() {
////                        return ".pgn";
////                    }
////
////                    @Override
////                    public boolean accept(final File file) {
////                        return file.isDirectory() || file.getName().toLowerCase().endsWith("pgn");
////                    }
////                });
////                final int option = chooser.showSaveDialog(MainPanel.get().getGameFrame());
////                if (option == JFileChooser.APPROVE_OPTION) {
////                    savePGNFile(chooser.getSelectedFile());
////                }
////            }
////        });
////        filesMenu.add(saveToPGN);
//
//        final JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
//        exitMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                MainPanel.get().getGameFrame().dispose();
//                System.exit(0);
//            }
//        });
//        filesMenu.add(exitMenuItem);
//
//        return filesMenu;
//    }
//
//    private JMenu createOptionsMenu() {
//
//        final JMenu optionsMenu = new JMenu("Options");
//        optionsMenu.setMnemonic(KeyEvent.VK_O);
//
////        final JMenuItem resetMenuItem = new JMenuItem("New Game", KeyEvent.VK_P);
////        resetMenuItem.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(final ActionEvent e) {
////                undoAllMoves();
////            }
////
////        });
////        optionsMenu.add(resetMenuItem);
//
////        final JMenuItem evaluateBoardMenuItem = new JMenuItem("Evaluate Board", KeyEvent.VK_E);
////        evaluateBoardMenuItem.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(final ActionEvent e) {
////                System.out.println(StandardBoardEvaluator.get().evaluate(chessBoard, gameSetup.getSearchDepth()));
////
////            }
////        });
////        optionsMenu.add(evaluateBoardMenuItem);
//
////        final JMenuItem escapeAnalysis = new JMenuItem("Escape Analysis Score", KeyEvent.VK_S);
////        escapeAnalysis.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(final ActionEvent e) {
////                final Move lastMove = moveLog.getMoves().get(moveLog.size() - 1);
////                if (lastMove != null) {
////                    System.out.println(MoveUtils.exchangeScore(lastMove));
////                }
////
////            }
////        });
////        optionsMenu.add(escapeAnalysis);
//
//        final JMenuItem legalMovesMenuItem = new JMenuItem("Current State", KeyEvent.VK_L);
//        legalMovesMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                System.out.println(chessBoard.getWhitePieces());
//                System.out.println(chessBoard.getBlackPieces());
//                System.out.println(playerInfo(chessBoard.getCurrentPlayer()));
//                System.out.println(playerInfo(chessBoard.getCurrentPlayer().getOpponent()));
//            }
//        });
//        optionsMenu.add(legalMovesMenuItem);
//
////        final JMenuItem undoMoveMenuItem = new JMenuItem("Undo last move", KeyEvent.VK_M);
////        undoMoveMenuItem.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(final ActionEvent e) {
////                if (MainPanel.get().getMoveLog().size() > 0) {
////                    undoLastMove();
////                }
////            }
////        });
////        optionsMenu.add(undoMoveMenuItem);
//
//        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game", KeyEvent.VK_S);
//        setupGameMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                MainPanel.get().getGameSetup().promptUser();
//                MainPanel.get().setupUpdate(MainPanel.get().getGameSetup());
//            }
//        });
//        optionsMenu.add(setupGameMenuItem);
//
//        return optionsMenu;
//    }
//
//    private JMenu createPreferencesMenu() {
//
//        final JMenu preferencesMenu = new JMenu("Preferences");
//
//        final JMenu colorChooserSubMenu = new JMenu("Choose Colors");
//        colorChooserSubMenu.setMnemonic(KeyEvent.VK_S);
//
//        final JMenuItem chooseDarkMenuItem = new JMenuItem("Choose Dark Tile Color");
//        colorChooserSubMenu.add(chooseDarkMenuItem);
//
//        final JMenuItem chooseLightMenuItem = new JMenuItem("Choose Light Tile Color");
//        colorChooserSubMenu.add(chooseLightMenuItem);
//
//        final JMenuItem chooseLegalHighlightMenuItem = new JMenuItem(
//                "Choose Legal Move Highlight Color");
//        colorChooserSubMenu.add(chooseLegalHighlightMenuItem);
//
//        preferencesMenu.add(colorChooserSubMenu);
//
//        chooseDarkMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                final Color colorChoice = JColorChooser.showDialog(MainPanel.get().getGameFrame(), "Choose Dark Tile Color",
//                        MainPanel.get().getGameFrame().getBackground());
//                if (colorChoice != null) {
//                    MainPanel.get().getGuiBoard().setTileDarkColor(chessBoard, colorChoice);
//                }
//            }
//        });
//
//        chooseLightMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                final Color colorChoice = JColorChooser.showDialog(MainPanel.get().getGameFrame(), "Choose Light Tile Color",
//                        MainPanel.get().getGameFrame().getBackground());
//                if (colorChoice != null) {
//                    MainPanel.get().getGuiBoard().setTileLightColor(chessBoard, colorChoice);
//                }
//            }
//        });
//
//        final JMenu chessMenChoiceSubMenu = new JMenu("Choose Chess Men Image Set");
//
//        final JMenuItem holyWarriorsMenuItem = new JMenuItem("Holy Warriors");
//        chessMenChoiceSubMenu.add(holyWarriorsMenuItem);
//
//        final JMenuItem rockMenMenuItem = new JMenuItem("Rock Men");
//        chessMenChoiceSubMenu.add(rockMenMenuItem);
//
//        final JMenuItem abstractMenMenuItem = new JMenuItem("Abstract Men");
//        chessMenChoiceSubMenu.add(abstractMenMenuItem);
//
//        final JMenuItem woodMenMenuItem = new JMenuItem("Wood Men");
//        chessMenChoiceSubMenu.add(woodMenMenuItem);
//
//        final JMenuItem fancyMenMenuItem = new JMenuItem("Fancy Men");
//        chessMenChoiceSubMenu.add(fancyMenMenuItem);
//
//        final JMenuItem fancyMenMenuItem2 = new JMenuItem("Fancy Men 2");
//        chessMenChoiceSubMenu.add(fancyMenMenuItem2);
//
//        woodMenMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                System.out.println("implement me");
//                MainPanel.get().getGameFrame().repaint();
//            }
//        });
//
//        holyWarriorsMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                pieceIconPath = "art/holywarriors/";
//                MainPanel.get().getGuiBoard().drawBoard(chessBoard);
//            }
//        });
//
//        rockMenMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//            }
//        });
//
//        abstractMenMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                pieceIconPath = "art/simple/";
//                MainPanel.get().getGuiBoard().drawBoard(chessBoard);
//            }
//        });
//
//        fancyMenMenuItem2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                pieceIconPath = "art/fancy2/";
//                MainPanel.get().getGuiBoard().drawBoard(chessBoard);
//            }
//        });
//
//        fancyMenMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                pieceIconPath = "art/fancy/";
//                MainPanel.get().getGuiBoard().drawBoard(chessBoard);
//            }
//        });
//
//        preferencesMenu.add(chessMenChoiceSubMenu);
//
//        chooseLegalHighlightMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                System.out.println("implement me");
//                MainPanel.get().getGameFrame().repaint();
//            }
//        });
//
//        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");
//
//        flipBoardMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                boardDirection = boardDirection.opposite();
//                guiBoard.drawBoard(chessBoard);
//            }
//        });
//
//        preferencesMenu.add(flipBoardMenuItem);
//        preferencesMenu.addSeparator();
//
//
//        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem(
//                "Highlight Legal Moves", false);
//
//        cbLegalMoveHighlighter.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                highlightLegalMoves = cbLegalMoveHighlighter.isSelected();
//            }
//        });
//
//        preferencesMenu.add(cbLegalMoveHighlighter);
//
//        final JCheckBoxMenuItem cbUseBookMoves = new JCheckBoxMenuItem(
//                "Use Book Moves", false);
//
//        cbUseBookMoves.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                useBook = cbUseBookMoves.isSelected();
//            }
//        });
//
//        preferencesMenu.add(cbUseBookMoves);
//
//        return preferencesMenu;
//
//    }
//
//    private static String playerInfo(final Player player) {
//        return ("Player is: " + player.getColour() + "\nlegal moves (" + player.getLegalMoves().size() + ") = " + player.getLegalMoves() + "\ninCheck = " +
//                player.isInCheck() + "\nisInCheckMate = " + player.isInCheckMate() +
//                "\nisCastled = " + player.isCastled()) + "\n";
//    }
//
//    private void updateGameBoard(final Board board) {
//        this.chessBoard = board;
//    }
//
//    private void updateComputerMove(final Move move) {
//        this.computerMove = move;
//    }
//
////    private void undoAllMoves() {
////        for (int i = MainPanel.get().getMoveLog().size() - 1; i >= 0; i--) {
////            final Move lastMove = MainPanel.get().getMoveLog().removeMove(MainPanel.get().getMoveLog().size() - 1);
////            this.chessBoard = this.chessBoard.getCurrentPlayer().unMakeMove(lastMove).getToBoard();
////        }
////        this.computerMove = null;
////        MainPanel.get().getMoveLog().clear();
////        MainPanel.get().getGameHistoryPanel().redo(chessBoard, MainPanel.get().getMoveLog());
////        MainPanel.get().getTakenPiecesPanel().redo(MainPanel.get().getMoveLog());
////        MainPanel.get().getGuiBoard().drawBoard(chessBoard);
////        MainPanel.get().getDebugPanel().redo();
////    }
////
////    private static void loadPGNFile(final File pgnFile) {
////        try {
////            persistPGNFile(pgnFile);
////        } catch (final IOException e) {
////            e.printStackTrace();
////        }
////    }
//
////    private static void savePGNFile(final File pgnFile) {
////        try {
////            writeGameToPGNFile(pgnFile, MainPanel.get().getMoveLog());
////        } catch (final IOException e) {
////            e.printStackTrace();
////        }
////    }
//
////    private void undoLastMove() {
////        final Move lastMove = MainPanel.get().getMoveLog().removeMove(MainPanel.get().getMoveLog().size() - 1);
////        this.chessBoard = this.chessBoard.getCurrentPlayer().unMakeMove(lastMove).getToBoard();
////        this.computerMove = null;
////        MainPanel.get().getMoveLog().removeMove(lastMove);
////        MainPanel.get().getGameHistoryPanel().redo(chessBoard, MainPanel.get().getMoveLog());
////        MainPanel.get().getTakenPiecesPanel().redo(MainPanel.get().getMoveLog());
////        MainPanel.get().getGuiBoard().drawBoard(chessBoard);
////        MainPanel.get().getDebugPanel().redo();
////    }
//
//    private void moveMadeUpdate(final PlayerType playerType) {
//        setChanged();
//        notifyObservers(playerType);
//    }
//
//    private void setupUpdate(final GameSetup gameSetup) {
//        setChanged();
//        notifyObservers(gameSetup);
//    }
//
//    enum PlayerType {
//        HUMAN,
//        COMPUTER
//    }
//}
