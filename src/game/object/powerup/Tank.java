package game.object.powerup;

import game.object.tank.Player;
import game.panel.battle.BattleFieldPanel;

public class Tank extends AbstractPowerUp {

    public Tank(int x, int y, BattleFieldPanel battleFieldPanel) {
        super(x, y, battleFieldPanel);
    }

    @Override
    void execute(Player player) {
        player.addExtraLife();
    }

    @Override
    int getImageX() {
        return 336;
    }
}
