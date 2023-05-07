package game.panel.constructor;

import game.action.Engine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BattleFiledConstructorPanel extends JPanel {

    public static final String BLOCK_PROPERTY = "BLOCK_PROPERTY";

    private final Engine engine;

    public BattleFiledConstructorPanel(Engine engine) {
        this.engine = engine;

        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        BlockChooserPanel blockChooserPanel = new BlockChooserPanel();
        add(blockChooserPanel, BorderLayout.WEST);

        BattleFieldGridPanel battleFieldGridPanel = new BattleFieldGridPanel(blockChooserPanel);
        add(battleFieldGridPanel, BorderLayout.CENTER);

        add(new NavigationPanel(engine, battleFieldGridPanel), BorderLayout.SOUTH);
    }
}
