package cz.cvut.fel.pjv.engine.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import cz.cvut.fel.pjv.engine.pieces.Piece;

import java.util.Objects;

import static cz.cvut.fel.pjv.engine.board.BoardUtils.SET_OF_TILES;


public class Tile {
    //  each tile has row and column
    private final int tileRow;
    private final int tileColumn;
    private Piece pieceOnTile;
    //  creates a hashtable as a "cache" to speed up things
    private static final Table<Integer, Integer, Tile> TILES_CACHE
            = createAllPossibleEmptyTiles();

    private Tile(final int tileRow,
                 final int tileColumn) {
        this.tileRow = tileRow;
        this.tileColumn = tileColumn;
        this.pieceOnTile = null;
    }

    //  method for creating a hash-table of all possible empty tiles
    private static Table<Integer, Integer, Tile> createAllPossibleEmptyTiles() {
        final Table<Integer, Integer, Tile> emptyTileTable = HashBasedTable.create();
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                emptyTileTable.put(i, j, new Tile(i, j));
            }
        }
        return ImmutableTable.copyOf(emptyTileTable);
    }

    //  creates a tile, if it should be empty it returns it from the cache else creates a new tile with piece on it
    static Tile createTile(final int tileRow,
                           final int tileColumn,
                           final Piece piece) {
        Tile tile = TILES_CACHE.get(tileRow, tileColumn);
        if (piece == null) {
            return tile;
        } else {
            tile.setPieceOnTile(piece);
            return tile;
        }
    }
// returns algebraic notation of tile
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.tileRow, this.tileColumn);
    }

    public int getTileRow() {
        return tileRow;
    }

    public int getTileColumn() {
        return tileColumn;
    }

    public void setPieceOnTile(Piece piece) {
        this.pieceOnTile = piece;
    }

    public boolean isOccupied() {
        return pieceOnTile != null;
    }

    public Piece getPiece() {
        return this.pieceOnTile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return getTileRow() == tile.getTileRow() &&
                getTileColumn() == tile.getTileColumn() &&
                Objects.equals(pieceOnTile, tile.pieceOnTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTileRow(), getTileColumn(), pieceOnTile);
    }
}
