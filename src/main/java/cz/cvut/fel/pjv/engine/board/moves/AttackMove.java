package cz.cvut.fel.pjv.engine.board.moves;


import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

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
            opponentsInactivePieces = this.board.getInactiveWhitePieces();
        } else {
            opponentsInactivePieces = this.board.getInactiveBlackPieces();
        }
        opponentsInactivePieces.add(attackedPiece);
        this.attackedPiece.setActive(false);
        this.attackedPiece.move(-1, -1);
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        setExecuted(true);
        final King king = this.board.getCurrentPlayer().getPlayersKing();
        final Tile kingTile = this.board.getTile(king.getPieceRow(), king.getPieceColumn());
        this.board.recalculate();
        for (final Move move : this.board.getCurrentPlayer().getOpponent().getLegalMoves()) {
            if (move.getDestinationTile() == kingTile) {
                this.movedPiece.move(this.getSourceTile().getTileRow(), this.getSourceTile().getTileColumn());
                this.getSourceTile().setPieceOnTile(movedPiece);
                this.getDestinationTile().setPieceOnTile(attackedPiece);
                opponentsInactivePieces.remove(attackedPiece);
                this.attackedPiece.setActive(true);
                this.attackedPiece.move(getDestinationTile().getTileRow(), getDestinationTile().getTileColumn());
                setExecuted(false);
            }
        }
        if (isExecuted()) {
            movedPiece.setFirstMove(false);
            this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        }
        this.board.recalculate();
    }

    @Override
    public Piece getAttackedPiece() {
        return attackedPiece;
    }
}