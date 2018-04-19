package cz.cvut.fel.pjv.board;

public class BoardUtils {
//  columns which might cause exceptions when calculating news
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);
//  basic important numbers
    public static final int ALL_TILES = 64;
    public static final int SET_OF_TILES = 8;

    public BoardUtils() {
        throw new RuntimeException("You cannot run me!");
    }

//    TODO
    private static boolean[] initColumn(int columnNumber) {
        return null;
    }

//  checks if the coordinate is valid
    public static boolean isValidTileCoordinate(final int row, final int column) {
        return (row >= 0 && row < SET_OF_TILES) && (column >= 0 && column < SET_OF_TILES);
    }
}
