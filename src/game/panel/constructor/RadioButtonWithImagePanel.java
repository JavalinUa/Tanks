package game.panel.constructor;

import javax.swing.*;
import java.awt.*;

class RadioButtonWithImagePanel extends JPanel {

    public RadioButtonWithImagePanel(Icon icon, JRadioButton radioButton) {
        JLabel label = new JLabel(icon);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);

        add(label);
        add(radioButton);
    }
}
