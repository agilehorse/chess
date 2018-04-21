package cz.cvut.fel.pjv.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.pieces.Piece;

public class Builder {

    Table<Integer, Integer, Piece> boardConfiguration;
    private Colour nextMove;

    Builder(){
        this.boardConfiguration = HashBasedTable.create();
    }

    Builder putPiece(final Piece piece){
        this.boardConfiguration.put(piece.getPieceRow(),
                                    piece.getPieceColumn(),
                                    piece);
        return this;
    }
    //      sets next move a of a player
    public Builder setMove(final Colour colour) {
        this.nextMove = colour;
        return this;
    }
//      builds a new board

    Board build() {
        return new Board(this);
    }
}
