package cz.cvut.fel.pjv.board.tiles;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import cz.cvut.fel.pjv.pieces.Piece;
import static cz.cvut.fel.pjv.board.BoardUtils.SET_OF_TILES;


public  abstract class Tile {
//  each tile has row and column
    private final int tileRow;
    private final int tileColumn;

//  creates a hashtable as a "cache" to speed up things
    private static final Table<Integer, Integer, EmptyTile> TILES_CACHE
            = createAllPossibleEmptyTiles();

//  method for creating a hashtable of all possible empty tiles
    private static Table <Integer, Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Table<Integer, Integer, EmptyTile> emptyTileTable = HashBasedTable.create();
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                emptyTileTable.put(i, j, new EmptyTile(i,j));
            }
        }
        return ImmutableTable.copyOf(emptyTileTable);
    }
//  creates a tile, if it should be empty it returns it from the cache else creates a new tile with piece on it
    public static Tile createTile(final int tileRow, final int tileColumn,
                                  final Piece piece) {
        return piece == null ? TILES_CACHE.get(tileRow, tileColumn)
                : new OccupiedTile(tileRow, tileColumn, piece);
    }

    Tile(final int tileRow, final int tileColumn) {
        this.tileRow = tileRow;
        this.tileColumn = tileColumn;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();
}
