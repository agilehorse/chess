package cz.cvut.fel.pjv.pieces;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    public Queen(
                 int pieceRow,
                 int pieceColumn,
                 Colour pieceColour) {
        super(PieceType.QUEEN, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
       return ImmutableList.copyOf(Iterables.concat(sliderMovesCalculator.calculateStraightSliderMoves(board, this, 7),
                sliderMovesCalculator.calculateDiagonalSliderMoves(board, this, 7)));

    }

    @Override
    public Piece moveIt(Move move) {
        return new Queen(move.getNewRow(), move.getNewColumn(), move.getMovedPiece().getPieceColour());

    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
