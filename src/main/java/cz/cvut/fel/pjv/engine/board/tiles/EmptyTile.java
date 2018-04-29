package cz.cvut.fel.pjv.engine.board.tiles;

import cz.cvut.fel.pjv.engine.pieces.Piece;

public class EmptyTile extends Tile {

    EmptyTile(final int tileRow, final int tileColumn, final String tileNotation) {
        super(tileRow, tileColumn, tileNotation);
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }

    @Override
    public String toString() {
        return "[ ]";
    }
}
