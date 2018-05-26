package cz.cvut.fel.pjv.GUI;

import javax.swing.*;
import java.awt.event.*;

public class ClockSetup extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private static String whiteTimer;
    private static String blackTimer;
    private JTextField whitePlayer;
    private JTextField blackPlayer;
    private static int mode = 0;
    private JRadioButton timeSpendModeRadioButton;
    private JRadioButton classicModeRadioButton;
    private JRadioButton noClockRadioButton;

    ClockSetup(final JFrame frame,
               final boolean bool) {
        super(frame, bool);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        noClockRadioButton.setSelected(true);
        whitePlayer.setEditable(false);
        blackPlayer.setEditable(false);
        noClockRadioButton.addActionListener(actionEvent -> {
            mode = 0;
            whitePlayer.setEditable(false);
            blackPlayer.setEditable(false);
            classicModeRadioButton.setSelected(false);
            timeSpendModeRadioButton.setSelected(false);
        });

        classicModeRadioButton.addActionListener(actionEvent -> {
            mode = 1;
            whitePlayer.setEditable(true);
            blackPlayer.setEditable(true);
            noClockRadioButton.setSelected(false);
            timeSpendModeRadioButton.setSelected(false);
        });
        timeSpendModeRadioButton.addActionListener(actionEvent -> {
            mode = 2;
            whitePlayer.setEditable(false);
            blackPlayer.setEditable(false);
            classicModeRadioButton.setSelected(false);
            noClockRadioButton.setSelected(false);
        });

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
    }

    static int[] getTimes() {
        if (mode == 1) {
            return new int[]{new Integer(whiteTimer), new Integer(blackTimer)};
        }
        return null;
    }

    private void onOK() {
        // add your code here
        if (mode == 1) {
            whiteTimer = whitePlayer.getText();
            blackTimer = blackPlayer.getText();
            if (whiteTimer.isEmpty() || blackTimer.isEmpty()) {
                JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                        "Times can't be empty on classic mode!", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                Clock.modeChanged();
                MainPanel.getGameHistoryPanel().setTimerVisible(true);
                dispose();
            }

        } else if (mode == 2) {
            Clock.modeChanged();
            MainPanel.getGameHistoryPanel().setTimerVisible(true);
            dispose();
        } else {
            Clock.modeChanged();
            MainPanel.getGameHistoryPanel().setTimerVisible(false);
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    static int getMode() {
        return mode;
    }

    void promptUser() {
        setVisible(true);
        repaint();
    }
}
