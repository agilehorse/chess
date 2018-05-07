package cz.cvut.fel.pjv.engine;

import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.engine.board.Board;

public class JChess {
    public static void main(String[] args){
        Board board = new Board();
        System.out.println(board);
        System.out.println(board.toAlgebraicString());
        MainPanel mainPanel = new MainPanel();
    }
}
