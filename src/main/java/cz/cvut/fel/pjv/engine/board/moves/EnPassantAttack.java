package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Piece;

public class EnPassantAttack extends AttackMove {
    public EnPassantAttack(final Board board,
                           final Piece movedPiece,
                           final int newRow,
                           final int newColumn,
                           final Piece attackedPiece) {
        super(board, movedPiece, newRow, newColumn, attackedPiece);
    }

    @Override
    public void execute() {
        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
        final Tile attackedTile = this.board.getTile(this.getAttackedPiece().getPieceRow(), this.getAttackedPiece().getPieceColumn());
        this.getAttackedPiece().setActive(false);
        attackedTile.setPieceOnTile(null);
        this.movedPiece.move(getNewRow(), getNewColumn());
        this.getDestinationTile().setPieceOnTile(movedPiece);
        this.getSourceTile().setPieceOnTile(null);
        movedPiece.setFirstMove(false);
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        this.board.setEnPassantPawn(null);
    }

    @Override
    public boolean freeFromCheck() {
        boolean invalid = false;
        final Tile attackedTile = this.getDestinationTile();
        final King king = this.board.getCurrentPlayer().getPlayersKing();
        final Tile kingTile = this.board.getTile(king.getPieceRow(), king.getPieceColumn());
        this.getAttackedPiece().move(-1, -1);
        this.getAttackedPiece().setActive(false);
        attackedTile.setPieceOnTile(null);
        this.movedPiece.move(getNewRow(), getNewColumn());
        this.getDestinationTile().setPieceOnTile(movedPiece);
        this.getSourceTile().setPieceOnTile(null);
        this.board.recalculate(false);
        for (final Move move : this.board.getMoves(this.board.getCurrentPlayer().getOpponent().getColour())) {
            if (move.getDestinationTile() == kingTile) {
                invalid = true;
                break;
            }
        }
        getAttackedPiece().move(attackedTile.getTileRow(), attackedTile.getTileColumn());
        getAttackedPiece().setActive(true);
        attackedTile.setPieceOnTile(getMovedPiece());
        getDestinationTile().setPieceOnTile(null);
        movedPiece.move(this.getSourceTile().getTileRow(), this.getSourceTile().getTileColumn());
        this.getSourceTile().setPieceOnTile(movedPiece);
        this.board.recalculate(false);
        return !invalid;
    }
}
