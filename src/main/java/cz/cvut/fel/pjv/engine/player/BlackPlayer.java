package cz.cvut.fel.pjv.engine.player;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.*;
import java.util.Collection;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        super(board, legalMoves, opponentMoves);
    }

    King getKing() {
        return this.playersKing;
    }

    @Override
    public Colour getColour() {
        return Colour.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    protected Collection<Move> calculateCastling(final Collection<Move> playerMoves,
                                                 final Collection<Move> opponentMoves) {
        return ImmutableList.copyOf(PieceMoveCalculator.calculateCastlingMoves(this.board, this, 0, opponentMoves));
    }

    @Override
    public String toString() {
        return "Black Player";
    }
}
