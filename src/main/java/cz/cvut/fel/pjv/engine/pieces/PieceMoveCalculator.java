package cz.cvut.fel.pjv.engine.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.board.moves.*;
import cz.cvut.fel.pjv.engine.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.engine.board.BoardUtils.OFFSETS;

public class PieceMoveCalculator {

    public static Collection<Move> calculateCastlingMoves(Board board, Player player, int row, Collection<Move> playersMoves, Collection<Move> opponentMoves) {

        final List<Move> castleMoves = new ArrayList<>();
        if (player.getPlayersKing().isFirstMove() && !player.isInCheck() && !player.isCastled()) {
            if (!board.getTile(row, 5).isOccupied()
                    && !board.getTile(row, 6).isOccupied()) {
                final Tile rookTile = board.getTile(row, 7);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateCheck(row, 5, opponentMoves).isEmpty()
                            && Player.calculateCheck(row, 6, opponentMoves).isEmpty()
                            && rookTile.getPiece().getPieceType() == PieceType.ROOK) {
                        castleMoves.add(new KingSideCastle(board,
                                player.getPlayersKing(),
                                row,
                                6,
                                (Rook) rookTile.getPiece(),
                                rookTile.getTileRow(),
                                rookTile.getTileColumn(),
                                row,
                                5));
                    }
                }
            }
            if (!board.getTile(row, 1).isOccupied()
                    && !board.getTile(row, 2).isOccupied()
                    && !board.getTile(row, 3).isOccupied()) {
                final Tile rookTile = board.getTile(row, 0);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateCheck(row, 1, opponentMoves).isEmpty()
                            && Player.calculateCheck(row, 2, opponentMoves).isEmpty()
                            && Player.calculateCheck(row, 3, opponentMoves).isEmpty()
                            && rookTile.getPiece().getPieceType() == PieceType.ROOK) {
                        castleMoves.add(new QueenSideCastle(board,
                                player.getPlayersKing(),
                                row,
                                2, (Rook)
                                rookTile.getPiece(),
                                rookTile.getTileRow(),
                                rookTile.getTileColumn(),
                                row,
                                3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(castleMoves);
    }

    static List<Move> calculateDiagonalSliderMoves(Board board, Piece piece, int maxReach) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int rowOffset : OFFSETS) {
            for (int columnOffset : OFFSETS) {
                for (int i = 1; i <= maxReach; i++) {
                    if (BoardUtils.isValidTileCoordinate(piece.pieceRow + i * rowOffset,
                            piece.pieceColumn + i * columnOffset)) {
                        final Tile targetTile = board.getTile(piece.pieceRow + i * rowOffset,
                                piece.pieceColumn + i * columnOffset);
                        if (!targetTile.isOccupied()) {
                            legalMoves.add(new NormalMove(board,
                                    piece,
                                    piece.pieceRow + i * rowOffset,
                                    piece.pieceColumn + i * columnOffset));
                        } else {
                            Piece pieceAtDestinationTile = targetTile.getPiece();
                            if (pieceAtDestinationTile.getPieceColour() != piece.getPieceColour()) {
                                legalMoves.add(new AttackMove(board, piece,
                                        piece.pieceRow + i * rowOffset,
                                        piece.pieceColumn + i * columnOffset,
                                        pieceAtDestinationTile));
                            }
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    static List<Move> calculateStraightSliderMoves(Board board, Piece piece, int maxReach) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int columnOffset : OFFSETS) {
            for (int i = 1; i <= maxReach; i++) {
                if (BoardUtils.isValidTileCoordinate(piece.pieceRow,
                        piece.pieceColumn + i * columnOffset)) {
                    final Tile targetTile = board.getTile(piece.pieceRow,
                            piece.pieceColumn + i * columnOffset);
                    if (!targetTile.isOccupied()) {
                        legalMoves.add(new NormalMove(board,
                                piece,
                                piece.pieceRow,
                                piece.pieceColumn + i * columnOffset));
                    } else {
                        Piece pieceAtDestinationTile = targetTile.getPiece();
                        if (pieceAtDestinationTile.getPieceColour() != piece.getPieceColour()) {
                            legalMoves.add(new AttackMove(board, piece,
                                    piece.pieceRow,
                                    piece.pieceColumn + i * columnOffset,
                                    pieceAtDestinationTile));
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        for (int rowOffset : OFFSETS) {
            for (int i = 1; i <= maxReach; i++) {
                if (BoardUtils.isValidTileCoordinate(piece.pieceRow + i * rowOffset,
                        piece.pieceColumn)) {
                    final Tile targetTile = board.getTile(piece.pieceRow + i * rowOffset,
                            piece.pieceColumn);
                    if (!targetTile.isOccupied()) {
                        legalMoves.add(new NormalMove(board,
                                piece,
                                piece.pieceRow + i * rowOffset,
                                piece.pieceColumn));
                    } else {
                        Piece pieceAtDestinationTile = targetTile.getPiece();
                        if (pieceAtDestinationTile.getPieceColour() != piece.getPieceColour()) {
                            legalMoves.add(new AttackMove(board, piece,
                                    piece.pieceRow + i * rowOffset,
                                    piece.pieceColumn,
                                    pieceAtDestinationTile));
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
