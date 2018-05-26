package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.player.Player;

import javax.swing.*;
import java.awt.*;

public class GameSetup extends JDialog {
    private PlayerType whitePlayerType;
    private PlayerType blackPlayerType;

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
        jPanel.add(new JLabel("Black"));
        jPanel.add(blackHumanButton);
        jPanel.add(blackComputerButton);
        jPanel.add(new JLabel("White"));
        jPanel.add(whiteHumanButton);
        jPanel.add(whiteComputerButton);


        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");
//      sets players
        okButton.addActionListener(e -> {
            whitePlayerType = whiteComputerButton.isSelected()
                    ? PlayerType.COMPUTER
                    : PlayerType.HUMAN;
            blackPlayerType = blackComputerButton.isSelected()
                    ? PlayerType.COMPUTER
                    : PlayerType.HUMAN;
            GameSetup.this.setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            System.out.println("Cancel");
            GameSetup.this.setVisible(false);
        });

        jPanel.add(cancelButton);
        jPanel.add(okButton);

        pack();
        setVisible(false);
    }

    void promptUser() {
        setVisible(true);
        repaint();
    }
//  checks what type of player is current player by colour
    public boolean isAIPlayer(final Player player) {
        if(player.getColour() == Colour.WHITE) {
            return getWhitePlayerType() == PlayerType.COMPUTER;
        }
        return getBlackPlayerType() == PlayerType.COMPUTER;
    }

    private PlayerType getWhitePlayerType() {
        return this.whitePlayerType;
    }

    private PlayerType getBlackPlayerType() {
        return this.blackPlayerType;
    }

    public enum PlayerType {
            HUMAN,
            COMPUTER
    }
}

