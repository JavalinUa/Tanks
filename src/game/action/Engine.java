package game.action;

import game.battlefield.BattleField;
import game.object.BFObject;
import game.object.tank.Tank;
import game.panel.battle.BattlePanel;
import game.panel.constructor.BattleFiledConstructorPanel;
import game.panel.menu.MenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static game.battlefield.BattleField.SQUARE_SIZE;

public class Engine extends JFrame {

    public static final int FPS = 60;

    public static final String MAP_ID = "MAP_ID";

    public static final String FIRST_DEFENDER = "FIRST_DEFENDER";
    public static final String SECOND_DEFENDER = "SECOND_DEFENDER";

    public static final String FIRST_DEFENDER_TIGER = "FIRST_DEFENDER_TIGER";
    public static final String SECOND_DEFENDER_TIGER = "SECOND_DEFENDER_TIGER";
    public static final String FIRST_AGGRESSOR_TIGER = "FIRST_AGGRESSOR_TIGER";
    public static final String FIRST_AGGRESSOR_ABRAMS = "FIRST_AGGRESSOR_ABRAMS";
    public static final String SECOND_AGGRESSOR_TIGER = "SECOND_AGGRESSOR_TIGER";
    public static final String SECOND_AGGRESSOR_ABRAMS = "SECOND_AGGRESSOR_ABRAMS";

    private boolean writeRecord = false;
    private final BattleField battleField = new BattleField();
    private final Map<String, Tank> tanksByTeamPosition = new HashMap<>();
    private final TankRecordActions tankRecordActions = new TankRecordActions(this);

    public static BufferedImage IMAGE;
    //    private static final String IMAGE_NAME = "/awt_swing/game/image/sprites.png";
    private static final String IMAGE_NAME = "/game/image/test.png";

    static {
        try {
            IMAGE = ImageIO.read(Objects.requireNonNull(Engine.class.getResource(IMAGE_NAME)));
        } catch (IOException e) {
            System.out.println("Image not found");
        }
    }

    public Engine() {
        setTitle("TANKS");

        setSize(BattleField.WIDTH + 16 + 256, BattleField.HEIGHT + 39 + 128);

        init();

        centreWindow();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void init() {
        startMainPanel();
    }

    private void initTanksByTeamPosition() {
        tanksByTeamPosition.clear();
//        tanksByTeamPosition.put(FIRST_DEFENDER_TIGER, new Tiger(128, 0, Tank.DEFENDER_TEAM, FIRST_DEFENDER_TIGER, this, Direction.UP));
//        tanksByTeamPosition.put(SECOND_DEFENDER_TIGER, new Tiger(384, 512, Tank.DEFENDER_TEAM, SECOND_DEFENDER_TIGER, this, Direction.UP));
//        tanksByTeamPosition.put(FIRST_AGGRESSOR_TIGER, new BasicTank(0, 0, Tank.AGGRESSIVE_TEAM, FIRST_AGGRESSOR_TIGER, this, Direction.RIGHT));
//        tanksByTeamPosition.put(FIRST_AGGRESSOR_ABRAMS, new BasicTank(0, 0, Tank.AGGRESSIVE_TEAM, FIRST_AGGRESSOR_ABRAMS, this, Direction.RIGHT));
//        tanksByTeamPosition.put(SECOND_AGGRESSOR_TIGER, new Tiger(512, 0, Tank.AGGRESSIVE_TEAM, SECOND_AGGRESSOR_TIGER, this, Direction.LEFT));
//        tanksByTeamPosition.put(SECOND_AGGRESSOR_ABRAMS, new Abrams(512, 0, Tank.AGGRESSIVE_TEAM, SECOND_AGGRESSOR_ABRAMS, this, Direction.LEFT));
    }

    public void startGame(boolean secondPlayer) {
        writeRecord = true;
        this.battleField.setMap(0);

//        startPanel(new BattleFieldPanel(this, secondPlayer));
        startPanel(new BattlePanel(this, secondPlayer, 0));
    }

    public void startGame(String[][] map) {
        writeRecord = true;

        battleField.fillBattleFieldByMap(map);
        battleField.getFirstDefenderCoordinates();

        startPanel(new BattlePanel(this, false, 0));
    }

    public void startRecord() {
//        writeRecord = false;
//        startPanel(new BattlePanel(this, false));
//
//        for (Tank tank : tanks) {
//            new TankActionsThread(tank, this).start();
//        }
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                TankStatusRecord tankStatusRecord;
//                while ((tankStatusRecord = (TankStatusRecord) tankRecordActions.read()) != null) {
//                    System.out.println("Time: " + tankStatusRecord.getTime());
//                    sleep(tankStatusRecord.getTime());
//
//                    Tank tank = tanksByTeamPosition.get(tankStatusRecord.getTeamPosition());
//                    tank.setAction(tankStatusRecord.getAction());
//                    tank.setDirection(tankStatusRecord.getDirection());
//
//                    System.out.println(tankStatusRecord.getTeamPosition() + " : " + tankStatusRecord.getDirection());
//                    if (FIRST_DEFENDER_TIGER.equals(tankStatusRecord.getTeamPosition())) {
//                        System.out.println("FIRST_DEFENDER_TIGER: " + tank.getDirection());
//                    }
//                }
//            }
//        }).start();
    }

    public void startConstructor() {
        startPanel(new BattleFiledConstructorPanel(this));
    }

    public void startMainPanel() {
        tankRecordActions.stopWriteRecord();
        tankRecordActions.stopReadRecord();

        initTanksByTeamPosition();
//        startPanel(new MainPanel(this));
        startPanel(new MenuPanel(this));
    }

    private void startPanel(JPanel jPanel) {
        getContentPane().removeAll();
        getContentPane().add(jPanel);
        jPanel.revalidate();
//        requestFocus();
        jPanel.setFocusable(true);
        jPanel.requestFocusInWindow();
    }

    public static void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ex) {
            // ignore
        }
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public BFObject getBFObjectAbove(Tank tank) {
        return battleField.getBlock(tank.getY() / SQUARE_SIZE - 1, tank.getX() / SQUARE_SIZE);
    }

    public BFObject getBFObjectBelow(Tank tank) {
        return battleField.getBlock(tank.getY() / SQUARE_SIZE + 1, tank.getX() / SQUARE_SIZE);
    }

    public BFObject getBFObjectLeft(Tank tank) {
        return battleField.getBlock(tank.getY() / SQUARE_SIZE, tank.getX() / SQUARE_SIZE - 1);
    }

    public BFObject getBFObjectRight(Tank tank) {
        return battleField.getBlock(tank.getY() / SQUARE_SIZE, tank.getX() / SQUARE_SIZE + 1);
    }

    public boolean isWriteRecord() {
        return writeRecord;
    }

    public TankRecordActions getTankRecordActions() {
        return tankRecordActions;
    }

    public Tank getTanksByTeamPosition(String team) {
        return tanksByTeamPosition.get(team);
    }

    public void centreWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }
}
