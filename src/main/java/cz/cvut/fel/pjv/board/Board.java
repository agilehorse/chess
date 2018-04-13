package cz.cvut.fel.pjv.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.tiles.*;
import cz.cvut.fel.pjv.pieces.*;
import java.util.*;
import static cz.cvut.fel.pjv.board.BoardUtils.ALL_TILES;
import static cz.cvut.fel.pjv.board.BoardUtils.SET_OF_TILES;



public class Board {
//  Board is represented by list of tiles and white and black pieces
    private final List<Tile> chessBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

//  constructor calls a builder to build a board and also calls methods
// for getting active pieces of white team and black team
    private Board(Builder builder) {
        this.chessBoard = createChessBoard(builder);
        this.whitePieces = getActivePieces(this.chessBoard, Colour.WHITE);
        this.blackPieces = getActivePieces(this.chessBoard, Colour.BLACK);
//      creates a collection of white legal moves and black legal moves
        final Collection<Move> whiteBasicLegalMoves
                = calculateMoves(this.whitePieces);
        final Collection<Move> blackBasicLegalMoves
                = calculateMoves(this.blackPieces);
    }
//  builds a giant string from string values which represent a given tile
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ALL_TILES; i++) {
            final String tileText = this.chessBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
//            after each row new line is added so it can look as a board
            if ((i+1) % BoardUtils.SET_OF_TILES == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private Collection<Move> calculateMoves(Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
//          for each piece in the collection it calculates its legal moves
            legalMoves.addAll(piece.calculateMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }
//  returns a list of current active pieces of a given colour
    private static Collection<Piece> getActivePieces(final List<Tile> chessBoard,
                                                    final Colour colour) {
        final List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : chessBoard) {
            if (tile.isOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceColour() == colour){
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }
//  return a concrete tile object by given parameters
    public Tile getTile(final int tileRow, final int tileColumn) {
        return chessBoard.get((tileRow*8)+tileColumn);
    }
//  creates a standard chess board, 8x8 (one set=8), return a list of 64 tiles
    private static List<Tile> createChessBoard(final Builder builder){
        final Tile[] tiles = new Tile[ALL_TILES];
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                tiles[(i*8)+j] = Tile.createTile(i, j, builder.boardConfiguration.get(i,j));
            }
        }
        return ImmutableList.copyOf(tiles);
    }
//  creates a board with the help of a builder, and puts standard chess pieces on their standard positions
    public static Board createStandardBoard(){
        final Builder builder = new Builder();
//        BLACK
        builder.putPiece(new Rook(  0,0, Colour.BLACK));
        builder.putPiece(new Knight(0,1, Colour.BLACK));
        builder.putPiece(new Bishop(0,2, Colour.BLACK));
        builder.putPiece(new Queen( 0,3, Colour.BLACK));
        builder.putPiece(new King(  0,4, Colour.BLACK));
        builder.putPiece(new Bishop(0,5, Colour.BLACK));
        builder.putPiece(new Knight(0,6, Colour.BLACK));
        builder.putPiece(new Rook(  0,7, Colour.BLACK));
        builder.putPiece(new Pawn(  1,0, Colour.BLACK));
        builder.putPiece(new Pawn(  1,1, Colour.BLACK));
        builder.putPiece(new Pawn(  1,2, Colour.BLACK));
        builder.putPiece(new Pawn(  1,3, Colour.BLACK));
        builder.putPiece(new Pawn(  1,4, Colour.BLACK));
        builder.putPiece(new Pawn(  1,5, Colour.BLACK));
        builder.putPiece(new Pawn(  1,6, Colour.BLACK));
        builder.putPiece(new Pawn(  1,7, Colour.BLACK));
//        WHITE
        builder.putPiece(new Pawn(  6,0, Colour.WHITE));
        builder.putPiece(new Pawn(  6,1, Colour.WHITE));
        builder.putPiece(new Pawn(  6,2, Colour.WHITE));
        builder.putPiece(new Pawn(  6,3, Colour.WHITE));
        builder.putPiece(new Pawn(  6,4, Colour.WHITE));
        builder.putPiece(new Pawn(  6,5, Colour.WHITE));
        builder.putPiece(new Pawn(  6,6, Colour.WHITE));
        builder.putPiece(new Pawn(  6,7, Colour.WHITE));
        builder.putPiece(new Rook(  7,0, Colour.WHITE));
        builder.putPiece(new Knight(7,1, Colour.WHITE));
        builder.putPiece(new Bishop(7,2, Colour.WHITE));
        builder.putPiece(new Queen( 7,3, Colour.WHITE));
        builder.putPiece(new King(  7,4, Colour.WHITE));
        builder.putPiece(new Bishop(7,5, Colour.WHITE));
        builder.putPiece(new Knight(7,6, Colour.WHITE));
        builder.putPiece(new Rook(  7,7, Colour.WHITE));

        builder.setMove(Colour.WHITE);
        return builder.build();
    }

    public static class Builder {

        Table<Integer, Integer, Piece> boardConfiguration;
        Colour nextMove;

        public Builder(){
            this.boardConfiguration = HashBasedTable.create();
        }

        public Builder putPiece(final Piece piece){
            this.boardConfiguration.put(piece.getPieceRow(),
                    piece.getPieceColumn(), piece);
            return this;
        }
//      sets next move a of a player
        public Builder setMove(final Colour colour) {
            this.nextMove = nextMove;
            return this;
        }
//      builds a new board
        public Board build() {
            return new Board(this);
        }
    }
}
