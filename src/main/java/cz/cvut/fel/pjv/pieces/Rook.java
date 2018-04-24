package cz.cvut.fel.pjv.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;

import java.util.Collection;

public class Rook extends Piece{

    public Rook(
                int pieceRow,
                int pieceColumn,
                Colour pieceColour) {
        super(PieceType.ROOK, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        return ImmutableList.copyOf(sliderMovesCalculator.calculateStraightSliderMoves(board, this, 7));
    }

    @Override
    public Piece moveIt(Move move) {
        return new Rook(move.getNewRow(), move.getNewColumn(), move.getMovedPiece().getPieceColour());
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}
