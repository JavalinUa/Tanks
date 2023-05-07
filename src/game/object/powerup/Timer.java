package game.object.powerup;

import game.action.Engine;
import game.object.tank.Action;
import game.object.tank.Player;
import game.object.tank.Tank;
import game.panel.battle.BattleFieldPanel;

public class Timer extends AbstractPowerUp {

    public Timer(int x, int y, BattleFieldPanel battleFieldPanel) {
        super(x, y, battleFieldPanel);
    }

    @Override
    void execute(Player player) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Tank aggressor: battleFieldPanel.getAggressiveTanks()) {
                    aggressor.setAction(Action.STOP);
                }

                Engine.sleep(5_000);

                for (Tank aggressor: battleFieldPanel.getAggressiveTanks()) {
                    aggressor.setAction(Action.NONE);
                }
            }
        }).start();
    }

    @Override
    int getImageX() {
        return 272;
    }
}
