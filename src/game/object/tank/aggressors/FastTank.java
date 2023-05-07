package game.object.tank.aggressors;

import game.object.tank.AbstractTank;
import game.object.tank.Direction;
import game.object.tank.TankColor;
import game.panel.battle.BattleFieldPanel;

public class FastTank extends AbstractTank {

    public FastTank(int x, int y, String team, String teamPosition, BattleFieldPanel battleFieldPanel, Direction direction) {
        super(x, y, team, teamPosition, battleFieldPanel, direction, TankColor.WHITE);
        speed = 30;
        pointsMultiplier = 2;
        bulletSpeedMultiplier = 2;
    }

    @Override
    public int getImagesY() {
        return 80;
    }
}
