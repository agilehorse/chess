package cz.cvut.fel.pjv.engine.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;

import java.util.List;


public class BoardUtils {
//  columns which might cause exceptions when calculating news
//  basic important numbers
    static final int ALL_TILES = 64;
    public static final int SET_OF_TILES = 8;
    public static int[] OFFSETS = {1, -1};
    public static final List<String> TILE_NOTATION = initTileNotation();
    public static final Table<Integer, Integer, String> POSITION_TO_COORDINATES = translateToCoords();

    private static List<String> initTileNotation() {
        return ImmutableList.copyOf(new String[]{
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        });
    }

    private static Table<Integer,Integer, String> translateToCoords() {
        final Table<Integer, Integer, String> positionToCoordinate = HashBasedTable.create();
        for (int i = 0; i < ALL_TILES; i++) {
            positionToCoordinate.put(i/8, i%8, TILE_NOTATION.get(i));
        }
        return positionToCoordinate;
    }

    public BoardUtils() {
        throw new RuntimeException("You cannot run me!");
    }

//  checks if the coordinate is valid
    public static boolean isValidTileCoordinate(final int row,
                                                final int column) {
        return (row >= 0
                && row < SET_OF_TILES)
                && (column >= 0
                && column < SET_OF_TILES);
    }
}
