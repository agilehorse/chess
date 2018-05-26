package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Piece;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EnPassantAttack extends AttackMove {

    private final static Logger LOGGER = Logger.getLogger(EnPassantAttack.class.getName());

    public EnPassantAttack(final Board board,
                           final Piece movedPiece,
                           final int newRow,
                           final int newColumn,
                           final Piece attackedPiece) {
        super(board, movedPiece, newRow, newColumn, attackedPiece);
    }
//  method for execution of a legal en passant attack move
    @Override
    public void execute() {
        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
//        moves piece
        this.getDestinationTile().setPieceOnTile(movedPiece);
        this.getSourceTile().setPieceOnTile(null);
        Board.getTile(this.getAttackedPiece().getPieceRow(),
                this.getAttackedPiece().getPieceColumn()).setPieceOnTile(null);
        this.movedPiece.move(getNewRow(), getNewColumn());
        //noinspection AccessStaticViaInstance
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        //        switches turn to opponent
        this.board.setEnPassantPawn(null);
        LOGGER.log(Level.INFO, "En passant attack move was made to tile: " +
                getDestinationTile().toString());
    }
//  method for validating if the move will leave current player in check
    @Override
    public boolean freeFromCheck() {
        boolean invalid = false;
        final Tile attackedTile = Board.getTile(
                this.getAttackedPiece().getPieceRow(),
                this.getAttackedPiece().getPieceColumn());
        final King king = this.board.getCurrentPlayer().getPlayersKing();
        final Tile kingTile = Board.getTile(king.getPieceRow(),
                king.getPieceColumn());
        this.movedPiece.move(getNewRow(), getNewColumn());
        this.getDestinationTile().setPieceOnTile(movedPiece);
        this.getSourceTile().setPieceOnTile(null);
        attackedTile.setPieceOnTile(null);
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
        getDestinationTile().setPieceOnTile(null);
        attackedTile.setPieceOnTile(getAttackedPiece());
        return !invalid;
    }
}
