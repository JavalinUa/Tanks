package game.panel.menu;

import game.action.Engine;
import game.battlefield.BattleField;
import game.object.tank.Direction;

import javax.swing.*;
import java.awt.*;

import static game.action.Engine.FPS;
import static game.action.Engine.IMAGE;

public class MenuPanel extends JPanel implements Runnable {

    private static final int TOP_Y = 64;
    private static final int TANKS_WORD_LEFT_PADDING = 96;

    private static final int IMAGE_DELAY = 40;
    private static final int PLAYER_1_Y = 400;
    private static final int PLAYERS_2_Y = 500;
    private static final int STAGE_CONSTRUCTOR_Y = 600;
    private static final int[] MENU_LAYERS = new int[]{PLAYER_1_Y, PLAYERS_2_Y, STAGE_CONSTRUCTOR_Y};

    private int tankLayerIdx = 0;
    //    private int movingIdx = 400;
    private int movingIdx = 0;
    private final Direction direction = Direction.RIGHT;
    private int imageX = direction.getFirstTankImageX();
    private int imageIdx = 0;

    private final Engine engine;

    public MenuPanel(Engine engine) {
        this.engine = engine;

        addKeyListener(new MenuPanelKeyListener(engine, this));

        setBackground(Color.BLACK);
        initDraw();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (movingIdx != 0) {
            movingIdx -= 2;
        } else {
            drawTank(g);
        }

        drawTanksWord(g);
        drawPlayersSelection(g);
    }

    private void drawTanksWord(Graphics g) {
        for (int y = TOP_Y; y < TOP_Y + 128; y += 64) {
            for (int x = 0; x < getWidth(); x += 64) {
//                g.drawImage(IMAGE, x, movingIdx + y, x + 64, movingIdx + y + 64, 1052, 0, 1052 + 64, 64, null);
                g.drawImage(IMAGE, x, movingIdx + y, x + 64, movingIdx + y + 64, 256, 0, 256 + 15, 15, null);
            }
        }

        g.setColor(Color.BLACK);

        //T 10 114
        drawVoidTanksPartImage(g, 0, 0, 10, 16);     //1
        drawVoidTanksPartImage(g, 0, 16, 42, 96);    //last

        drawVoidTanksPartImage(g, 106, 0, 8, 16);    //1
        drawVoidTanksPartImage(g, 74, 16, 40, 96);   //last

        //A 114 242
        drawVoidTanksPartImage(g, 114, 0, 40, 16);   //1
        drawVoidTanksPartImage(g, 114, 16, 24, 16);  //2
        drawVoidTanksPartImage(g, 114, 32, 8, 80);   //last

        drawVoidTanksPartImage(g, 170, 16, 16, 16);  //2
        drawVoidTanksPartImage(g, 154, 32, 48, 32);  //3-4
        drawVoidTanksPartImage(g, 154, 82, 48, 32);  //6-7

        drawVoidTanksPartImage(g, 202, 0, 40, 16);   //1
        drawVoidTanksPartImage(g, 218, 16, 24, 16);  //2
        drawVoidTanksPartImage(g, 234, 32, 8, 80);   //last

        //N 242 370
        drawVoidTanksPartImage(g, 242, 0, 8, 112);   //last

        drawVoidTanksPartImage(g, 282, 0, 48, 16);   //1
        drawVoidTanksPartImage(g, 298, 16, 32, 16);  //2
        drawVoidTanksPartImage(g, 314, 32, 16, 16);  //3
        drawVoidTanksPartImage(g, 282, 64, 16, 16);  //5
        drawVoidTanksPartImage(g, 282, 80, 32, 16);  //6
        drawVoidTanksPartImage(g, 282, 96, 48, 16);  //7

        drawVoidTanksPartImage(g, 362, 0, 8, 112);   //last

        //K 370 498
        drawVoidTanksPartImage(g, 370, 0, 8, 112);   //last

        drawVoidTanksPartImage(g, 410, 0, 48, 16);   //1
        drawVoidTanksPartImage(g, 410, 16, 32, 16);  //2
        drawVoidTanksPartImage(g, 410, 32, 16, 16);  //3
        drawVoidTanksPartImage(g, 410, 80, 16, 16);  //6
        drawVoidTanksPartImage(g, 410, 96, 32, 16);  //7

        drawVoidTanksPartImage(g, 490, 0, 8, 16);    //1
        drawVoidTanksPartImage(g, 474, 16, 24, 16);  //2
        drawVoidTanksPartImage(g, 458, 32, 40, 16);  //3
        drawVoidTanksPartImage(g, 442, 48, 56, 16);  //4
        drawVoidTanksPartImage(g, 458, 64, 40, 16);  //5
        drawVoidTanksPartImage(g, 474, 80, 24, 16);  //6
        drawVoidTanksPartImage(g, 490, 96, 8, 16);   //7

        //S 498 610
        drawVoidTanksPartImage(g, 498, 0, 8, 16);    //1
        drawVoidTanksPartImage(g, 498, 16, 8, 16);   //2
        drawVoidTanksPartImage(g, 498, 32, 24, 16);  //3
        drawVoidTanksPartImage(g, 498, 48, 40, 16);  //4
        drawVoidTanksPartImage(g, 498, 64, 56, 16);  //5
        drawVoidTanksPartImage(g, 498, 80, 8, 32);   //last

        drawVoidTanksPartImage(g, 538, 16, 48, 16);  //2
        drawVoidTanksPartImage(g, 522, 80, 48, 16);  //6

        drawVoidTanksPartImage(g, 602, 0, 8, 32);    //1-2
        drawVoidTanksPartImage(g, 554, 32, 56, 16);  //3
        drawVoidTanksPartImage(g, 570, 48, 40, 16);  //4
        drawVoidTanksPartImage(g, 586, 64, 24, 16);  //5
        drawVoidTanksPartImage(g, 602, 80, 8, 32);   //last

        //voids to the left, right and bottom around the word
        g.fillRect(0, movingIdx + TOP_Y, TANKS_WORD_LEFT_PADDING, 112);                                                 //left
        g.fillRect(TANKS_WORD_LEFT_PADDING + 610, movingIdx + TOP_Y, getWidth() - TANKS_WORD_LEFT_PADDING + 610, 112);  //right
        g.fillRect(0, movingIdx + TOP_Y + 112, getWidth(), 16);                                                         //bottom
    }

