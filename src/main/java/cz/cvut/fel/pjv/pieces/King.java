package cz.cvut.fel.pjv.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    public King(
                int pieceRow,
                int pieceColumn,
                Colour pieceColour) {
        super(PieceType.KING, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves
                = new ArrayList<>(sliderMovesCalculator.calculateStraightSliderMoves(board, this, 1));
        legalMoves.addAll(sliderMovesCalculator.calculateDiagonalSliderMoves(board, this, 1));
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Piece moveIt(Move move) {
        return new King(move.getNewRow(), move.getNewColumn(), move.getMovedPiece().getPieceColour());
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}
