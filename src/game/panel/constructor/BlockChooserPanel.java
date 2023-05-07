package game.panel.constructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.action.Engine.FIRST_DEFENDER;
import static game.action.Engine.IMAGE;
import static game.battlefield.BattleField.*;
import static game.panel.constructor.BattleFiledConstructorPanel.BLOCK_PROPERTY;
import static java.awt.Image.SCALE_SMOOTH;

class BlockChooserPanel extends JPanel {

    private Icon icon;
    private String block;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    public BlockChooserPanel() {
        setLayout(new GridLayout(4, 2, 10, 10));

        addRadioButtonWithImage(336, 0, VOID);
        addRadioButtonWithImage(256, 0, BRICK);
        addRadioButtonWithImage(256, 16, ROCK);
        addRadioButtonWithImage(272, 32, SHRUB);
        addRadioButtonWithImage(256, 48, WATER);
        addRadioButtonWithImage(304, 32, EAGLE);
        addRadioButtonWithImage(0, 0, FIRST_DEFENDER);

        icon = new ImageIcon(IMAGE.getSubimage(336, 0, 16, 16).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, SCALE_SMOOTH));
        block = VOID;
    }

    private void addRadioButtonWithImage(int imageX, int imageY, String block) {
        Icon icon = new ImageIcon(IMAGE.getSubimage(imageX, imageY, 16, 16).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, SCALE_SMOOTH));

        JRadioButton radioButton = new JRadioButton();
        radioButton.putClientProperty(BLOCK_PROPERTY, block);
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BlockChooserPanel.this.icon = icon;
                BlockChooserPanel.this.block = block;
            }
        });
        buttonGroup.add(radioButton);

        add(new RadioButtonWithImagePanel(icon, radioButton));
    }

    public Icon getIcon() {
        return icon;
    }

    public String getBlock() {
        return block;
    }
}
