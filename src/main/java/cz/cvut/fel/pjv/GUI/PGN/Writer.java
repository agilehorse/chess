package cz.cvut.fel.pjv.GUI.PGN;

import cz.cvut.fel.pjv.GUI.MoveLog;
import cz.cvut.fel.pjv.engine.board.moves.Move;

import java.io.*;

public class Writer {
    public static void writeGameToPGNFile(File pgnFile, MoveLog moveLog) throws IOException {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Chess game made by David Michal Bulko]" + "\n");
        Integer counter = 0;
        Integer round = 1;
        for(final Move move : moveLog.getMoves()) {
            if (counter % 2 == 0) {
                builder.append(round.toString() + ". ");
                round++;
            }
            builder.append(move.getPerformedToString()).append(" ");
            counter++;
        }
        try (final java.io.Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pgnFile, true)))) {
            writer.write(builder.toString());
        }
    }
}
