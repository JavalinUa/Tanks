package game.object.tank.aggressors;

import game.object.tank.AbstractTank;
import game.object.tank.Direction;
import game.object.tank.TankColor;
import game.panel.battle.BattleFieldPanel;

public class BasicTank extends AbstractTank {

    public BasicTank(int x, int y, String team, String teamPosition, BattleFieldPanel battleFieldPanel, Direction direction) {
        super(x, y, team, teamPosition, battleFieldPanel, direction, TankColor.WHITE);
        speed = 10;
        pointsMultiplier = 1;
    }

    @Override
    public int getImagesY() {
        return 64;
    }
}
