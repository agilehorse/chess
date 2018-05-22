package cz.cvut.fel.pjv.engine.board.moves;


import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

import java.util.Objects;

//  defines new attack move, on an occupied tile
public class AttackMove extends Move {

    private final Piece attackedPiece;
    private MoveType moveType;

    public AttackMove(final Board board,
                      final Piece movedPiece,
                      final int newRow,
                      final int newColumn,
                      final Piece attackedPiece) {
        super(board, movedPiece, newRow, newColumn);
        this.moveType = MoveType.ATTACK;
        this.attackedPiece = attackedPiece;
    }

    @Override
    public boolean equals(Object o) {
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
    public void execute() {
        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        movedPiece.setFirstMove(false);
        //noinspection AccessStaticViaInstance
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        board.setEnPassantPawn(null);
    }

    @Override
    public boolean freeFromCheck() {
        boolean invalid = false;
        final King king = this.board.getCurrentPlayer().getPlayersKing();
        final Tile kingTile = this.board.getTile(king.getPieceRow(), king.getPieceColumn());
        this.attackedPiece.move(-1, -1);
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        this.board.recalculate(false);
        for (final Move move : this.board.getMovesByColour(this.board.getCurrentPlayer().getOpponent().getColour())) {
            if (move.getDestinationTile() == kingTile) {
                invalid = true;
                break;
            }
        }
        this.movedPiece.move(this.getSourceTile().getTileRow(), this.getSourceTile().getTileColumn());
        this.getSourceTile().setPieceOnTile(movedPiece);
        this.getDestinationTile().setPieceOnTile(this.attackedPiece);
        this.attackedPiece.move(getDestinationTile().getTileRow(), getDestinationTile().getTileColumn());
        this.board.recalculate(false);
        return !invalid;
    }

    @Override
    public Piece getAttackedPiece() {
        return this.attackedPiece;
    }

    @Override
    public String toString() {
        if (this.movedPiece.getPieceType() == PieceType.PAWN) {
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPieceRow(),
                    this.movedPiece.getPieceColumn()).substring(0, 1)
                    + "x" + BoardUtils.getPositionAtCoordinate(this.getNewRow(),
                    this.getNewColumn());
        } else {
            return movedPiece.getPieceType() + disambiguationTile() + "x"
                    + BoardUtils.getPositionAtCoordinate(this.getNewRow(),
                    this.getNewColumn());
        }
    }
}