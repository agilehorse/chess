package cz.cvut.fel.pjv.board.tiles;

import cz.cvut.fel.pjv.pieces.Piece;

public class OccupiedTile extends Tile {

    private final Piece pieceOnTile;

    OccupiedTile(int tileRow,
                 int tileColumn,
                 final Piece pieceOnTile) {
        super(tileRow, tileColumn);
        this.pieceOnTile = pieceOnTile;
    }

    @Override
    public boolean isOccupied() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return this.pieceOnTile;
    }
//  if the piece is black returns string representation
//  of it in lowercase, else returns default which is uppercase
    @Override
    public String toString() {
        return getPiece().getPieceColour().isBlack()
                ? getPiece().toString().toLowerCase() : getPiece().toString();
    }
}
