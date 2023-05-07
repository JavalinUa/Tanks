package game.panel.battle;

import game.action.Engine;
import game.battlefield.BattleField;
import game.custom.Pair;

import javax.swing.*;
import java.awt.*;

import static game.action.Engine.*;
import static game.action.TankSpawnsThread.AGGRESSIVE_TANKS_PER_LEVEL;
import static game.battlefield.BattleField.SQUARE_SIZE;
import static game.panel.battle.BattlePanel.BORDER_COLOR;

public class BattleInfoPanel extends JPanel implements Runnable {

    private static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

    private static final int NUMBER_SIZE = 7;
    private static final Pair[] NUMBER_COORDINATES = new Pair[]{
            new Pair(329, 184), new Pair(338, 184), new Pair(345, 184), new Pair(353, 184), new Pair(361, 184),
            new Pair(329, 192), new Pair(337, 192), new Pair(345, 192), new Pair(353, 192), new Pair(361, 192)};

    private final int mapNumber;
    private final BattleFieldPanel battleFieldPanel;

    public BattleInfoPanel(int mapIdx, BattleFieldPanel battleFieldPanel) {
        this.mapNumber = mapIdx + 1;
        this.battleFieldPanel = battleFieldPanel;

        setPreferredSize(new Dimension(192, 0));

        initDraw();
    }

    @Override
    public void run() {
        while (battleFieldPanel.isDrawing()) {
            repaint();

            Engine.sleep(1000 / FPS);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(BORDER_COLOR);
        g.fillRect(0, 0, 192, BattleField.HEIGHT);
        g.setColor(Color.BLACK);

        drawAggressiveTanks(g);
        drawDefenderLife(g);
        drawStage(g);
    }

    private void drawAggressiveTanks(Graphics g) {
        int count = 0;
        int startX = 60;
        int startY = 0;
        int size = SQUARE_SIZE / 2;

        for (int y = startY; true; y += size) {
            for (int x = startX; x <= startX + size + 5; x += size + 5) {
                if (AGGRESSIVE_TANKS_PER_LEVEL - battleFieldPanel.getTankAggressorSpawnedCount() > count) {
                    g.fillRect(x, y, size, size);
                    g.drawImage(IMAGE, x, y, x + size, y + size, 320, 192, 329, 200, null);

                    count++;
                } else {
                    return;
                }
            }
        }
    }

    private void drawDefenderLife(Graphics g) {
        //IP
        g.fillRect(60, 350, SQUARE_SIZE, HALF_SQUARE_SIZE);
        g.drawImage(IMAGE, 60, 350, 60 + SQUARE_SIZE, 350 + HALF_SQUARE_SIZE,
                377, 136, 392, 143, null);

        //Tank
        g.drawImage(IMAGE, 60, 355 + HALF_SQUARE_SIZE, 60 + HALF_SQUARE_SIZE - 5, 355 + HALF_SQUARE_SIZE * 2,
                377, 144, 384, 152, null);

        //Life
        g.fillRect(61 + HALF_SQUARE_SIZE, 355 + HALF_SQUARE_SIZE, HALF_SQUARE_SIZE, HALF_SQUARE_SIZE);
        Pair numberCoordinate = NUMBER_COORDINATES[battleFieldPanel.getPlayer(FIRST_DEFENDER).getLife()];
        drawNumber(g, 61 + HALF_SQUARE_SIZE, 355 + HALF_SQUARE_SIZE, numberCoordinate);
    }

    private void drawStage(Graphics g) {
        //Flag
        g.fillRect(60, 450, SQUARE_SIZE, SQUARE_SIZE);
        g.drawImage(IMAGE, 60, 450, 60 + SQUARE_SIZE, 450 + SQUARE_SIZE, 376, 184, 391, 198, null);

        //Stage number
        if (mapNumber < 10) {
            drawNumber(g, 70, 520, NUMBER_COORDINATES[mapNumber]);
        } else if (mapNumber < 100) {
            int firstNumber = mapNumber / 10;
            int secondNumber = mapNumber % 10;

            drawNumber(g, 70, 520, NUMBER_COORDINATES[firstNumber]);
            drawNumber(g, 70 + HALF_SQUARE_SIZE, 520, NUMBER_COORDINATES[secondNumber]);
        }
    }

    private void drawNumber(Graphics g, int x, int y, Pair numberCoordinate) {
        g.fillRect(x, y, HALF_SQUARE_SIZE, HALF_SQUARE_SIZE);
        g.drawImage(IMAGE, x, y, x + HALF_SQUARE_SIZE, y + HALF_SQUARE_SIZE,
                numberCoordinate.x, numberCoordinate.y, numberCoordinate.x + NUMBER_SIZE, numberCoordinate.y + NUMBER_SIZE, null);
    }

    private void initDraw() {
        new Thread(this).start();
    }
}
