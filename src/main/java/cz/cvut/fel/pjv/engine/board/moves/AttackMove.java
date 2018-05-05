package cz.cvut.fel.pjv.engine.board.moves;


import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.pieces.Piece;

import java.util.Collection;
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
        Collection<Piece> opponentsInactivePieces;
        if (attackedPiece.getPieceColour() == Colour.WHITE) {
            opponentsInactivePieces = board.getInactiveWhitePieces();
        } else {
            opponentsInactivePieces = board.getInactiveBlackPieces();
        }
        opponentsInactivePieces.add(attackedPiece);

        attackedPiece.setActive(false);
        attackedPiece.setPieceRow(-1);
        attackedPiece.setPieceColumn(-1);

        this.movedPiece.move(this);
        movedPiece.setFirstMove(false);
        Board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        board.recalculate();
    }

    @Override
    public Piece getAttackedPiece() {
        return attackedPiece;
    }
}