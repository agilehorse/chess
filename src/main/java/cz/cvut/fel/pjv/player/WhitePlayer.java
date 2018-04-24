package cz.cvut.fel.pjv.player;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.moves.Move;
import cz.cvut.fel.pjv.pieces.*;
import java.util.Collection;


public class WhitePlayer extends Player {
    public WhitePlayer(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        super(board, legalMoves, opponentMoves);
    }

    King getKing() {
        return this.playersKing;
    }

    @Override
    public Colour getColour() {
        return Colour.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    Collection<Piece> getInactivePieces() {
        return null;
    }

    @Override
    public MoveMaker makeMove(Move move) {
        return null;
    }

    protected Collection<Move> calculateCastling(final Collection<Move> playerMoves,
                                                 final Collection<Move> opponentMoves) {
        return ImmutableList.copyOf(castlingMovesCalculator.execute(this.board, this, 7, playerMoves, opponentMoves));
    }
}
