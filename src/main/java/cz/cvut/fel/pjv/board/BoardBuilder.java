package cz.cvut.fel.pjv.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import cz.cvut.fel.pjv.Colour;
import cz.cvut.fel.pjv.pieces.Piece;

public class BoardBuilder {

    Table<Integer, Integer, Piece> boardConfiguration;
    private Colour nextMove;

    BoardBuilder(){
        this.boardConfiguration = HashBasedTable.create();
    }

    BoardBuilder putPiece(final Piece piece){
        this.boardConfiguration.put(piece.getPieceRow(),
                                    piece.getPieceColumn(),
                                    piece);
        return this;
    }
    //      sets next move a of a player
    public BoardBuilder setMove(final Colour colour) {
        this.nextMove = colour;
        return this;
    }
//      builds a new board

    Board build() {
        return new Board(this);
    }
}
