package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameSetup extends JDialog{
    private MainPanel.PlayerType whitePlayer;
    private MainPanel.PlayerType blackPlayer;

    private static final String HUMAN = "Human";
    private static final String COMPUTER = "Computer";

    GameSetup(final JFrame frame,
              final boolean bool) {
        super(frame, bool);
        final JPanel jPanel = new JPanel(new GridLayout(0,1));
        final JRadioButton whiteHumanButton = new JRadioButton(HUMAN);
        final JRadioButton whiteComputerButton = new JRadioButton(COMPUTER);
        final JRadioButton blackHumanButton = new JRadioButton(HUMAN);
        final JRadioButton blackComputerButton = new JRadioButton(COMPUTER);
        whiteHumanButton.setActionCommand(HUMAN);
        final ButtonGroup whiteGroup = new ButtonGroup();
        whiteGroup.add(whiteHumanButton);
        whiteGroup.add(whiteComputerButton);
        whiteHumanButton.setSelected(true);

        final ButtonGroup blackGroup = new ButtonGroup();
        blackGroup.add(blackHumanButton);
        blackGroup.add(blackComputerButton);
        blackHumanButton.setSelected(true);

        getContentPane().add(jPanel);
        jPanel.add(new JLabel("White"));
        jPanel.add(whiteHumanButton);
        jPanel.add(whiteComputerButton);
        jPanel.add(new JLabel("Black"));
        jPanel.add(blackHumanButton);
        jPanel.add(blackComputerButton);

        jPanel.add(new JLabel("Search"));

        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            whitePlayer = whiteComputerButton.isSelected() ? MainPanel.PlayerType.COMPUTER : MainPanel.PlayerType.HUMAN;
            blackPlayer = blackComputerButton.isSelected() ? MainPanel.PlayerType.COMPUTER : MainPanel.PlayerType.HUMAN;
            GameSetup.this.setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            System.out.println("Cancel");
            GameSetup.this.setVisible(false);
        });

        jPanel.add(cancelButton);
        jPanel.add(okButton);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    void promptUser() {
        setVisible(true);
        repaint();
    }

    boolean isAIPlayer(final Player player) {
        if(player.getColour() == Colour.WHITE) {
            return getWhitePlayerType() == MainPanel.PlayerType.COMPUTER;
        }
        return getBlackPlayerType() == MainPanel.PlayerType.COMPUTER;
    }

    MainPanel.PlayerType getWhitePlayerType() {
        return this.whitePlayer;
    }

    MainPanel.PlayerType getBlackPlayerType() {
        return this.blackPlayer;
    }



}

