package cz.cvut.fel.pjv.player;

import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;
import cz.cvut.fel.pjv.pieces.King;
import cz.cvut.fel.pjv.pieces.Piece;

import java.util.Collection;

public abstract class Player {
    protected final Board board;
    protected final King playersKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    protected Player(Board board, King playerKing, Collection<Move> legalMoves, boolean isInCheck) {
        this.board = board;
        this.playersKing = kingInit();
        this.legalMoves = legalMoves;
        this.isInCheck = isInCheck();
    }

    private King kingInit() {
        return null;
    }

    King getKing() {
        return this.playersKing;
    }

    abstract boolean isInCheck();

    abstract Colour getColour();

    abstract Player getOpponent();

    abstract Collection<Piece> getRemainingPieces();
}
