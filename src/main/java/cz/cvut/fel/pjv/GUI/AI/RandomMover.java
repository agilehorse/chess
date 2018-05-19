package cz.cvut.fel.pjv.GUI.AI;

import cz.cvut.fel.pjv.GUI.Clock;
import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomMover {
    public Move get(final Board board) {
        final ArrayList<Move> moves = new ArrayList<>(board.getCurrentPlayer().getLegalMoves());
        ArrayList<Move> legalMoves = new ArrayList<>();
        for (final Move move : moves) {
            if (move.freeFromCheck()) {
                legalMoves.add(move);
            }
        }
        if (legalMoves.size() == 0) {
            if (board.getCurrentPlayer().isInCheckMate()) {
                Clock.terminate();
                JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                        "Game Over: Player " + board.getCurrentPlayer().toString() + " is in checkmate!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            if (board.getCurrentPlayer().isInStaleMate()) {
                Clock.terminate();
                JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                        "Game Over: Player " + board.getCurrentPlayer().toString() + " is in stalemate!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (board.getWhitePieces().size() == 1 && board.getBlackPieces().size() == 1) {
            ArrayList<Piece> white = new ArrayList<>(board.getWhitePieces());
            ArrayList<Piece> black = new ArrayList<>(board.getBlackPieces());
            Piece whiteKing = white.get(0);
            Piece blackKing = black.get(0);
            if (whiteKing.getPieceType().equals(PieceType.KING) && blackKing.getPieceType().equals(PieceType.KING)) {
                JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                        "Game Over: AI can't give up so this would go forever", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
                MainPanel.exit();
            }
        }
        Random rand = new Random();
        int x = rand.nextInt(legalMoves.size());
        return legalMoves.get(x);
    }
}
