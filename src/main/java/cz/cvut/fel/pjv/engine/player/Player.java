package cz.cvut.fel.pjv.engine.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import cz.cvut.fel.pjv.GUI.GameSetup;
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

    private Collection<Move> validateForCheck(final Collection<Move> allMoves) {
        List<Move> legalMoves = new ArrayList<>(allMoves);
        List<Move> moves = new ArrayList<>();
        for (final Move move : legalMoves) {
            if (!move.freeFromCheck()) {
                moves.add(move);
            }
        }
        legalMoves.removeAll(moves);
        return ImmutableList.copyOf(legalMoves);
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
        if (move == null || !isMoveLegal(move)) {
            return false;
        } else {
            move.execute();
            if (!calculateCheck(move.getNewRow(),
                    move.getNewColumn(), this.legalMoves).isEmpty()) {
                this.getOpponent().isInCheck = true;
            }
            if (move.getMoveType() == MoveType.CASTLE) {
                this.playersKing.setCastled();
            }
            return true;
        }
    }

    private boolean hasNoEscapeMoves() {
        Collection<Move> kingsMoves = new ArrayList<>(this.playersKing.calculateMoves(board));
        Collection<Move> movesToRemove = new ArrayList<>();
        Collection<Move> opponentMoves = this.board.getMoves(this.board.getCurrentPlayer().getOpponent().getColour());
        for (final Move kingMove : kingsMoves) {
            for (final Move opponentMove : opponentMoves) {
                if (opponentMove.getDestinationTile() == kingMove.getDestinationTile()) {
                    movesToRemove.add(kingMove);
                }
            }
        }
        kingsMoves.removeAll(movesToRemove);
        return kingsMoves.isEmpty();
    }

    public void setLegalMoves(Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        final Collection<Move> moves = validateForCheck(legalMoves);
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(moves,
                calculateCastling(moves, opponentMoves)));
    }

    public Move findMove(final Tile sourceTile, final Tile destinationTile) {
        for (final Move move : this.legalMoves) {
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

    public void setIsInCheck(Collection<Move> opponentMoves) {
        this.isInCheck = !Player.calculateCheck(this.playersKing.getPieceRow(),
                this.playersKing.getPieceColumn(),
                opponentMoves).isEmpty();
    }

    public boolean isInCheckMate() {
        return this.isInCheck && hasNoEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && legalMoves.isEmpty();
    }

    public boolean isCastled() {
        return this.playersKing.isCastled();
    }

    private boolean isMoveLegal(Move move) {
        return this.legalMoves.contains(move);
    }

    protected abstract Collection<Move> calculateCastling(final Collection<Move> playerMoves,
                                                          final Collection<Move> opponentMoves);

    @Override
    public abstract String toString();
}
