package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int pieceRow;
    protected final int pieceColumn;
    protected final Colour pieceColour;
    protected final boolean isFirstMove;

    public Piece(final int pieceRow, final int pieceColumn, final Colour pieceColour) {
        this.pieceRow = pieceRow;
        this.pieceColumn = pieceColumn;
        this.pieceColour = pieceColour;
        this.isFirstMove = false;
    }

    public int getPieceRow() {
        return this.pieceRow;
    }

    public int getPieceColumn() {
        return this.pieceColumn;
    }

    public Colour getPieceColour(){
        return this.pieceColour;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract Collection<Move> calculateMoves(final Board board);

//    string feedback for printing pieces on board
    public enum PieceType {
        PAWN("[P]"),
        KNIGHT("[N]"),
        BISHOP("[B]"),
        ROOK("[R]"),
        QUEEN("[Q]"),
        KING("[K]");

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
