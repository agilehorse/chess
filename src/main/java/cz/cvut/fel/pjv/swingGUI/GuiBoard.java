//package cz.cvut.fel.pjv.swingGUI;
//
//import com.google.common.collect.Lists;
//import cz.cvut.fel.pjv.engine.board.Board;
//import cz.cvut.fel.pjv.engine.board.BoardUtils;
//import cz.cvut.fel.pjv.engine.board.moves.Move;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static cz.cvut.fel.pjv.swingGUI.MainPanel.BOARD_PANEL_DIMENSION;
//
//public class GuiBoard extends JPanel {
//    final List<GuiTile> boardTiles;
//
//    GuiBoard() {
//        super(new GridLayout(8, 8));
//        this.boardTiles = new ArrayList<>();
//        for (int i = 0; i < BoardUtils.; i++) {
//            final GuiTile GuiTile = new GuiTile(this, i);
//            this.boardTiles.add(GuiTile);
//            add(GuiTile);
//        }
//        setPreferredSize(BOARD_PANEL_DIMENSION);
//        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        setBackground(Color.decode("#8B4726"));
//        validate();
//    }
//
//    void drawBoard(final Board board) {
//        removeAll();
//        for (final GuiTile boardTile : boardDirection.traverse(boardTiles)) {
//            boardTile.drawTile(board);
//            add(boardTile);
//        }
//        validate();
//        repaint();
//    }
//
//    void setTileDarkColor(final Board board,
//                          final Color darkColor) {
//        for (final GuiTile boardTile : boardTiles) {
//            boardTile.setDarkTileColor(darkColor);
//        }
//        drawBoard(board);
//    }
//
//    void setTileLightColor(final Board board,
//                           final Color lightColor) {
//        for (final GuiTile boardTile : boardTiles) {
//            boardTile.setLightTileColor(lightColor);
//        }
//        drawBoard(board);
//    }
//
//    enum BoardDirection {
//        NORMAL {
//            @Override
//            List<GuiTile> traverse(final List<GuiTile> boardTiles) {
//                return boardTiles;
//            }
//
//            @Override
//            BoardDirection opposite() {
//                return FLIPPED;
//            }
//        },
//        FLIPPED {
//            @Override
//            List<GuiTile> traverse(final List<GuiTile> boardTiles) {
//                return Lists.reverse(boardTiles);
//            }
//
//            @Override
//            BoardDirection opposite() {
//                return NORMAL;
//            }
//        };
//
//        abstract List<GuiTile> traverse(final List<GuiTile> boardTiles);
//
//        abstract BoardDirection opposite();
//
//    }
//
//}
//
//
//
//
