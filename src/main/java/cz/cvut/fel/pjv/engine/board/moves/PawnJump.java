package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardBuilder;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.tiles.Tile;
import cz.cvut.fel.pjv.engine.pieces.Piece;

public class PawnJump extends NormalMove {

    public PawnJump(Board board, Piece movedPiece, int newRow, int newColumn) {
        super(board, movedPiece, newRow, newColumn);
    }

    public Board execute() {
        final BoardBuilder builder = new BoardBuilder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.putPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.putPiece(piece);
        }
        if (BoardUtils.isValidTileCoordinate(getNewRow(), getNewColumn()-1)) {
            final Tile attackerTile = board.getTile(getNewRow(), getNewColumn() - 1);
            if (attackerTile.isOccupied()) {
                final Piece attackerPiece = attackerTile.getPiece();
                
            }
        }
        Piece piece = this.movedPiece.moveIt(this);
        piece.setFirstMove(false);
        builder.putPiece(piece);
        builder.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        return builder.build();
    }
}
