package cz.cvut.fel.pjv.engine.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Table;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.*;
import cz.cvut.fel.pjv.engine.player.BlackPlayer;
import cz.cvut.fel.pjv.engine.player.Player;
import cz.cvut.fel.pjv.engine.player.WhitePlayer;
import java.util.*;
import static cz.cvut.fel.pjv.engine.board.BoardUtils.ALL_TILES;
import static cz.cvut.fel.pjv.engine.board.BoardUtils.SET_OF_TILES;


public class Board {
    //  Board is represented by list of tiles and white and black pieces
    private final List<Tile> chessBoard;
    private static Table<Integer, Integer, Piece> boardConfiguration;
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;
    private Collection<Piece> inactiveWhitePieces;
    private Collection<Piece> inactiveBlackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private Player currentPlayer;
    private Pawn enPassantPawn;
    private static Colour nextMove;


    //  constructor calls a boardBuilder to build a board and also calls methods
// for getting active pieces of white team and black team
    public Board() {
        boardConfiguration = HashBasedTable.create();
        setUpStandardBoard();
        this.chessBoard = createChessBoard();
        this.whitePieces = getActivePieces(this.chessBoard, Colour.WHITE);
        this.blackPieces = getActivePieces(this.chessBoard, Colour.BLACK);
//      creates a collection of white legal moves and black legal moves
        Collection<Move> whiteBasicLegalMoves
                = calculateMoves(this.whitePieces);
        Collection<Move> blackBasicLegalMoves
                = calculateMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteBasicLegalMoves, blackBasicLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackBasicLegalMoves, whiteBasicLegalMoves);
        this.currentPlayer = nextMove.getCurrentPlayer(whitePlayer, blackPlayer);
        inactiveWhitePieces = new ArrayList<>();
        inactiveBlackPieces = new ArrayList<>();
        this.enPassantPawn = null;
    }

    public void recalculate() {
        this.whitePieces = getActivePieces(this.chessBoard, Colour.WHITE);
        this.blackPieces = getActivePieces(this.chessBoard, Colour.BLACK);
//      creates a collection of white legal moves and black legal moves
        Collection<Move> whiteBasicLegalMoves
                = calculateMoves(this.whitePieces);
        Collection<Move> blackBasicLegalMoves
                = calculateMoves(this.blackPieces);
        this.whitePlayer.setLegalMoves(whiteBasicLegalMoves, blackBasicLegalMoves);
        this.blackPlayer.setLegalMoves(blackBasicLegalMoves, whiteBasicLegalMoves);
        this.currentPlayer = nextMove.getCurrentPlayer(whitePlayer, blackPlayer);
    }

    private static void setUpStandardBoard() {
        //        BLACK
        putPiece(new Rook(0, 0, Colour.BLACK));
        putPiece(new Knight(0, 1, Colour.BLACK));
        putPiece(new Bishop(0, 2, Colour.BLACK));
        putPiece(new Queen(0, 3, Colour.BLACK));
        putPiece(new King(0, 4, Colour.BLACK));
        putPiece(new Bishop(0, 5, Colour.BLACK));
        putPiece(new Knight(0, 6, Colour.BLACK));
        putPiece(new Pawn(1, 0, Colour.BLACK));
        putPiece(new Rook(0, 7, Colour.BLACK));
        putPiece(new Pawn(1, 1, Colour.BLACK));
        putPiece(new Pawn(1, 2, Colour.BLACK));
        putPiece(new Pawn(1, 3, Colour.BLACK));
        putPiece(new Pawn(1, 4, Colour.BLACK));
        putPiece(new Pawn(1, 5, Colour.BLACK));
        putPiece(new Pawn(1, 6, Colour.BLACK));
        putPiece(new Pawn(1, 7, Colour.BLACK));
        //        WHITE
        putPiece(new Pawn(6, 0, Colour.WHITE));
        putPiece(new Pawn(6, 1, Colour.WHITE));
        putPiece(new Pawn(6, 2, Colour.WHITE));
        putPiece(new Pawn(6, 3, Colour.WHITE));
        putPiece(new Pawn(6, 4, Colour.WHITE));
        putPiece(new Pawn(6, 5, Colour.WHITE));
        putPiece(new Pawn(6, 6, Colour.WHITE));
        putPiece(new Pawn(6, 7, Colour.WHITE));
        putPiece(new Rook(7, 0, Colour.WHITE));
        putPiece(new Knight(7, 1, Colour.WHITE));
        putPiece(new Bishop(7, 2, Colour.WHITE));
        putPiece(new Queen(7, 3, Colour.WHITE));
        putPiece(new King(7, 4, Colour.WHITE));
        putPiece(new Bishop(7, 5, Colour.WHITE));
        putPiece(new Knight(7, 6, Colour.WHITE));
        putPiece(new Rook(7, 7, Colour.WHITE));
        setMove(Colour.WHITE);
    }

    //  builds a giant string from string values which represent a given tile
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ALL_TILES; i++) {
            final String tileText = this.chessBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
//            after each row new line is added so it can look as a board
            if ((i + 1) % BoardUtils.SET_OF_TILES == 0) {
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

    //  return a concrete tile object by given parameters

    private static Collection<Piece> getActivePieces(final List<Tile> chessBoard,
                                                     final Colour colour) {
        final List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : chessBoard) {
            if (tile.isOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceColour() == colour) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    //  creates a standard chess board, 8x8 (one set=8), return a list of 64 tiles
    private static List<Tile> createChessBoard() {
        final Tile[] tiles = new Tile[ALL_TILES];
        for (int i = 0; i < SET_OF_TILES; i++) {
            for (int j = 0; j < SET_OF_TILES; j++) {
                tiles[(i * 8) + j] = Tile.createTile(i, j, boardConfiguration.get(i, j));
            }
        }
        return ImmutableList.copyOf(tiles);
    }

    private static void putPiece(final Piece piece){
        boardConfiguration.put(piece.getPieceRow(),
                piece.getPieceColumn(),
                piece);
    }

    public Tile getTile(final int tileRow, final int tileColumn) {
        return chessBoard.get((tileRow * 8) + tileColumn);
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(),
                this.blackPlayer.getLegalMoves()));
    }

    public static void setMove(final Colour colour) {
        nextMove = colour;
    }

    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

    public Pawn getEnPassantPawn() {
        return enPassantPawn;
    }

    public WhitePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public BlackPlayer getBlackPlayer() {
        return blackPlayer;
    }

    public Collection<Piece> getWhitePieces() {
        return whitePieces;
    }

    public Collection<Piece> getBlackPieces() {
        return blackPieces;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Collection<Piece> getInactiveWhitePieces() {
        return inactiveWhitePieces;
    }

    public Collection<Piece> getInactiveBlackPieces() {
        return inactiveBlackPieces;
    }
}
