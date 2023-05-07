package game.object.tank;

import game.panel.battle.BattleFieldPanel;

public class Abrams extends AbstractTank {

    public Abrams(int x, int y, String team, String teamPosition, BattleFieldPanel battleFieldPanel, Direction direction) {
        super(x, y, team, teamPosition, battleFieldPanel, direction, TankColor.WHITE);
        speed = 15;
        armor = 1;
    }

    @Override
    public int getVerticalImageOffset() {
        return 0;
    }

    @Override
    public int getImagesY() {
//        return 986;
        return 0;
    }
}
