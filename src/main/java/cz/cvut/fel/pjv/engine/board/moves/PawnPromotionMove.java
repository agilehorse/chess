package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;

import java.util.Objects;

public class PawnPromotionMove extends NormalMove {

    final Board board;
    private final Pawn promotedPawn;
    private final int newRow;
    private final int newColumn;
    private Piece newPiece;
    private MoveType moveType;

    public PawnPromotionMove(final Board board,
                             final Pawn promotedPawn,
                             final int newRow,
                             final int newColumn,
                             final Piece newPiece) {
        super(board, promotedPawn, newRow, newColumn);
        this.board = board;
        this.promotedPawn = promotedPawn;
        this.newRow = newRow;
        this.newColumn = newColumn;
        this.moveType = MoveType.PROMOTION;
        this.newPiece = newPiece;
    }

    @Override
    public Piece getMovedPiece() {
        return this.newPiece;
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPieceRow(),
                this.movedPiece.getPieceColumn()) + "-" +
                BoardUtils.getPositionAtCoordinate(this.newRow, this.newColumn)
                + "=" + this.newPiece.getPieceType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PawnPromotionMove)) return false;
        if (!super.equals(o)) return false;
        PawnPromotionMove that = (PawnPromotionMove) o;
        return getNewRow() == that.getNewRow() &&
                getNewColumn() == that.getNewColumn() &&
                Objects.equals(getBoard(), that.getBoard()) &&
                Objects.equals(promotedPawn, that.promotedPawn) &&
                Objects.equals(newPiece, that.newPiece) &&
                getMoveType() == that.getMoveType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBoard(), promotedPawn, getNewRow(), getNewColumn(), newPiece, getMoveType());
    }
}
