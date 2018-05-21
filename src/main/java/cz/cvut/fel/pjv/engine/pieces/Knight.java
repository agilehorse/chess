package cz.cvut.fel.pjv.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.moves.AttackMove;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.NormalMove;
import cz.cvut.fel.pjv.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    private static int[] KING_OFFSETS_EVEN = {2, -2};
    private static int[] KING_OFFSETS_ODD = {1, -1};


    public Knight(
            int pieceRow,
            int pieceColumn,
            Colour pieceColour) {
        super(PieceType.KNIGHT, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        return ImmutableList.copyOf(Iterables.unmodifiableIterable(Iterables.concat(calculateKnightMoves(board, KING_OFFSETS_EVEN, KING_OFFSETS_ODD),
                calculateKnightMoves(board, KING_OFFSETS_ODD, KING_OFFSETS_EVEN))));
    }

    private Collection<Move> calculateKnightMoves(final Board board,
                                                  final int[] rowOffsets,
                                                  final int[] columnOffsets) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int rowOffset : rowOffsets) {
            for (int columnOffset : columnOffsets) {
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
        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
