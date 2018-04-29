package cz.cvut.fel.pjv.engine.board;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.Pawn;
import cz.cvut.fel.pjv.engine.pieces.Piece;

public class BoardBuilder {

    Table<Integer, Integer, Piece> boardConfiguration;
    Colour nextMove;
    Pawn enPassant;
    Move transitionMove;

    public BoardBuilder(){
        this.boardConfiguration = HashBasedTable.create();
    }

    public BoardBuilder putPiece(final Piece piece){
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

    public void setEnPassant(Pawn enPassant) {
        this.enPassant = enPassant;
    }

    public BoardBuilder setMoveTransition(final Move transitionMove) {
        this.transitionMove = transitionMove;
        return this;
    }
//      builds a new board

    public Board build() {
        return new Board(this);
    }


}
