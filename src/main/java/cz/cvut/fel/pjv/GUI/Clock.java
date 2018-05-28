package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.GUI.PGN.Writer;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clock extends JPanel implements Runnable {

    private static Thread thread;
    @SuppressWarnings("FieldCanBeLocal")
    private JPanel whiteTimer, blackTimer;
    private static JLabel whiteLabel, blackLabel;
    private static int whiteTime;
    private static int blackTime;
    private static boolean started;
    private static boolean stopped;
    private static int[] userInputTimes;
    private static final Dimension CLOCK_PANEL_DIMENSION = new Dimension(40, 50);
    private final static Logger LOGGER = Logger.getLogger(Clock.class.getSimpleName());


    Clock() {
//         new thread made
        thread = new Thread(this);
        this.setLayout(new BorderLayout());
//        sets up white timer
        this.whiteTimer = new JPanel();
        whiteLabel = new JLabel();
        this.whiteTimer.add(whiteLabel);
        whiteLabel.setText(whiteTime / 600 + ":" +
                whiteTime % 600 + ":" + (whiteTime % 600) % 10);
//        sets up black timer
        this.blackTimer = new JPanel();
        blackLabel = new JLabel();
        this.blackTimer.add(blackLabel);
        blackLabel.setText(blackTime / 600 + ":" +
                blackTime % 600 + ":" + (blackTime % 600) % 10);
        userInputTimes = ClockSetup.getTimes();
        this.add(whiteTimer, BorderLayout.SOUTH);
        this.add(blackTimer, BorderLayout.NORTH);
        this.setPreferredSize(CLOCK_PANEL_DIMENSION);
        this.setVisible(true);
    }
// called on mode change
    static void modeChanged() {
        userInputTimes = ClockSetup.getTimes();
//        if the mode is classic, timer are set to what user has inputted
        if (ClockSetup.getMode() == 1) {
            assert userInputTimes != null;
            whiteTime = userInputTimes[0] * 600;
            whiteLabel.setText(whiteTime / 600 + ":" +
                    whiteTime % 600 + ":" + (whiteTime % 600) % 10);
            blackTime = userInputTimes[1] * 600;
            blackLabel.setText(blackTime / 600 + ":" +
                    blackTime % 600 + ":" + (blackTime % 600) % 10);
            started = false;
            start();
//       otherwise everything is reset
        } else if (ClockSetup.getMode() == 2) {
            started = false;
            resetTimer();
            start();
        } else {
            stop();
            resetTimer();
//            if there is no clock mode, the clock will become invisible
            MainPanel.getGameHistoryPanel().setTimerVisible(false);
        }
    }

//    wakes up clock if stopped
    static void wakeUp() {
        stopped = false;
    }
//   method for starting clock
    static void start() {
        if (!started) {
            started = true;
            stopped = false;
            if (thread.getState().equals(Thread.State.NEW)) {
                thread.start();
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            System.out.print("");
            //noinspection CatchMayIgnoreException
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            if (!stopped) {
                if (MainPanel.getBoard().getCurrentPlayer().getColour().isWhite()) {
//                    adds time to white clock
                    if (userInputTimes != null) {
                        whiteTime--;
                    } else {
                        whiteTime++;
                    }
                    setText(whiteLabel, blackLabel, true);
                    if (userInputTimes != null && whiteTime == 0) {
                        JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                                "White player's time is up! Black player wins!", "Game over",
                                JOptionPane.INFORMATION_MESSAGE);
                        stop();
                    }
                } else {
//                    ads time to black clock
                    if (userInputTimes != null) {
                        blackTime--;
                    } else {
                        blackTime++;
                    }
                    if (userInputTimes != null && blackTime == 0) {
                        JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                                "Black player's time is up! White player wins", "Game over",
                                JOptionPane.INFORMATION_MESSAGE);
                        stop();
                    }
                    setText(whiteLabel, blackLabel, false);
                }
            }
        }
    }

    private void setText(JLabel white, JLabel black, boolean isWhite) {
//        changes colour of current active time to red
        if (isWhite) {
            white.setForeground(Color.RED);
            black.setForeground(Color.BLACK);
        } else {
            white.setForeground(Color.BLACK);
            black.setForeground(Color.RED);
        }
        white.setText(whiteTime / 600 + ":" + (whiteTime % 600) / 10 +
                ":" + (whiteTime % 600) % 10);
        black.setText(blackTime / 600 + ":" + (blackTime % 600) / 10 +
                ":" + (blackTime % 600) % 10);
    }

    static void resetTimer() {
        whiteTime = 0;
        blackTime = 0;
        LOGGER.log(Level.INFO, "Clock reset.");
    }

    public static void stop() {
        stopped = true;
        LOGGER.log(Level.INFO, "Clock stopped.");
    }

    int getWhiteTime() {
        return whiteTime;
    }
}
