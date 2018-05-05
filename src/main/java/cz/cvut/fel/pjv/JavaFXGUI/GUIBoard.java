package cz.cvut.fel.pjv.JavaFXGUI;

import cz.cvut.fel.pjv.engine.board.Board;
import javafx.scene.layout.GridPane;

import static cz.cvut.fel.pjv.JavaFXGUI.Main.*;
import static cz.cvut.fel.pjv.engine.board.BoardUtils.SET_OF_TILES;

public class GUIBoard extends GridPane {


    private static Board engineBoard;
    private static GUITile[][] guiTiles = new GUITile[8][8];

    GUIBoard() {
    }

    public void setBoard(Board engineBoard) {
        GUIBoard.engineBoard = engineBoard;
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                GUITile guiTile = new GUITile(i, j, GUIBoard.engineBoard);
                this.add(guiTile, j, i);
                guiTiles[i][j] = guiTile;
            }
        }
    }

    void clearBoard() {
        this.getChildren().clear();
    }

    static void highlightActiveGUITile(boolean highlight) {
        if (getSourceTile() != null) {
            GUITile sourceGUITile = getSpecificGUITile(Main.getSourceTile().getTileRow(),
                    Main.getSourceTile().getTileColumn());
            if (!highlight) {
                sourceGUITile.getStyleClass().removeAll("active-chess-tile");
                if ((getSourceTile().getTileRow() + getSourceTile().getTileColumn()) % 2 == 0) {
                    sourceGUITile.getStyleClass().add("light");
                } else {
                    sourceGUITile.getStyleClass().add("dark");
                }
            } else {
                sourceGUITile.getStyleClass().add("active-chess-tile");
            }
        }
    }

    static GUITile getSpecificGUITile(int row, int column) {
        return guiTiles[row][column];
    }
}