package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;

public class PawnPromotionAttack extends AttackMove{

    final Board board;
    private final int newRow;
    private final int newColumn;
    private Piece newPiece;
    private MoveType moveType;
    private final Piece attackedPiece;

    public PawnPromotionAttack(final Board board,
                             final Pawn promotedPawn,
                             final int newRow,
                             final int newColumn,
                             final Piece attackedPiece,
                             final Piece newPiece) {
        super(board, promotedPawn, newRow, newColumn, attackedPiece);
        this.board = board;
        this.newRow = newRow;
        this.attackedPiece = attackedPiece;
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
// tile notation for pawn promotion attack move is pawn's column, x, destination tile,
// equal sign and initial character of promoted piece piece type
    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPieceRow(),
                this.movedPiece.getPieceColumn()) + "x" +
                BoardUtils.getPositionAtCoordinate(this.newRow, this.newColumn)
                + "=" + this.newPiece.getPieceType();
    }

    @Override
    public Piece getAttackedPiece() {
        return attackedPiece;
    }
}
