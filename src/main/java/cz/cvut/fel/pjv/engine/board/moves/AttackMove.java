package cz.cvut.fel.pjv.engine.board.moves;


import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardBuilder;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import java.util.Objects;

//  defines new attack move, on an occupied tile
public class AttackMove extends Move {

    private final Piece attackedPiece;

    public AttackMove(final Board board,
                      final Piece movedPiece,
                      final int newRow,
                      final int newColumn,
                      final Piece attackedPiece) {
        super(MoveType.ATTACK, board, movedPiece, newRow, newColumn);
        this.attackedPiece = attackedPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttackMove)) return false;
        if (!super.equals(o)) return false;
        AttackMove that = (AttackMove) o;
        return Objects.equals(attackedPiece, that.attackedPiece);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), attackedPiece);
    }

    @Override
    public Board execute() {
        final BoardBuilder builder = new BoardBuilder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.putPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            if (!this.attackedPiece.equals(piece)) {
                builder.putPiece(piece);
            } else if (this.attackedPiece.equals(piece)) {
                if (this.attackedPiece.getPieceColour() == Colour.BLACK) {
                    Board.getInactiveBlackPieces().add(this.attackedPiece);
                } else if (this.attackedPiece.getPieceColour() == Colour.WHITE) {
                    Board.getInactiveWhitePieces().add(this.attackedPiece);
                } else {
                    throw new RuntimeException("Should not reach here");
                }
            }
        }

        Piece piece = this.movedPiece.moveIt(this);
        piece.setFirstMove(false);
        builder.putPiece(piece);
        builder.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        return builder.build();
    }

    Piece getAttackedPiece() {
        return attackedPiece;
    }
}