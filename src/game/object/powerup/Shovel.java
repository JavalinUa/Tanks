package game.object.powerup;

import game.object.tank.Player;
import game.panel.battle.BattleFieldPanel;

public class Shovel extends AbstractPowerUp {

    public Shovel(int x, int y, BattleFieldPanel battleFieldPanel) {
        super(x, y, battleFieldPanel);
    }

    @Override
    void execute(Player player) {
        battleFieldPanel.initRockWall();
    }

    @Override
    int getImageX() {
        return 288;
    }
}
