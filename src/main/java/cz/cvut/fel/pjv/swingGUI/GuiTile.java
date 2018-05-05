//package cz.cvut.fel.pjv.swingGUI;
//
//import cz.cvut.fel.pjv.engine.board.Board;
//import cz.cvut.fel.pjv.engine.board.moves.Move;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Collections;
//
//import static cz.cvut.fel.pjv.swingGUI.MainPanel.TILE_PANEL_DIMENSION;
//import static javax.swing.SwingUtilities.invokeLater;
//import static javax.swing.SwingUtilities.isLeftMouseButton;
//import static javax.swing.SwingUtilities.isRightMouseButton;
//
//public class GuiTile extends JPanel {
//    GuiTile(final GuiBoard boardPanel,
//            final int tileRow,
//            final int tileColumn) {
//        super(new GridBagLayout());
//        this.tileRow = tileId;
//        setPreferredSize(TILE_PANEL_DIMENSION);
//        assignTileColor();
//        assignTilePieceIcon(chessBoard);
//        highlightTileBorder(chessBoard);
//        addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(final MouseEvent event) {
//
//                if(MainPanel.get().getGameSetup().isAIPlayer(MainPanel.get().getGameBoard().currentPlayer()) ||
//                        BoardUtils.isEndGame(MainPanel.get().getGameBoard())) {
//                    return;
//                }
//
//                if (isRightMouseButton(event)) {
//                    sourceTile = null;
//                    destinationTile = null;
//                    humanMovedPiece = null;
//                } else if (isLeftMouseButton(event)) {
//                    if (sourceTile == null) {
//                        sourceTile = chessBoard.getTile(tileId);
//                        humanMovedPiece = sourceTile.getPiece();
//                        if (humanMovedPiece == null) {
//                            sourceTile = null;
//                        }
//                    } else {
//                        destinationTile = chessBoard.getTile(tileId);
//                        final Move move = MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
//                                destinationTile.getTileCoordinate());
//                        final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
//                        if (transition.getMoveStatus().isDone()) {
//
//                            chessBoard = transition.getToBoard();
//                            moveLog.addMove(move);
//                        }
//                        sourceTile = null;
//                        destinationTile = null;
//                        humanMovedPiece = null;
//                    }
//                }
//                invokeLater(new Runnable() {
//                    public void run() {
//                        gameHistoryPanel.redo(chessBoard, moveLog);
//                        takenPiecesPanel.redo(moveLog);
//                        //if (gameSetup.isAIPlayer(chessBoard.currentPlayer())) {
//                        MainPanel.get().moveMadeUpdate(PlayerType.HUMAN);
//                        //}
//                        boardPanel.drawBoard(chessBoard);
//                        debugPanel.redo();
//                    }
//                });
//            }
//
//            @Override
//            public void mouseExited(final MouseEvent e) {
//            }
//
//            @Override
//            public void mouseEntered(final MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(final MouseEvent e) {
//            }
//
//            @Override
//            public void mousePressed(final MouseEvent e) {
//            }
//        });
//        validate();
//    }
//
//    void drawTile(final Board board) {
//        assignTileColor();
//        assignTilePieceIcon(board);
//        highlightTileBorder(board);
//        highlightLegals(board);
//        highlightAIMove();
//        validate();
//        repaint();
//    }
//
//    void setLightTileColor(final Color color) {
//        lightTileColor = color;
//    }
//
//    void setDarkTileColor(final Color color) {
//        darkTileColor = color;
//    }
//
//    private void highlightTileBorder(final Board board) {
//        if(humanMovedPiece != null &&
//                humanMovedPiece.getPieceAllegiance() == board.getCurrentPlayer().getAlliance() &&
//                humanMovedPiece.getPiecePosition() == this.tileId) {
//            setBorder(BorderFactory.createLineBorder(Color.cyan));
//        } else {
//            setBorder(BorderFactory.createLineBorder(Color.GRAY));
//        }
//    }
//
//    private void highlightAIMove() {
//        if(computerMove != null) {
//            if(this.tileId == computerMove.getCurrentCoordinate()) {
//                setBackground(Color.pink);
//            } else if(this.tileId == computerMove.getDestinationCoordinate()) {
//                setBackground(Color.red);
//            }
//        }
//    }
//
//    private void highlightLegals(final Board board) {
//        if (MainPanel.get().getHighlightLegalMoves()) {
//            for (final Move move : pieceLegalMoves(board)) {
//                if (move.getDestinationCoordinate() == this.tileId) {
//                    try {
//                        add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
//                    }
//                    catch (final IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//    private Collection<Move> pieceLegalMoves(final Board board) {
//        if(humanMovedPiece != null && humanMovedPiece.getPieceAllegiance() == board.getCurrentPlayer().getAlliance()) {
//            return humanMovedPiece.calculateLegalMoves(board);
//        }
//        return Collections.emptyList();
//    }
//
//    private void assignTilePieceIcon(final Board board) {
//        this.removeAll();
//        if(board.getTile(this.tileId).isTileOccupied()) {
//            try{
//                final BufferedImage image = ImageIO.read(new File(pieceIconPath +
//                        board.getTile(this.tileId).getPiece().getPieceAllegiance().toString().substring(0, 1) + "" +
//                        board.getTile(this.tileId).getPiece().toString() +
//                        ".gif"));
//                add(new JLabel(new ImageIcon(image)));
//            } catch(final IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void assignTileColor() {
//        if (BoardUtils.INSTANCE.FIRST_ROW.get(this.tileId) ||
//                BoardUtils.INSTANCE.THIRD_ROW.get(this.tileId) ||
//                BoardUtils.INSTANCE.FIFTH_ROW.get(this.tileId) ||
//                BoardUtils.INSTANCE.SEVENTH_ROW.get(this.tileId)) {
//            setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
//        } else if(BoardUtils.INSTANCE.SECOND_ROW.get(this.tileId) ||
//                BoardUtils.INSTANCE.FOURTH_ROW.get(this.tileId) ||
//                BoardUtils.INSTANCE.SIXTH_ROW.get(this.tileId)  ||
//                BoardUtils.INSTANCE.EIGHTH_ROW.get(this.tileId)) {
//            setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
//        }
//    }
//}
//}
