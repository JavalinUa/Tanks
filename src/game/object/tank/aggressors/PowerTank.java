package game.object.tank.aggressors;

import game.object.tank.AbstractTank;
import game.object.tank.Direction;
import game.object.tank.TankColor;
import game.panel.battle.BattleFieldPanel;

public class PowerTank extends AbstractTank {

    public PowerTank(int x, int y, String team, String teamPosition, BattleFieldPanel battleFieldPanel, Direction direction) {
        super(x, y, team, teamPosition, battleFieldPanel, direction, TankColor.WHITE);
        speed = 20;
        pointsMultiplier = 3;
        bulletSpeedMultiplier = 3;
    }

    @Override
    public int getImagesY() {
        return 96;
    }
}