    private void drawPlayersSelection(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));

        g.drawString("1  P L A Y E R", 250, movingIdx + PLAYER_1_Y);
        g.drawString("2  P L A Y E R S", 250, movingIdx + PLAYERS_2_Y);
        g.drawString("C O N S T R U C T O R", 250, movingIdx + STAGE_CONSTRUCTOR_Y);
    }

    private void drawTank(Graphics g) {
        if (System.currentTimeMillis() % IMAGE_DELAY * 2 > IMAGE_DELAY) {
            if (imageIdx == 0) {
                imageX = direction.getFirstTankImageX();
                imageIdx = 1;
            } else {
                imageX = direction.getSecondTankImageX();
                imageIdx = 0;
            }
        }

        g.drawImage(IMAGE, 150, MENU_LAYERS[tankLayerIdx] - BattleField.SQUARE_SIZE + 16,
                150 + BattleField.SQUARE_SIZE, MENU_LAYERS[tankLayerIdx] + 16,
                imageX, 0,
//                imageX + direction.getTankImageWidth(), direction.getTankImageHeight() + 4,
                imageX + direction.getTankImageWidth(), direction.getTankImageHeight(),
                null);
    }

    private void drawVoidTanksPartImage(Graphics g, int x, int y, int width, int height) {
        g.fillRect(TANKS_WORD_LEFT_PADDING + x, movingIdx + TOP_Y + y, width, height);
    }

    void startGame() {
//        engine.getTanks().clear();
//        engine.getAggressiveTanks().clear();
//        engine.getKeyListenerTanks().clear();
//
//        engine.getBattleField().setMap(0);
//
//        Tank defenderTank = engine.getTanksByTeamPosition(FIRST_DEFENDER_TIGER);
//        engine.getTanks().add(defenderTank);
//        engine.getKeyListenerTanks().put(FIRST_DEFENDER, defenderTank);
//
//        if (tankLayerIdx == 1) {
//            Tank secondDefenderTank = engine.getTanksByTeamPosition(SECOND_DEFENDER_TIGER);
//            engine.getTanks().add(secondDefenderTank);
//            engine.getKeyListenerTanks().put(SECOND_DEFENDER, secondDefenderTank);
//        }
//
//        Tank firstAggressiveTank = engine.getTanksByTeamPosition(FIRST_AGGRESSOR_ABRAMS);
////        firstAggressiveTank.setAi(new DestroyOpponent(firstAggressiveTank, engine, defenderTank));
//        engine.getTanks().add(firstAggressiveTank);
//        engine.getAggressiveTanks().add(firstAggressiveTank);
//
//        Tank secondAggressiveTank = engine.getTanksByTeamPosition(SECOND_AGGRESSOR_ABRAMS);
////        secondAggressiveTank.setAi(new DestroyOpponent(secondAggressiveTank, engine, engine.getBattleField().getEagle()));
//        engine.getTanks().add(secondAggressiveTank);
//        engine.getAggressiveTanks().add(secondAggressiveTank);


        if (tankLayerIdx == 2) {
            engine.startConstructor();
        } else {
            engine.startGame(tankLayerIdx == 1);
        }
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            Engine.sleep(1000 / FPS);
        }
    }

    private void initDraw() {
        new Thread(this).start();
    }

    public boolean isKeyActionReady() {
        return movingIdx == 0;
    }

    public void setTankLayer(int tankLayerChanger) {
        int idx = tankLayerIdx + tankLayerChanger;

        if (idx < 0) {
            this.tankLayerIdx = MENU_LAYERS.length - 1;
        } else if (idx > MENU_LAYERS.length - 1) {
            this.tankLayerIdx = 0;
        } else {
            this.tankLayerIdx = idx;
        }
    }
}
