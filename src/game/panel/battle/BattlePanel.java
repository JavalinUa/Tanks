package game.panel.battle;

import game.action.Engine;

import javax.swing.*;
import java.awt.*;

public class BattlePanel extends JPanel {

    public static final Color BORDER_COLOR = new Color(99, 99, 99);

    private final Engine engine;
    private final boolean secondPlayer;

    private BattleFieldPanel battleFieldPanel;

    public BattlePanel(Engine engine, boolean secondPlayer, int mapIdx) {
        this.engine = engine;
        this.secondPlayer = secondPlayer;

        init(mapIdx);
    }

    private void init(int mapIdx) {
        setLayout(new BorderLayout());

        add(createDefaultBorderPanel(0, 64), BorderLayout.NORTH);
        add(createDefaultBorderPanel(0, 64), BorderLayout.SOUTH);
        add(createDefaultBorderPanel(64, 0), BorderLayout.WEST);

        battleFieldPanel = new BattleFieldPanel(engine, secondPlayer);
        add(battleFieldPanel, BorderLayout.CENTER);

        BattleInfoPanel battleInfoPanel = new BattleInfoPanel(mapIdx, battleFieldPanel);
        add(battleInfoPanel, BorderLayout.EAST);
    }

    private JPanel createDefaultBorderPanel(int width, int height) {
        JPanel container = new JPanel();
        container.setBackground(BORDER_COLOR);
        container.setPreferredSize(new Dimension(width, height));
        return container;
    }

    @Override
    public void setFocusable(boolean focusable) {
        battleFieldPanel.setFocusable(focusable);
    }

    @Override
    public boolean requestFocusInWindow() {
        return battleFieldPanel.requestFocusInWindow();
    }
}
