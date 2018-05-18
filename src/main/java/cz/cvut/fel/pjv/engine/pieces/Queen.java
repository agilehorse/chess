package cz.cvut.fel.pjv.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import java.util.Collection;

public class Queen extends Piece {

    public Queen(
                 int pieceRow,
                 int pieceColumn,
                 Colour pieceColour) {
        super(PieceType.QUEEN, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
       return ImmutableList.copyOf(Iterables.concat(PieceMoveCalculator.calculateStraightSliderMoves(board, this, 8),
                PieceMoveCalculator.calculateDiagonalSliderMoves(board, this, 8)));

    }

    @Override
    public Piece returnImposter(int row, int column) {
        return new Queen(row, column, this.getPieceColour());
    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
