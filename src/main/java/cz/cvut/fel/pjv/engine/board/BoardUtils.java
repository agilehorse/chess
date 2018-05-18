package cz.cvut.fel.pjv.engine.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BoardUtils {
//  columns which might cause exceptions when calculating news
//  basic important numbers

    static final int ALL_TILES = 64;
    public static final int SET_OF_TILES = 8;
    public static int[] OFFSETS = {1, -1};
    static final List<String> TILE_NOTATION = initAlgebraicNotation();
    private static final Map<String,  List<Integer>> POSITION_TO_COORDINATES = translateNotationToCoords();

    private static List<String> initAlgebraicNotation() {
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

    private static Map<String,  List<Integer>> translateNotationToCoords() {
        final Map<String,  List<Integer>> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < ALL_TILES; i++) {
            List<Integer> coordinates = new ArrayList<Integer>();
            coordinates.add(i/8);
            coordinates.add(i%8);
            positionToCoordinate.put(TILE_NOTATION.get(i), coordinates);
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

    public static List<Integer> getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATES.get(position);
    }

    public static String getPositionAtCoordinate(final int row,
                                          final int column) {
        return TILE_NOTATION.get((row * 8) + column);
    }
}
