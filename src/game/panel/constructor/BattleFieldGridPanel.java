package game.panel.constructor;

import game.battlefield.BattleField;
import game.custom.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import static game.action.Engine.FIRST_DEFENDER;
import static game.action.Engine.IMAGE;
import static game.battlefield.BattleField.*;
import static game.panel.constructor.BattleFiledConstructorPanel.BLOCK_PROPERTY;
import static java.awt.Image.SCALE_SMOOTH;

class BattleFieldGridPanel extends JPanel {

    private static final int ROWS = 9;
    private static final int COLUMNS = 9;

    private static final String V_PROPERTY = "V_PROPERTY";
    private static final String H_PROPERTY = "H_PROPERTY";

    private String[][] map = new String[COLUMNS][ROWS];

    private JButton firstDefenderAssignedButton;

    private static final Map<Pair, DefaultBlock> DEFAULT_BLOCKS = new HashMap<>();

    static {
        DEFAULT_BLOCKS.put(new Pair(8, 4), new DefaultBlock(304, 32, EAGLE));
    }

    public BattleFieldGridPanel(BlockChooserPanel blockChooserPanel) {
        setLayout(new GridLayout(ROWS, COLUMNS));
        setPreferredSize(new Dimension(BattleField.WIDTH, BattleField.HEIGHT));

        for (int v = 0; v < COLUMNS; v++) {
            for (int h = 0; h < ROWS; h++) {
                JButton button = new JButton();
                button.setBackground(Color.BLACK);
                button.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                add(button);

                if (DEFAULT_BLOCKS.containsKey(new Pair(v, h))) {
                    DefaultBlock defaultBlock = DEFAULT_BLOCKS.get(new Pair(v, h));
                    map[v][h] = defaultBlock.blockName;
                    button.setIcon(defaultBlock.icon);
                    button.setEnabled(false);
                } else {
                    map[v][h] = VOID;

                    button.putClientProperty(BLOCK_PROPERTY, VOID);
                    button.putClientProperty(V_PROPERTY, v);
                    button.putClientProperty(H_PROPERTY, h);
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (firstDefenderAssignedButton == null) {
                                if (FIRST_DEFENDER.equals(blockChooserPanel.getBlock())) {
                                    firstDefenderAssignedButton = button;
                                }
                            } else {
                                if (FIRST_DEFENDER.equals(blockChooserPanel.getBlock())) {
                                    firstDefenderAssignedButton.setIcon(null);
                                    firstDefenderAssignedButton.putClientProperty(BLOCK_PROPERTY, VOID);
                                    firstDefenderAssignedButton = button;
                                } else if (firstDefenderAssignedButton == button) {
                                    firstDefenderAssignedButton = null;
                                }
                            }

                            button.setIcon(blockChooserPanel.getIcon());
//                        button.putClientProperty(BLOCK_PROPERTY, blockChooserPanel.getBlock());
                            map[(int) button.getClientProperty(V_PROPERTY)][(int) button.getClientProperty(H_PROPERTY)] =
                                    blockChooserPanel.getBlock();
                        }
                    });
                }
            }
        }
    }

    public String[][] getMap() {
        return map;
    }

    static class DefaultBlock {
        private final Icon icon;
        private final String blockName;

        public DefaultBlock(int imageX, int imageY, String blockName) {
            this.icon = new ImageIcon(IMAGE.getSubimage(imageX, imageY, 16, 16).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, SCALE_SMOOTH));
            this.blockName = blockName;
        }
    }
}
