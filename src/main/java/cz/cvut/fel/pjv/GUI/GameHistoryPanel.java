package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;

import javax.swing.*;
import java.awt.*;

public class GameHistoryPanel extends JPanel {

    private static final Dimension HISTORY_PANEL_DIMENSION
            = new Dimension(140, 400);
    private final DataModel model;
    private final JScrollPane jScrollPane;
    private Clock clock;

    GameHistoryPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        clock = new Clock();
        final JTable table = new JTable(this.model);
        table.setRowHeight(15);
        this.jScrollPane = new JScrollPane(table);
        jScrollPane.setColumnHeaderView(table.getTableHeader());
        jScrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(jScrollPane, BorderLayout.CENTER);
        if (ClockSetup.getMode() != 0) setTimerVisible(true);
        this.setVisible(true);
    }

    public void redo(final Board board,
                     final MoveLog moveHistory) {
//       resets the table
        int currentRow = 0;
        this.model.clear();
//        ads all done moves back to table
        for (final Move move : moveHistory.getMoves()) {
//             gets PGN format of move
            final String moveText = move.getPerformedToString();
//            ads value to its column by colour
            if (move.getMovedPiece().getPieceColour().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 0);
            } else if (move.getMovedPiece().getPieceColour().isBlack()) {
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
//        modifies last move
        if (moveHistory.getMoves().size() > 0) {
            final Move lastMove = moveHistory.getMoves().get(moveHistory.size() - 1);
//            ads notation of checkmate to the move notation if move leads to the checkmate
            if (!lastMove.getPerformedToString().endsWith("#")) {
                if (board.getCurrentPlayer().isInCheckMate()) {
                    lastMove.setPerformedToString("#");
//                ads notation of check to the move notation if move leads to the check
                } else if (board.getCurrentPlayer().isInCheck()) {
                    lastMove.setPerformedToString("+");
                }
            }
            final String moveText = lastMove.getPerformedToString();
            if (lastMove.getMovedPiece().getPieceColour().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 0);
            } else if (lastMove.getMovedPiece().getPieceColour().isBlack()) {
                this.model.setValueAt(moveText, currentRow - 1, 1);
            }
        }
        final JScrollBar vertical = jScrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    void setTimerVisible(final boolean bool) {
        if (bool) {
            this.add(clock, BorderLayout.SOUTH);
        } else {
            this.remove(clock);
        }
        validate();
        repaint();
    }
}
