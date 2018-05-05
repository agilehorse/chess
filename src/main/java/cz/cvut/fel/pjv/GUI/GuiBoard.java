package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.board.Board;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.engine.board.BoardUtils.SET_OF_TILES;

class GuiBoard extends JPanel {

    private static final Dimension BOARD_DIMENSION = new Dimension(400,350);
    private List<GuiTile> boardTiles;

    GuiBoard() {
        super(new GridLayout(8,8));
        this.boardTiles = new ArrayList<>();
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                GuiTile guiTile = new GuiTile(this, i,j);
                this.boardTiles.add(guiTile);
                add(guiTile);
            }
        }
        setPreferredSize(BOARD_DIMENSION);
        validate();
    }

    void drawBoard(final Board engineBoard) {
        removeAll();
        for (final GuiTile guiTile : boardTiles) {
            guiTile.drawTile(engineBoard);
            add(guiTile);
        }
        validate();
        repaint();
    }
}
