package cz.cvut.fel.pjv.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.moves.AttackMove;
import cz.cvut.fel.pjv.board.moves.Move;
import cz.cvut.fel.pjv.board.moves.NormalMove;
import cz.cvut.fel.pjv.board.tiles.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.board.BoardUtils.OFFSETS;

public class Pawn extends Piece {

    private int DESTINATION_ROW = this.pieceRow + this.pieceColour.getDirection();

    public Pawn(
                int pieceRow,
                int pieceColumn,
                Colour pieceColour) {

        super(PieceType.PAWN, pieceRow, pieceColumn, pieceColour);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
//      calculating attack moves, pawn attacks diagonally, therefore there is column offset
        for (final int columnOffset : OFFSETS) {
//            if destination tile isn't out of board it is stored in tileInFront
            if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW,
                    columnOffset + this.pieceColumn)) {
                final Tile targetTile = board.getTile(DESTINATION_ROW, columnOffset + this.pieceColumn);
//              attack move is added into the list of legal moves if the destination is a valid tile, which is occupied by a piece of different colour
                if (targetTile.isOccupied()
                        && targetTile.getPiece().getPieceColour() != this.pieceColour) {
                    legalMoves.add(new AttackMove(board,
                            this,
                            DESTINATION_ROW,
                            this.pieceColumn + columnOffset,
                            targetTile.getPiece()));
                }
            }
        }
//      normal move is added to the list of moves if the target tile isn't out of board and isn't occupied
        if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW,
                                             this.pieceColumn)) {
            final Tile tileInFront = board.getTile(DESTINATION_ROW, this.pieceColumn);
            if (!tileInFront.isOccupied()) {
                legalMoves.add(new NormalMove(board, this,
                        DESTINATION_ROW, this.pieceColumn));
                if (this.isFirstMove) {
                    if (BoardUtils.isValidTileCoordinate(DESTINATION_ROW + this.pieceColour.getDirection(),
                                                              this.pieceColumn)) {
                        final Tile jumpTile = board.getTile(DESTINATION_ROW + this.pieceColour.getDirection(), this.pieceColumn);
                        if (!jumpTile.isOccupied()) {
                            legalMoves.add(new NormalMove(board,
                                                this,
                                                   DESTINATION_ROW + this.pieceColour.getDirection(),
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
    public Piece moveIt(Move move) {
        return new Pawn(move.getNewRow(), move.getNewColumn(), move.getMovedPiece().getPieceColour());

    }

    private Tile validateTile(Board board,
                              final int row,
                              final int column) {
        if (BoardUtils.isValidTileCoordinate(row, column)) {
            return board.getTile(row, column);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
