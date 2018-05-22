package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.King;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

import java.util.Objects;

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
        if (this.movedPiece.getPieceType() == PieceType.PAWN) {
            return BoardUtils.getPositionAtCoordinate(this.getNewRow(), this.getNewColumn());
        } else {
            return movedPiece.getPieceType().toString() + disambiguationTile() +
                    BoardUtils.getPositionAtCoordinate(this.getNewRow(), this.getNewColumn());
        }
    }

    public void execute() {
        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
        final Tile sourceTile = this.getSourceTile();
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        sourceTile.setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        this.movedPiece.setFirstMove(false);
        //noinspection AccessStaticViaInstance
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        board.setEnPassantPawn(null);

    }

    @Override
    public boolean freeFromCheck() {
        boolean invalid = false;
        final Tile sourceTile = this.getSourceTile();
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        sourceTile.setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.movedPiece);
        final King king = this.board.getCurrentPlayer().getPlayersKing();
        final Tile kingTile = this.board.getTile(king.getPieceRow(), king.getPieceColumn());
        for (final Move move : this.board.getMovesByColour(this.board.getCurrentPlayer().getOpponent().getColour())) {
            if (move.getDestinationTile() == kingTile) {
                invalid = true;
                break;
            }
        }
        this.movedPiece.move(sourceTile.getTileRow(), sourceTile.getTileColumn());
        sourceTile.setPieceOnTile(movedPiece);
        this.getDestinationTile().setPieceOnTile(null);
        this.board.recalculate(false);
        return !invalid;
    }

    @Override
    public boolean equals(Object o) {
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
