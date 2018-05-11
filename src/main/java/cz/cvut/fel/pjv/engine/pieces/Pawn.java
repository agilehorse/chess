package cz.cvut.fel.pjv.engine.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.moves.*;
import cz.cvut.fel.pjv.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.engine.board.BoardUtils.OFFSETS;

public class Pawn extends Piece {


    public Pawn(
            int pieceRow,
            int pieceColumn,
            Colour pieceColour) {

        super(PieceType.PAWN, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        int DESTINATION_ROW = this.pieceRow + this.getPieceColour().getDirection();
        final List<Move> legalMoves = new ArrayList<>();
//      calculating attack moves, pawn attacks diagonally, therefore there is column offset
        for (final int columnOffset : OFFSETS) {
//            if destination tile isn't out of board it is stored in tileInFront
            if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW,
                    columnOffset + this.pieceColumn)) {
                final Tile targetTile = board.getTile(DESTINATION_ROW, columnOffset + this.pieceColumn);
//              attack move is added into the list of legal moves if the destination is a valid tile, which is occupied by a piece of different colour
                final Piece passingPawn = board.getEnPassantPawn();
                if (targetTile.isOccupied()
                        && targetTile.getPiece().getPieceColour() != this.getPieceColour()) {
                    legalMoves.add(new AttackMove(board,
                            this,
                            DESTINATION_ROW,
                            this.pieceColumn + columnOffset,
                            targetTile.getPiece()));
                } else if (passingPawn != null
                        && passingPawn.getPieceColumn() == (this.pieceColumn + columnOffset)
                        && passingPawn.getPieceRow() == this.pieceRow
                        && this.getPieceColour() != passingPawn.getPieceColour()) {
                    legalMoves.add(new EnPassantAttack(board, this,
                            DESTINATION_ROW, this.pieceColumn + columnOffset, passingPawn));
                }
            }
        }
//      normal move is added to the list of moves if the target tile isn't out of board and isn't occupied
        if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW,
                this.pieceColumn)) {
            final Tile tileInFront = board.getTile(DESTINATION_ROW, this.pieceColumn);
            if (!tileInFront.isOccupied()) {
                if (this.getPieceColour() == Colour.WHITE && DESTINATION_ROW == 0) {
                    legalMoves.add(new PawnPromotionMove(board, this, DESTINATION_ROW, this.pieceColumn,
                            new Bishop(DESTINATION_ROW, this.pieceColumn, Colour.WHITE)));
                    legalMoves.add(new PawnPromotionMove(board, this, DESTINATION_ROW, this.pieceColumn,
                            new Knight(DESTINATION_ROW, this.pieceColumn, Colour.WHITE)));
                    legalMoves.add(new PawnPromotionMove(board, this, DESTINATION_ROW, this.pieceColumn,
                            new Rook(DESTINATION_ROW, this.pieceColumn, Colour.WHITE)));
                    legalMoves.add(new PawnPromotionMove(board, this, DESTINATION_ROW, this.pieceColumn,
                            new Queen(DESTINATION_ROW, this.pieceColumn, Colour.WHITE)));
                } else if (this.getPieceColour() == Colour.BLACK && DESTINATION_ROW == 7) {
                    legalMoves.add(new PawnPromotionMove(board, this, DESTINATION_ROW, this.pieceColumn,
                            new Bishop(DESTINATION_ROW, this.pieceColumn, Colour.BLACK)));
                    legalMoves.add(new PawnPromotionMove(board, this, DESTINATION_ROW, this.pieceColumn,
                            new Knight(DESTINATION_ROW, this.pieceColumn, Colour.BLACK)));
                    legalMoves.add(new PawnPromotionMove(board, this, DESTINATION_ROW, this.pieceColumn,
                            new Rook(DESTINATION_ROW, this.pieceColumn, Colour.BLACK)));
                    legalMoves.add(new PawnPromotionMove(board, this, DESTINATION_ROW, this.pieceColumn,
                            new Queen(DESTINATION_ROW, this.pieceColumn, Colour.BLACK)));
                } else {
                    legalMoves.add(new NormalMove(board, this,
                            DESTINATION_ROW, this.pieceColumn));
                    if (this.isFirstMove()) {
                        if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW + this.getPieceColour().getDirection(),
                                this.pieceColumn)) {
                            final Tile jumpTile = board.getTile(DESTINATION_ROW + this.getPieceColour().getDirection(), this.pieceColumn);
                            if (!jumpTile.isOccupied()) {
                                legalMoves.add(new NormalMove(board,
                                        this,
                                        DESTINATION_ROW + this.getPieceColour().getDirection(),
                                        this.pieceColumn));
                            }
                        }
                    }
                }
            }
        }
//      pawn can move two squares only once in a game, if it hasn't done it, both square aren't occupied it can use this ability
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Piece returnImposter(int row, int column) {
        return new Pawn(row, column, this.getPieceColour());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
