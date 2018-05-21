package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.board.moves.Move;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class DataModel extends DefaultTableModel {
    private final List<Row> values;
    private final static String[] NAMES = {"White", "Black"};

    DataModel() {
        this.values = new ArrayList<>();
    }

    void clear() {
        this.values.clear();
        setRowCount(0);
    }

    @Override
    public int getRowCount() {
        //noinspection ConstantConditions
        if (this.values == null) {
            return 0;
        }
        return this.values.size();
    }

    @Override
    public int getColumnCount() {
        return NAMES.length;
    }

    @Override
    public Object getValueAt(final int row, final int column) {
        final Row currentRow = this.values.get(row);
        if (column == 0){
            return currentRow.getWhiteMove();
        } else if (column == 1) {
            return currentRow.getBlackMove();
        }
        return null;
    }

    @Override
    public void setValueAt(final Object aValue,
                           final int row,
                           final int column) {
        final Row currentRow;
        if (this.values.size() <= row){
            currentRow = new Row();
            this.values.add(currentRow);
        } else {
            currentRow = this.values.get(row);
        }
        if (column == 0) {
            currentRow.setWhiteMove((String) aValue);
            fireTableRowsInserted(row, row);
        } else if (column == 1) {
            currentRow.setBlackMove((String) aValue);
            fireTableCellUpdated(row, column);
        }
    }

    @Override
    public Class<?> getColumnClass(final int column) {
        return Move.class;
    }

    @Override
    public String getColumnName(final int column) {
        return NAMES[column];
    }

    private static class Row {

        private String whiteMove;
        private String blackMove;

        Row() {
        }

        String getWhiteMove() {
            return whiteMove;
        }

        String getBlackMove() {
            return blackMove;
        }

        void setWhiteMove(String whiteMove) {
            this.whiteMove = whiteMove;
        }

        void setBlackMove(String blackMove) {
            this.blackMove = blackMove;
        }
    }
}
