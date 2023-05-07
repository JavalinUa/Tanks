package game.panel.constructor;

import game.action.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationPanel extends JPanel {

    private final JButton declineButton = new JButton("Return to menu");
    private final JButton acceptanceButton = new JButton("Accept and play");

    public NavigationPanel(Engine engine, BattleFieldGridPanel battleFieldGridPanel) {
        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.startMainPanel();
            }
        });
        add(declineButton);

        acceptanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.startGame(battleFieldGridPanel.getMap());
            }
        });
        add(acceptanceButton);
    }
}
