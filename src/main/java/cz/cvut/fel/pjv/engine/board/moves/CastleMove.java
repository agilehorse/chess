package cz.cvut.fel.pjv.engine.board.moves;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.pieces.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CastleMove extends Move {

    private MoveType moveType;
    private Rook castlingRook;
    private int castlingRookOldRow;
    private int castlingRookOldColumn;
    private int castlingRookNewRow;
    private int castlingRookNewColumn;
    private final static Logger LOGGER = Logger.getLogger(CastleMove.class.getName());


    CastleMove(final Board board,
               final King castlingKing,
               final int castlingKingNewRow,
               final int castlingKingNewColumn,
               final Rook castlingRook,
               final int castlingRookOldRow,
               final int castlingRookOldColumn,
               final int castlingRookNewRow,
               final int castlingRookNewColumn) {
        super(board, castlingKing, castlingKingNewRow, castlingKingNewColumn);
        this.castlingRook = castlingRook;
        this.castlingRookOldRow = castlingRookOldRow;
        this.castlingRookOldColumn = castlingRookOldColumn;
        this.castlingRookNewRow = castlingRookNewRow;
        this.castlingRookNewColumn = castlingRookNewColumn;
        this.moveType = MoveType.CASTLE;
    }
//  method for execution of a legal castle move
    @Override
    public void execute() {
        //        sets PGN string format

        if (this.getPerformedToString().equals("")) {
            this.setPerformedToString(this.toString());
        }
//      moves king
        this.getSourceTile().setPieceOnTile(null);
        this.getDestinationTile().setPieceOnTile(this.getMovedPiece());
        this.movedPiece.move(this.getNewRow(), this.getNewColumn());
        this.movedPiece.setFirstMove(false);

//      moves rook
        Board.getTile(castlingRook.getPieceRow(),
                castlingRook.getPieceColumn()).setPieceOnTile(null);
        Board.getTile(castlingRookNewRow,
                castlingRookNewColumn).setPieceOnTile(castlingRook);
        this.castlingRook.move(castlingRookNewRow, castlingRookNewColumn);
        this.castlingRook.setFirstMove(false);
        //noinspection AccessStaticViaInstance
        this.board.setMove(this.board.getCurrentPlayer().getOpponent().getColour());
        //        switches turn to opponent
        board.setEnPassantPawn(null);
        LOGGER.log(Level.INFO, this.castlingRook.getPieceColour() + " castle move was made.");
    }

    private Rook getCastlingRook() {
        return castlingRook;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CastleMove)) return false;
        if (!super.equals(o)) return false;
        CastleMove that = (CastleMove) o;
        return castlingRookOldRow == that.castlingRookOldRow &&
                castlingRookOldColumn == that.castlingRookOldColumn &&
                castlingRookNewRow == that.castlingRookNewRow &&
                castlingRookNewColumn == that.castlingRookNewColumn &&
                getMoveType() == that.getMoveType() &&
                Objects.equals(getCastlingRook(), that.getCastlingRook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMoveType(), getCastlingRook(),
                castlingRookOldRow, castlingRookOldColumn, castlingRookNewRow,
                castlingRookNewColumn);
    }

    @Override
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public boolean freeFromCheck() {
        return true;
    }
}
