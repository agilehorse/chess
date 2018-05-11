package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

//  defines new basic new, on empty tile
public class NormalMove extends Move {

    private MoveType moveType;

    public NormalMove(final Board board,
                      final Piece movedPiece,
                      final int newRow,
                      final int newColumn) {
        super(board, movedPiece, newRow, newColumn);
        this.moveType = MoveType.NORMAL;
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public String toString() {
        String pieceString = "";
        if (movedPiece.getPieceType() != PieceType.PAWN && movedPiece.getPieceColour() == Colour.WHITE) {
            pieceString = movedPiece.getPieceType().toString().toUpperCase() + sourceTileString();
        } else if (movedPiece.getPieceType() != PieceType.PAWN && movedPiece.getPieceColour() == Colour.BLACK) {
            pieceString = movedPiece.getPieceType() + sourceTileString();
        }
        return pieceString +
                BoardUtils.getPositionAtCoordinate(this.getNewRow(), this.getNewColumn());
    }

    public void execute() {
        if (validateForCheck()) {
            final Tile oldTile = this.board.getTile(movedPiece.getPieceRow(), movedPiece.getPieceColumn());
            this.movedPiece.move(this.getNewRow(), this.getNewColumn());
            this.getSourceTile().setPieceOnTile(null);
            this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
            setExecuted(true);
            if (movedPiece.getPieceType() == PieceType.PAWN && oldTile.getTileRow() + 2 == getNewRow()) {
                this.board.setEnPassantPawn((Pawn) movedPiece);
            }
            this.movedPiece.setFirstMove(false);
            this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
            this.board.recalculate(true);
        }
    }

    @Override
    public boolean validateForCheck() {
        boolean invalid = false;
        final Tile oldTile = this.board.getTile(movedPiece.getPieceRow(), movedPiece.getPieceColumn());
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        final King king = this.board.getCurrentPlayer().getPlayersKing();
        final Tile kingTile = this.board.getTile(king.getPieceRow(), king.getPieceColumn());
        this.board.recalculate(false);
        for (final Move move : this.board.getCurrentPlayer().getOpponent().getLegalMoves()) {
            if (move.getDestinationTile() == kingTile) {
                invalid = true;
                break;
            }
        }
        this.movedPiece.move(oldTile.getTileRow(), oldTile.getTileColumn());
        this.getSourceTile().setPieceOnTile(movedPiece);
        this.getDestinationTile().setPieceOnTile(null);
        this.board.recalculate(false);
        return !invalid;
    }
}
