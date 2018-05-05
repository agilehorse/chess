package cz.cvut.fel.pjv.engine.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.moves.AttackMove;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.NormalMove;
import cz.cvut.fel.pjv.engine.board.Tile;
import java.util.ArrayList;
import java.util.List;
import static cz.cvut.fel.pjv.engine.board.BoardUtils.OFFSETS;
import static cz.cvut.fel.pjv.engine.board.BoardUtils.SET_OF_TILES;

class sliderMovesCalculator {

    static List<Move> calculateDiagonalSliderMoves(Board board, Piece piece, int maxReach) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int rowOffset : OFFSETS) {
            for (int columnOffset : OFFSETS) {
                for (int i = 1; i < maxReach; i++) {
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
            for (int i = 1; i < maxReach; i++) {
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
            for (int i = 1; i < SET_OF_TILES - 1; i++) {
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
