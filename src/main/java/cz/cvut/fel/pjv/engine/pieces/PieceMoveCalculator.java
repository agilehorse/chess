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

import static cz.cvut.fel.pjv.engine.board.BoardUtils.STANDARD_OFFSETS;

public class PieceMoveCalculator {
//method for calculating castle moves
    public static Collection<Move> calculateCastlingMoves(final Board board,
                                                          final Player player,
                                                          final int row,
                                                          final Collection<Move> opponentMoves) {
        final List<Move> castleMoves = new ArrayList<>();
//        if conditions for castle are met
        if (player.getPlayersKing().isFirstMove() && !player.isInCheck() &&
                !player.isCastled()) {
//            if any tile between rook and king isn't occupied
            if (!Board.getTile(row, 5).isOccupied()
                    && !Board.getTile(row, 6).isOccupied()) {
                final Tile rookTile = Board.getTile(row, 7);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
//                    if any tile between rook and king is attacked by enemy piece
                    if (!Player.kingIsAttacked(row, 5, opponentMoves)
                            && !Player.kingIsAttacked(row, 6, opponentMoves)
                            && rookTile.getPiece().getPieceType() == PieceType.ROOK) {
//                        new castle move added
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
            //            if any tile between rook and king isn't occupied
            if (!Board.getTile(row, 1).isOccupied()
                    && !Board.getTile(row, 2).isOccupied()
                    && !Board.getTile(row, 3).isOccupied()) {
                final Tile rookTile = Board.getTile(row, 0);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    //                    if any tile between rook and king is attacked by enemy piece
                    if (!Player.kingIsAttacked(row, 1, opponentMoves)
                            && !Player.kingIsAttacked(row, 2, opponentMoves)
                            && !Player.kingIsAttacked(row, 3, opponentMoves)
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
// method for calculating diagonal moves
    static List<Move> calculateDiagonalSliderMoves(final Board board,
                                                   final Piece piece,
                                                   final int maxReach) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int rowOffset : STANDARD_OFFSETS) {
            for (int columnOffset : STANDARD_OFFSETS) {
                for (int i = 1; i <= maxReach; i++) {
//                    if the target tile lies on board
                    if (BoardUtils.isValidTileCoordinate(
                            piece.pieceRow + i * rowOffset,
                            piece.pieceColumn + i * columnOffset)) {
                        final Tile targetTile = Board.getTile(
                                piece.pieceRow + i * rowOffset,
                                piece.pieceColumn + i * columnOffset);
                        if (!targetTile.isOccupied()) {
//                            if it's not occupied creates a normal move
                            legalMoves.add(new NormalMove(board,
                                    piece,
                                    piece.pieceRow + i * rowOffset,
                                    piece.pieceColumn + i * columnOffset));
                        } else {
//                            if it's occupied creates attack move
                            Piece pieceAtDestinationTile = targetTile.getPiece();
                            if (pieceAtDestinationTile.getPieceColour() !=
                                    piece.getPieceColour()) {
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

    static List<Move> calculateStraightSliderMoves(final Board board, final Piece piece,
                                                   final int maxReach) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int columnOffset : STANDARD_OFFSETS) {
            for (int i = 1; i <= maxReach; i++) {
                //                    if the target tile lies on board
                if (BoardUtils.isValidTileCoordinate(piece.pieceRow,
                        piece.pieceColumn + i * columnOffset)) {
                    final Tile targetTile = Board.getTile(piece.pieceRow,
                            piece.pieceColumn + i * columnOffset);
                    //                            if it's not occupied creates a normal move
                    if (!targetTile.isOccupied()) {
                        legalMoves.add(new NormalMove(board,
                                piece,
                                piece.pieceRow,
                                piece.pieceColumn + i * columnOffset));
                    } else {
                        Piece pieceAtDestinationTile = targetTile.getPiece();
                        if (pieceAtDestinationTile.getPieceColour() !=
                                piece.getPieceColour()) {
                            //                            if it's occupied creates attack move
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
        for (int rowOffset : STANDARD_OFFSETS) {
            for (int i = 1; i <= maxReach; i++) {
                //                    if the target tile lies on board
                if (BoardUtils.isValidTileCoordinate(
                        piece.pieceRow + i * rowOffset,
                        piece.pieceColumn)) {
                    final Tile targetTile = Board.getTile(
                            piece.pieceRow + i * rowOffset,
                            piece.pieceColumn);
                    //                            if it's not occupied creates a normal move
                    if (!targetTile.isOccupied()) {
                        legalMoves.add(new NormalMove(board,
                                piece,
                                piece.pieceRow + i * rowOffset,
                                piece.pieceColumn));
                    } else {
                        //                            if it's occupied creates attack move
                        Piece pieceAtDestinationTile = targetTile.getPiece();
                        if (pieceAtDestinationTile.getPieceColour() !=
                                piece.getPieceColour()) {
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
