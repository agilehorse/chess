package cz.cvut.fel.pjv.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.moves.AttackMove;
import cz.cvut.fel.pjv.board.moves.Move;
import cz.cvut.fel.pjv.board.moves.NormalMove;
import cz.cvut.fel.pjv.board.tiles.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.board.BoardUtils.OFFSETS;
import static cz.cvut.fel.pjv.board.BoardUtils.SET_OF_TILES;

public class Knight extends Piece {

    private boolean isCastled;
    private boolean isInCheck;
    private boolean isInStaleMate;
    private boolean isInCheckMate;
    private boolean canEscape;
    private static int[] KING_OFFSETS = {2, 1, -1, -2};

    public Knight(
                  int pieceRow,
                  int pieceColumn,
                  Colour pieceColour) {
        super(PieceType.KNIGHT, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int rowOffset : KING_OFFSETS) {
            for (int columnOffset : KING_OFFSETS) {
                int rowMod = rowOffset % 2;
                int colMod = columnOffset % 2;
                if (rowMod != colMod) {
                    if (BoardUtils.isValidTileCoordinate(this.pieceRow + rowOffset,
                            this.pieceColumn + columnOffset)) {
                        final Tile targetTile = board.getTile(this.pieceRow + rowOffset,
                                this.pieceColumn + columnOffset);
                        if (!targetTile.isOccupied()) {
                            legalMoves.add(new NormalMove(board,
                                    this,
                                    this.pieceRow + rowOffset,
                                    this.pieceColumn + columnOffset));
                        } else {
                            Piece pieceAtDestinationTile = targetTile.getPiece();

                            if (pieceAtDestinationTile.getPieceColour() != this.getPieceColour()) {
                                legalMoves.add(new AttackMove(board, this,
                                        this.pieceRow + rowOffset,
                                        this.pieceColumn + columnOffset,
                                        pieceAtDestinationTile));
                            }
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Piece moveIt(Move move) {
        return new Knight(move.getNewRow(), move.getNewColumn(), move.getMovedPiece().getPieceColour());

    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
