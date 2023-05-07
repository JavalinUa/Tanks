package game.action;

import game.ai.DestroyOpponent;
import game.object.tank.Direction;
import game.object.tank.Tank;
import game.object.tank.aggressors.ArmorTank;
import game.object.tank.aggressors.BasicTank;
import game.object.tank.aggressors.FastTank;
import game.object.tank.aggressors.PowerTank;
import game.panel.battle.BattleFieldPanel;

import java.util.Random;

public class TankSpawnsThread extends Thread {

    public static final int AGGRESSIVE_TANKS_PER_LEVEL = 20;
    private static final int AGGRESSIVE_TANK_MAX_ON_BATTLEFIELD = 2;
    private static final Random RANDOM = new Random();

    private int tankAggressorSpawnedCount = 0;
    private boolean createNewTank = true;

    private final BattleFieldPanel battleFieldPanel;

    public TankSpawnsThread(BattleFieldPanel battleFieldPanel) {
        this.battleFieldPanel = battleFieldPanel;
    }

    @Override
    public void run() {
        while (battleFieldPanel.isGameInProgress() && tankAggressorSpawnedCount < AGGRESSIVE_TANKS_PER_LEVEL) {
            if (createNewTank && battleFieldPanel.getAggressiveTanks().size() < AGGRESSIVE_TANK_MAX_ON_BATTLEFIELD) {
                Tank aggressor = getRandomTank(battleFieldPanel);

                if (tankAggressorSpawnedCount % 2 != 0) {
                    aggressor.setX(512);
                }

                if (tankAggressorSpawnedCount % 6 == 0) {
                    aggressor.initFlashing();
                }

                if (tankAggressorSpawnedCount == 3) {
                    aggressor.setAi(new DestroyOpponent(aggressor, battleFieldPanel.getEngine(), battleFieldPanel.getEagle()));
                    new TankActionsThread(aggressor, battleFieldPanel).start();
                }

                battleFieldPanel.getTanks().add(aggressor);
                battleFieldPanel.getAggressiveTanks().add(aggressor);

                tankAggressorSpawnedCount++;
            }

            Engine.sleep(500);
        }

        battleFieldPanel.setAggressorsDestroyed(true);
    }

    private Tank getRandomTank(BattleFieldPanel battleFieldPanel) {
        switch (RANDOM.nextInt(4)) {
            case 0:
                return getArmorTank(battleFieldPanel);
            case 1:
                return getBasicTank(battleFieldPanel);
            case 2:
                return getFastTank(battleFieldPanel);
            case 3:
                return getPowerTank(battleFieldPanel);
        }
        throw new RuntimeException("Hello :)");
    }

    private Tank getArmorTank(BattleFieldPanel battleFieldPanel) {
        return new ArmorTank(0, 0, Tank.AGGRESSIVE_TEAM, "Deprecated", battleFieldPanel, Direction.DOWN);
    }

    private Tank getBasicTank(BattleFieldPanel battleFieldPanel) {
        return new BasicTank(0, 0, Tank.AGGRESSIVE_TEAM, "Deprecated", battleFieldPanel, Direction.DOWN);
    }

    private Tank getFastTank(BattleFieldPanel battleFieldPanel) {
        return new FastTank(0, 0, Tank.AGGRESSIVE_TEAM, "Deprecated", battleFieldPanel, Direction.DOWN);
    }

    private Tank getPowerTank(BattleFieldPanel battleFieldPanel) {
        return new PowerTank(0, 0, Tank.AGGRESSIVE_TEAM, "Deprecated", battleFieldPanel, Direction.DOWN);
    }

    public void setCreateNewTank(boolean createNewTank) {
        if (createNewTank) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Engine.sleep(2000);
                    TankSpawnsThread.this.createNewTank = true;
                }
            }).start();
        } else {
            this.createNewTank = false;
        }
    }

    public int getTankAggressorSpawnedCount() {
        return tankAggressorSpawnedCount;
    }
}
