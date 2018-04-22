package cz.cvut.fel.pjv.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    public Queen(int pieceRow,
                 int pieceColumn,
                 Colour pieceColour) {

        super(pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves
                = new ArrayList<>(sliderMovesCalculator.calculateStraightSliderMoves(board, this, 7));
        legalMoves.addAll(sliderMovesCalculator.calculateDiagonalSliderMoves(board, this, 7));

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
