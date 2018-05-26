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

    public Knight(
            int pieceRow,
            int pieceColumn,
            Colour pieceColour) {
        super(PieceType.KNIGHT, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
//      to get desired L shape move we have to iterate over even column offsets and odd row offsets = standard offsets
//        then the offsets switch first odd then even
        return ImmutableList.copyOf(Iterables.unmodifiableIterable(Iterables.concat(
                calculateKnightMoves(board, KING_OFFSETS_EVEN, BoardUtils.STANDARD_OFFSETS),
                calculateKnightMoves(board, BoardUtils.STANDARD_OFFSETS, KING_OFFSETS_EVEN))));
    }

    private Collection<Move> calculateKnightMoves(final Board board,
                                                  final int[] rowOffsets,
                                                  final int[] columnOffsets) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int rowOffset : rowOffsets) {
            for (int columnOffset : columnOffsets) {
//                check if destination tile is valid
                if (BoardUtils.isValidTileCoordinate(this.pieceRow + rowOffset,
                        this.pieceColumn + columnOffset)) {
//                    gets tile
                    final Tile targetTile = Board.getTile(this.pieceRow + rowOffset,
                            this.pieceColumn + columnOffset);
                    if (!targetTile.isOccupied()) {
//                        if the tile is not occupied creates normal move
                        legalMoves.add(new NormalMove(board,
                                this,
                                this.pieceRow + rowOffset,
                                this.pieceColumn + columnOffset));
                    } else {
                        Piece pieceAtDestinationTile = targetTile.getPiece();
//                  if it's occupied and has and piece on tile has different colour creats attack move
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
