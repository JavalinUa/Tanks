package game.object.powerup;

import game.object.tank.Tank;
import game.object.tank.Player;
import game.panel.battle.BattleFieldPanel;

public class Grenade extends AbstractPowerUp {

    public Grenade(int x, int y, BattleFieldPanel battleFieldPanel) {
        super(x, y, battleFieldPanel);
    }

    @Override
    void execute(Player player) {
        battleFieldPanel.setCreateNewTank(false);

        for (Tank aggressiveTank : battleFieldPanel.getAggressiveTanks()) {
            aggressiveTank.removePoints();
            aggressiveTank.setArmor(0);
            aggressiveTank.destroy();
        }

        battleFieldPanel.setCreateNewTank(true);
    }

    @Override
    int getImageX() {
        return 320;
    }
}
