package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.Colour;

import javax.swing.*;
import java.awt.*;

public class Clock extends JPanel implements Runnable {

    private static Thread thread;
    private static final Dimension CLOCK_PANEL_DIMENSION = new Dimension(50, 30);
    private JPanel whiteTimer, blackTimer;
    private JLabel whiteLabel, blackLabel;
    private static int whiteTime;
    private static int blackTime;
    private static boolean started;
    private static Colour turn;
    private static boolean terminate;
    private static boolean stopped;

    Clock() {
        thread = new Thread(this);
        this.setLayout(new BorderLayout());
        this.whiteTimer = new JPanel();
        this.whiteLabel = new JLabel();
        this.whiteTimer.add(whiteLabel);
        this.whiteLabel.setText(whiteTime / 600 + ":" + (whiteTime % 600) / 10 + ":" + (whiteTime % 600) % 10);
        this.blackTimer = new JPanel();
        this.blackLabel = new JLabel();
        this.blackTimer.add(blackLabel);
        this.blackLabel.setText(blackTime / 600 + ":" + (blackTime % 600) / 10 + ":" + (blackTime % 600) % 10);
        this.add(whiteTimer, BorderLayout.WEST);
        this.add(blackTimer, BorderLayout.EAST);
        this.setPreferredSize(CLOCK_PANEL_DIMENSION);
        this.setVisible(true);
    }

    static void updateClock() {
        stopped = false;
        if (turn.isWhite())
            turn = Colour.BLACK;
        else turn = Colour.WHITE;

    }

    @Override
    public void run() {
        while (true) {
            System.out.print("");//unnecessary but necessary

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            if (!stopped) {
                if (turn.isWhite()) {
                    whiteTime++;
                    setText(whiteLabel, blackLabel);
                    if (terminate) {
                        break;
                    }
                } else {
                    blackTime++;
                    setText(whiteLabel, blackLabel);
                    if (terminate) {
                        break;
                    }
                }
            }
        }
    }

    private void setText(JLabel activeLabel, JLabel inactiveLabel) {
        activeLabel.setForeground(Color.RED);
        activeLabel.setText(whiteTime / 600 + ":" + (whiteTime % 600) / 10 + ":" + (whiteTime % 600) % 10);
        inactiveLabel.setForeground(Color.BLACK);
        inactiveLabel.setText(blackTime / 600 + ":" + (blackTime % 600) / 10 + ":" + (blackTime % 600) % 10);

    }

    public static void terminate() {
        Clock.terminate = true;
    }

    static void resetTimer() {
        whiteTime = 0;
        blackTime = 0;
        turn = Colour.WHITE;
    }

    static void stop() {
        stopped = true;
    }

    static void start() {
        if (!started) {
        turn = Colour.WHITE;
        started = true;
        thread.start(); }
    }

}
