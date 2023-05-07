package game.object.powerup;

import game.object.tank.Player;
import game.panel.battle.BattleFieldPanel;

public class Helmet extends AbstractPowerUp {

    public Helmet(int x, int y, BattleFieldPanel battleFieldPanel) {
        super(x, y, battleFieldPanel);
    }

    @Override
    void execute(Player player) {
        player.initForceField();
    }

    @Override
    int getImageX() {
        return 256;
    }
}
