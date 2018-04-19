package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.board.Board;

public class JChess {
    public static void main(String[] args){
        Board board = Board.createStandardBoard();

        System.out.println(board);
    }
}
