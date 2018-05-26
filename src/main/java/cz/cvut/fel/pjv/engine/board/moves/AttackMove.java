package cz.cvut.fel.pjv.engine.board.moves;


import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttackMove extends Move {

    private MoveType moveType;
    private final Piece attackedPiece;
    private final static Logger LOGGER = Logger.getLogger(AttackMove.class.getName());

    public AttackMove(final Board board,
                      final Piece movedPiece,
                      final int newRow,
                      final int newColumn,
                      final Piece attackedPiece) {
        super(board, movedPiece, newRow, newColumn);
        this.moveType = MoveType.ATTACK;
        this.attackedPiece = attackedPiece;
    }
//  method for execution of a legal attack move
    @Override
    public void execute() {
//        sets PGN string format
        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        this.getMovedPiece().move(this.getNewRow(), this.getNewColumn());
        this.getMovedPiece().setFirstMove(false);
        //noinspection AccessStaticViaInstance
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
//        switches turn to opponent
        this.board.setEnPassantPawn(null);
        LOGGER.log(Level.INFO, "Attack move was made to tile: " +
                getDestinationTile().toString());
    }
//  method for validating if the move will leave current player in check
    @Override
    public boolean freeFromCheck() {
        boolean invalid = false;
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
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
        this.getDestinationTile().setPieceOnTile(this.attackedPiece);
        return !invalid;
    }
//  returns PGN notation of move
    @Override
    public String toString() {
//        source column
        if (this.movedPiece.getPieceType() == PieceType.PAWN) {
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPieceRow(),
                    this.movedPiece.getPieceColumn()).substring(0, 1)
//                    x means attack and finally destination tile
                    + "x" + BoardUtils.getPositionAtCoordinate(this.getNewRow(),
                    this.getNewColumn());
        } else {
//      for non pawn move notation is initial character of piece type,
//      disambiguation file differentiates if for ex. two same rooks have same attack move,
//      return source column
            return movedPiece.getPieceType() + disambiguationFile() + "x"
//                    destination tile notation
                    + BoardUtils.getPositionAtCoordinate(this.getNewRow(),
                    this.getNewColumn());
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AttackMove)) return false;
        if (!super.equals(o)) return false;
        AttackMove that = (AttackMove) o;
        return Objects.equals(getAttackedPiece(), that.getAttackedPiece());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAttackedPiece());
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public Piece getAttackedPiece() {
        return this.attackedPiece;
    }
}