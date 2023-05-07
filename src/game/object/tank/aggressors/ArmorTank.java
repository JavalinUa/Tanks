package game.object.tank.aggressors;

import game.object.tank.AbstractTank;
import game.object.tank.Direction;
import game.object.tank.TankColor;
import game.panel.battle.BattleFieldPanel;

public class ArmorTank extends AbstractTank {

    public ArmorTank(int x, int y, String team, String teamPosition, BattleFieldPanel battleFieldPanel, Direction direction) {
        super(x, y, team, teamPosition, battleFieldPanel, direction, TankColor.GREEN);
        armor = 3;
        speed = 20;
        pointsMultiplier = 4;
        bulletSpeedMultiplier = 2;
    }

    @Override
    public int getImagesY() {
        return 112;
    }
}
