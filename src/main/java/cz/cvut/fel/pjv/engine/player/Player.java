package cz.cvut.fel.pjv.engine.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.engine.pieces.PieceType.KING;

public abstract class Player {
    protected final Board board;
    King playersKing;
    private final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.playersKing = setUpKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,
                            calculateCastling(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateCheck(this.playersKing.getPieceRow(),
                this.playersKing.getPieceColumn(),
                opponentMoves).isEmpty();
    }

    public static Collection<Move> calculateCheck(int kingsRow, int kingsColumn, Collection<Move> opponentMoves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : opponentMoves) {
            if (kingsRow == move.getNewRow() && kingsColumn == move.getNewColumn()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private King setUpKing() {
        for (Piece piece : getActivePieces()) {
            if (piece.getPieceType() == KING) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Invalid Board! King is missing.");
    }

    public MoveMaker initiateMove(Move move) {
        if (!isMoveLegal(move)) {
            return new MoveMaker(this.board, move, MoveState.INVALID);
        }
        final Board newBoard = move.execute();
        final Collection<Move> kingAttacks
                = Player.calculateCheck(board.getCurrentPlayer().getOpponent().getPlayersKing().getPieceRow(),
                board.getCurrentPlayer().getOpponent().getPlayersKing().getPieceRow(),
                newBoard.getCurrentPlayer().getLegalMoves());
        if (!kingAttacks.isEmpty()) {
            return new MoveMaker(this.board, move, MoveState.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveMaker(newBoard, move, MoveState.VALID);
    }

    private boolean canEscapeCheck() {
        for (final Move move: this.legalMoves) {
            final MoveMaker transition = makeMove(move);
            if (transition.getMoveState().done()) {
                return true;
            }
        }
        return false;
    }

    public King getPlayersKing() {
        return this.playersKing;
    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }

    public abstract Colour getColour();

    public abstract Player getOpponent();

    public abstract Collection<Piece> getActivePieces();

    abstract Collection<Piece> getInactivePieces();

    public abstract MoveMaker makeMove(final Move move);

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate(){
        return this.isInCheck && canEscapeCheck();
    }

    public boolean isInStaleMate(){
        return !this.isInCheck && !canEscapeCheck();
    }

    public boolean isCastled() {
        return false;
    }

    private boolean isMoveLegal(Move move) {
        return this.legalMoves.contains(move);
    }

    protected abstract Collection<Move> calculateCastling(final Collection<Move> playerMoves,
                                                          final Collection<Move> opponentMoves);
}
