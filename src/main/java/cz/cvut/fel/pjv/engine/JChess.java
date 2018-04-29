package cz.cvut.fel.pjv.engine;

import cz.cvut.fel.pjv.engine.board.Board;

public class JChess {
    public static void main(String[] args){
        Board board = Board.createStandardBoard();
        System.out.println(board);
    }
}
