package game.action;

import game.object.tank.Tank;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TankRecordActions {

    private static final String GAME_RECORD_NAME = "GameRecord.txt";

    private final Engine engine;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private long lastTimerMillis;

    private final Lock lock = new ReentrantLock();

    public TankRecordActions(Engine engine) {
        this.engine = engine;
    }

    public boolean initReadRecord() {
        try {
            ois = new ObjectInputStream(new FileInputStream(GAME_RECORD_NAME));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean initWriteRecord() {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(GAME_RECORD_NAME));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void initTimer() {
        lastTimerMillis = System.currentTimeMillis();
    }

    public void write(Object o) {
        try {
            lock.lock();

            oos.writeObject(o);
            oos.flush();

            lock.unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Tank tank) {
        try {
            lock.lock();

            long currentTime = System.currentTimeMillis();
            oos.writeObject(new TankStatusRecord(currentTime - lastTimerMillis, tank));
            lastTimerMillis = currentTime;
            oos.flush();

            System.out.println(tank.getTeamPosition() + " : " + tank.getDirection());
            lock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object read() {
        try {
            return ois.readObject();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    public void stopWriteRecord() {
        try {
            if (oos != null)
                oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopReadRecord() {
        try {
            if (ois != null)
                ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
