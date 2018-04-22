package cz.cvut.fel.pjv.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;

import java.util.Collection;

public class Bishop extends Piece {


    public Bishop(int pieceRow,
                  int pieceColumn,
                  Colour pieceColour) {

        super(pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(final Board board) {

        return ImmutableList.copyOf(sliderMovesCalculator.calculateDiagonalSliderMoves(board, this, 7));
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }
}