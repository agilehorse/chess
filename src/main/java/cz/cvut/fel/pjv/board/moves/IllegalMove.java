package cz.cvut.fel.pjv.board.moves;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.pieces.Piece;

public class IllegalMove extends Move {

    public IllegalMove() {
        super(MoveType.ILLEGAL, null, null, -1, -1);
    }

    @Override
    public Board execute() {
        throw new RuntimeException("Move is not legal!");
    }
}
