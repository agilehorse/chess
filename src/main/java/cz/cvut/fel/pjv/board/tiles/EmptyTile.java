package cz.cvut.fel.pjv.board.tiles;

import cz.cvut.fel.pjv.pieces.Piece;

public class EmptyTile extends Tile {

    EmptyTile(final int tileRow, final int tileColumn) {
        super(tileRow, tileColumn);
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
