package game.panel.menu;

import game.action.Engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MenuPanelKeyListener extends KeyAdapter {

    private final Engine engine;
    private final MenuPanel menuPanel;

    public MenuPanelKeyListener(Engine engine, MenuPanel menuPanel) {
        this.engine = engine;
        this.menuPanel = menuPanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (menuPanel.isKeyActionReady()) {
            if (KeyEvent.VK_UP == e.getKeyCode()) {
                menuPanel.setTankLayer(-1);
            } else if (KeyEvent.VK_DOWN == e.getKeyCode()) {
                menuPanel.setTankLayer(1);
            } else if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                menuPanel.startGame();
            }
        }
    }
}
