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
import java.util.logging.Level;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.engine.pieces.PieceType.KING;

public abstract class Player {

    private final static Logger LOGGER = Logger.getLogger(Player.class.getSimpleName());
    protected Board board;
    King playersKing;
    private Collection<Move> legalMoves;
    private boolean isInCheck;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.playersKing = setUpKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,
                calculateCastling(legalMoves, opponentMoves)));
    }
//  method for validating if the current player's king will get in check after current player makes a move
    private Collection<Move> validateForCheck(final Collection<Move> allMoves) {
        List<Move> legalMoves = new ArrayList<>();
        for (final Move move : allMoves) {
            if (move.freeFromCheck()) {
                legalMoves.add(move);
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
//  checks if enemy has attack move on tile with input coordinates, usually checking the king, but also rook while calculating castle moves
    public static boolean kingIsAttacked(final int kingsRow,
                                         final int kingsColumn,
                                         final Collection<Move> opponentMoves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : opponentMoves) {
            if (kingsRow == move.getNewRow() && kingsColumn == move.getNewColumn()) {
                attackMoves.add(move);
            }
        }
        return !ImmutableList.copyOf(attackMoves).isEmpty();
    }
//  sets up king from players pieces
    private King setUpKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType() == KING) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Invalid Board! King is missing.");
    }
// method for initiating move
    public boolean executeMove(final Move move) {
//      if it's not null and player's legal moves contain input move, move is executed
        if (move == null || !isMoveLegal(move)) {
            return false;
        } else {
            move.execute();
            //            if the move is castle, sets king as castled
            if (move.getMoveType() == MoveType.CASTLE) {
                this.playersKing.setCastled();
            }
            return true;
        }
    }
// method for checking if king can perform any move to escape check
    private boolean hasNoEscapeMoves() {
        ArrayList<Move> kingsMoves = new ArrayList<>();
        for (final Move move : this.getLegalMoves()) {
            if (move.getMovedPiece().equals(this.playersKing)) {
                kingsMoves.add(move);
            }
        }
        return kingsMoves.isEmpty();
    }

    public void setLegalMoves(final Collection<Move> legalMoves,
                              final Collection<Move> opponentMoves) {
        final Collection<Move> moves = validateForCheck(legalMoves);
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(moves,
                calculateCastling(moves, opponentMoves)));

    }
// method for finding move by it's destination tile and source tile
    public Move findMove(final Tile sourceTile,
                         final Tile destinationTile) {
        for (final Move move : this.legalMoves) {
            if (move.getSourceTile() == sourceTile && move.getDestinationTile() ==
                    destinationTile) {
                return move;
            }
        }
        return null;
    }
//
    public void setIsInCheck(final Collection<Move> opponentMoves) {
        this.isInCheck = Player.kingIsAttacked(this.playersKing.getPieceRow(),
                this.playersKing.getPieceColumn(),
                opponentMoves);
        if (this.isInCheck) {
            LOGGER.log(Level.INFO, this.toString() + " player is in check.");
        }
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
