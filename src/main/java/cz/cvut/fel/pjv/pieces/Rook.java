package cz.cvut.fel.pjv.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;

import java.util.Collection;

public class Rook extends Piece{

    public Rook(int pieceRow,
                int pieceColumn,
                Colour pieceColour) {

        super(pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {

        return ImmutableList.copyOf(sliderMovesCalculator.calculateStraightSliderMoves(board, this, 7));
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}
