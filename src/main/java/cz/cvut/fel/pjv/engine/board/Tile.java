package cz.cvut.fel.pjv.engine.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import cz.cvut.fel.pjv.engine.pieces.Piece;

import static cz.cvut.fel.pjv.engine.board.BoardUtils.SET_OF_TILES;


public class Tile {
    //  each tile has row and column
    private final int tileRow;
    private final int tileColumn;
    private Piece pieceOnTile;
    private final String tileNotation;
    //  creates a hashtable as a "cache" to speed up things
    private static final Table<Integer, Integer, Tile> TILES_CACHE
            = createAllPossibleEmptyTiles();

    private Tile(final int tileRow,
                 final int tileColumn,
                 final String tileNotation) {
        this.tileRow = tileRow;
        this.tileColumn = tileColumn;
        this.tileNotation = tileNotation;
        this.pieceOnTile = null;
    }

    //  method for creating a hashtable of all possible empty tiles
    private static Table<Integer, Integer, Tile> createAllPossibleEmptyTiles() {
        final Table<Integer, Integer, Tile> emptyTileTable = HashBasedTable.create();
        int counter = 0;
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                emptyTileTable.put(i, j, new Tile(i, j, BoardUtils.TILE_NOTATION.get(counter)));
                counter++;
            }
        }
        return ImmutableTable.copyOf(emptyTileTable);
    }

    //  creates a tile, if it should be empty it returns it from the cache else creates a new tile with piece on it
    static Tile createTile(final int tileRow, final int tileColumn,
                           final Piece piece) {
        Tile tile = TILES_CACHE.get(tileRow, tileColumn);
        if (piece == null) {
            return tile;
        } else {
            tile.setPieceOnTile(piece);
            return tile;
        }
    }

    public String toString() {
        if (isOccupied()) {
            return getPiece().getPieceColour().isWhite()
                    ? getPiece().toString().toUpperCase() : getPiece().toString();
        } else {
            return "-";
        }
    }

    String algebraicToString() {
        return this.getTileNotation();
    }

    public int getTileRow() {
        return tileRow;
    }

    public int getTileColumn() {
        return tileColumn;
    }

    private String getTileNotation() {
        return tileNotation;
    }

    public Piece getPieceOnTile() {
        return pieceOnTile;
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
}
