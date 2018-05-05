package cz.cvut.fel.pjv.engine.pieces;

import com.google.common.collect.ImmutableList;
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
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
