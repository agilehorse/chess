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

import static cz.cvut.fel.pjv.engine.board.BoardUtils.STANDARD_OFFSETS;

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
        for (final int columnOffset : STANDARD_OFFSETS) {
//            if destination tile isn't out of board it is stored in tileInFront
            if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW,
                    columnOffset + this.pieceColumn)) {
//              gets en passant pawn if it was set in last move
                final Piece passingPawn = board.getEnPassantPawn();
                if (passingPawn != null
                        && passingPawn.getPieceColumn() == (this.pieceColumn + columnOffset)
                        && passingPawn.getPieceRow() == this.pieceRow
                        && this.getPieceColour() != passingPawn.getPieceColour()) {
//                    if all conditions for en passant attack are met it's added to the list of moves
                    legalMoves.add(new EnPassantAttack(board, this,
                            DESTINATION_ROW, this.pieceColumn +
                            columnOffset, passingPawn));
                }
                final Tile targetTile = Board.getTile(DESTINATION_ROW, columnOffset + this.pieceColumn);
                if (targetTile.isOccupied()
                        && targetTile.getPiece().getPieceColour() != this.getPieceColour()) {
//                   if the pawn has reached almost the end of the board and has the tiles diagonally filled with enemy pieces new attack promotion move is added
                    if (this.getPieceColour() == Colour.WHITE && DESTINATION_ROW == 0) {
                        legalMoves.add(new PawnPromotionAttack(board, this, DESTINATION_ROW, this.pieceColumn + columnOffset,
                                targetTile.getPiece(), new Bishop(DESTINATION_ROW, this.pieceColumn + columnOffset, Colour.WHITE)));
                        legalMoves.add(new PawnPromotionAttack(board, this, DESTINATION_ROW, this.pieceColumn + columnOffset,
                                targetTile.getPiece(), new Knight(DESTINATION_ROW, this.pieceColumn + columnOffset, Colour.WHITE)));
                        legalMoves.add(new PawnPromotionAttack(board, this, DESTINATION_ROW, this.pieceColumn + columnOffset,
                                targetTile.getPiece(), new Rook(DESTINATION_ROW, this.pieceColumn + columnOffset, Colour.WHITE)));
                        legalMoves.add(new PawnPromotionAttack(board, this, DESTINATION_ROW, this.pieceColumn + columnOffset,
                                targetTile.getPiece(), new Queen(DESTINATION_ROW, this.pieceColumn + columnOffset, Colour.WHITE)));
                    } else if (this.getPieceColour() == Colour.BLACK && DESTINATION_ROW == 7) {
                        legalMoves.add(new PawnPromotionAttack(board, this, DESTINATION_ROW, this.pieceColumn + columnOffset,
                                targetTile.getPiece(), new Bishop(DESTINATION_ROW, this.pieceColumn + columnOffset, Colour.BLACK)));
                        legalMoves.add(new PawnPromotionAttack(board, this, DESTINATION_ROW, this.pieceColumn + columnOffset,
                                targetTile.getPiece(), new Knight(DESTINATION_ROW, this.pieceColumn + columnOffset, Colour.BLACK)));
                        legalMoves.add(new PawnPromotionAttack(board, this, DESTINATION_ROW, this.pieceColumn + columnOffset,
                                targetTile.getPiece(), new Rook(DESTINATION_ROW, this.pieceColumn + columnOffset, Colour.BLACK)));
                        legalMoves.add(new PawnPromotionAttack(board, this, DESTINATION_ROW, this.pieceColumn + columnOffset,
                                targetTile.getPiece(), new Queen(DESTINATION_ROW, this.pieceColumn + columnOffset, Colour.BLACK)));
                    }
                    //                if the target tile is occupied with enemy piece new attack move is added
                    legalMoves.add(new AttackMove(board,
                            this,
                            DESTINATION_ROW,
                            this.pieceColumn + columnOffset,
                            targetTile.getPiece()));
                }
            }
        }
//      checks if target tile for normal pawn move is valid
        if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW,
                this.pieceColumn)) {
            final Tile tileInFront = Board.getTile(DESTINATION_ROW, this.pieceColumn);
            if (!tileInFront.isOccupied()) {
//                 if the pawn has reached almost the end, it creates all possible promotion moves
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

                }
                //          if the tile in front of pawn isn't occupied new move is added
                legalMoves.add(new NormalMove(board, this,
                        DESTINATION_ROW, this.pieceColumn));
//                if the pawn hasn't moved it can jump
                if (this.isFirstMove()) {
//                  checks if jump tile is on obard
                    if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW +
                                    this.getPieceColour().getDirection(),
                            this.pieceColumn)) {
                        final Tile jumpTile = Board.getTile(DESTINATION_ROW +
                                this.getPieceColour().getDirection(), this.pieceColumn);
//                        if the tile isn't occupied new move is added
                        if (!jumpTile.isOccupied()) {
                            legalMoves.add(new NormalMove(board,
                                    this,
                                    DESTINATION_ROW +
                                            this.getPieceColour().getDirection(),
                                    this.pieceColumn));
                        }
                    }
                }
            }
        }
//      pawn can move two squares only once in a game, if it hasn't done it, both square aren't occupied it can use this ability
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
