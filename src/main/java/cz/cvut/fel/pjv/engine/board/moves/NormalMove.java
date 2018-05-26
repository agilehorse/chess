package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.*;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

//  defines new basic new, on empty tile
public class NormalMove extends Move {

    private MoveType moveType;
    private final static Logger LOGGER = Logger.getLogger(NormalMove.class.getName());

    public NormalMove(final Board board,
                      final Piece movedPiece,
                      final int newRow,
                      final int newColumn) {
        super(board, movedPiece, newRow, newColumn);
        this.moveType = MoveType.NORMAL;
    }

//  method for execution of a legal normal move
    public void execute() {
        boolean enPassantSetNow = false;
        //        sets PGN string format
        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
//        if this was pawn jump set en passsant pawn = pawn on which en passant attack can be performed
        if (this.movedPiece.getPieceType() == PieceType.PAWN &&
                (this.getSourceTile().getTileRow() +
                        2 * movedPiece.getPieceColour().getDirection() == getNewRow())) {
            this.board.setEnPassantPawn((Pawn) movedPiece);
            enPassantSetNow = true;
        }
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.movedPiece.setFirstMove(false);
        //noinspection AccessStaticViaInstance
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        //        switches turn to opponent
//      if the en passant pawn wasn't set now, sets it to null
        if (!enPassantSetNow && this.board.getEnPassantPawn() != null &&
                this.board.getEnPassantPawn().getPieceColour() ==
                        this.movedPiece.getPieceColour()) {
            board.setEnPassantPawn(null);
        }
        LOGGER.log(Level.INFO, "Normal move was made to tile: " +
                getDestinationTile().toString());
    }
//  method for validating if the move will leave current player in check
    @Override
    public boolean freeFromCheck() {
        boolean invalid = false;
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.movedPiece);
        final King king = this.board.getCurrentPlayer().getPlayersKing();
        final Tile kingTile = Board.getTile(king.getPieceRow(),
                king.getPieceColumn());
        //        checks if opponents moves attack king's tile, if so makes this method return invalid

        for (final Move move : this.board.getMovesByColour(
                this.board.getCurrentPlayer().getOpponent().getColour())) {
            if (move.getDestinationTile() == kingTile) {
                invalid = true;
                break;
            }
        }
        //      returns everything as it was before checking
        this.movedPiece.move(this.getSourceTile().getTileRow(),
                this.getSourceTile().getTileColumn());
        this.getSourceTile().setPieceOnTile(movedPiece);
        this.getDestinationTile().setPieceOnTile(null);
        return !invalid;
    }

    @Override
    public String toString() {
//        tile notation for move if it was pawn is only notation of destination tile
        if (this.movedPiece.getPieceType() == PieceType.PAWN) {
            return BoardUtils.getPositionAtCoordinate(this.getNewRow(),
                    this.getNewColumn());
        } else {
//            for other pieces it's initial character of piece type,
//              column to differentiate if two pieces of same type
//              had possibility to perform move on the same destination tile
//            finally destination tile
            return this.movedPiece.getPieceType().toString() + disambiguationFile() +
                    BoardUtils.getPositionAtCoordinate(this.getNewRow(),
                            this.getNewColumn());
        }
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NormalMove)) return false;
        if (!super.equals(o)) return false;
        NormalMove that = (NormalMove) o;
        return getMoveType() == that.getMoveType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMoveType());
    }
}
