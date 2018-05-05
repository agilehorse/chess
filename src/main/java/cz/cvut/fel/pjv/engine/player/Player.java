package cz.cvut.fel.pjv.engine.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.MoveType;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.engine.pieces.PieceType.KING;

public abstract class Player {

    protected Board board;
    King playersKing;
    private Collection<Move> legalMoves;
    private boolean isInCheck;

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

    public boolean initiateMove(Move move) {
        if (move == null || !isMoveLegal(move) || leadsToCheck(move)) {
            return false;
        } else {
            move.execute();
            this.getOpponent();
            if (!calculateCheck(move.getNewRow(),
                    move.getNewColumn(), this.legalMoves).isEmpty()) {
                this.getOpponent().isInCheck = true;
            }
            if (move.getMoveType() == MoveType.CASTLE) {
                this.playersKing.setCastled(true);
            }
            return true;
        }
    }

    private boolean leadsToCheck(Move move) {
        final Collection<Move> kingAttacks = calculateCheck(move.getNewRow(), move.getNewColumn(), board.getCurrentPlayer().getOpponent().getLegalMoves());
        return !kingAttacks.isEmpty();
    }

    private boolean canEscapeCheck() {
        Collection<Move> kingsMoves = new ArrayList<>(this.playersKing.calculateMoves(board));

        Collection<Move> opponentMoves = this.getOpponent().getLegalMoves();
        for (final Move kingMove: kingsMoves) {
            for (final Move opponentMove: opponentMoves) {
                if (opponentMove.getMoveType() == MoveType.ATTACK
                        && opponentMove.getDestinationTile() == kingMove.getDestinationTile()) {
                    kingsMoves.remove(kingMove);
                }
            }
        }
        return kingsMoves.isEmpty();
    }

    public void setLegalMoves(Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,
                calculateCastling(legalMoves, opponentMoves)));
    }

    public Move findMove(final Tile sourceTile, final Tile destinationTile) {
        for (final Move move: this.legalMoves) {
            if (move.getSourceTile() == sourceTile && move.getDestinationTile() == destinationTile) {
                return move;
            }
        }
        return null;
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

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate(){
        return this.isInCheck && canEscapeCheck();
    }

    public boolean isInStaleMate(){
        return !this.isInCheck && canEscapeCheck();
    }

    public boolean isCastled() {
        return this.playersKing.isCastled();
    }

    private boolean isMoveLegal(Move move) {
        return this.legalMoves.contains(move);
    }

    protected abstract Collection<Move> calculateCastling(final Collection<Move> playerMoves,
                                                          final Collection<Move> opponentMoves);
}
