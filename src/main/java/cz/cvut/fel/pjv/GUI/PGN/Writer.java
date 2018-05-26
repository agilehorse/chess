package cz.cvut.fel.pjv.GUI.PGN;

import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.GUI.MoveLog;
import cz.cvut.fel.pjv.engine.board.moves.Move;

import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer {

    private final static Logger LOGGER = Logger.getLogger(Writer.class.getSimpleName());

    public static void writeGameToPGNFile(final File pgnFile, final MoveLog moveLog) {
        final StringBuilder builder = new StringBuilder();
//        writes a heading
        builder.append("[Chess game made by David Michal Bulko]" + "\n");
        Integer counter = 0;
        Integer round = 1;
        for(final Move move : moveLog.getMoves()) {
//            every odd iteration ordinal number defining round is appended
            if (counter % 2 == 0) {
                builder.append(round.toString()).append(".");
                round++;
            }
//            move PGN notation from move-log is appended
            builder.append(move.getPerformedToString()).append(" ");
            counter++;
        }
//        when done string is written into file
        try (final java.io.Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(pgnFile, true)))) {
            writer.write(builder.toString());
            LOGGER.log(Level.INFO, "File saved successfully.");
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Unexpected error while saving file!");
            JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                    "Unexpected error while saving file!", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
