package game.object.powerup;

import game.object.tank.Player;
import game.panel.battle.BattleFieldPanel;

public class Star extends AbstractPowerUp {

    public Star(int x, int y, BattleFieldPanel battleFieldPanel) {
        super(x, y, battleFieldPanel);
    }

    @Override
    void execute(Player player) {
        if (player.getTierIdx() < 3) {
            switch (player.getTierIdx()) {
                case 0:
                    executeSecondTier(player);
                    break;
                case 1:
                    executeThirdTier(player);
                    break;
                case 2:
                    executeFourthTier(player);
                    break;
            }

            player.increaseTierIdx();
        }
    }

    private void executeSecondTier(Player player) {
        player.setBulletSpeedMultiplier(3);
        player.setImageCoordinates(0, 16);
    }

    private void executeThirdTier(Player player) {
        player.setOwnBulletsCount(2);
        player.setImageCoordinates(0, 32);
    }

    private void executeFourthTier(Player player) {
        player.setDestroyRock(true);
        player.setImageCoordinates(0, 48);
    }

    @Override
    int getImageX() {
        return 304;
    }
}
