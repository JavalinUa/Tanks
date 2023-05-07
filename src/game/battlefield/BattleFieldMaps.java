package game.battlefield;

import java.util.ArrayList;
import java.util.List;

import static game.battlefield.BattleField.*;

public class BattleFieldMaps {
    private final List<String[][]> maps = new ArrayList<>();

    public BattleFieldMaps() {
        maps.add(firstMap);
        maps.add(secondMap);
    }

    private String[][] firstMap = {
            {VOID, VOID, VOID, VOID, BRICK, WATER, WATER, VOID, VOID},
            {VOID, BRICK, WATER, WATER, VOID, VOID, ROCK, VOID, VOID},
            {BRICK, ROCK, VOID, SHRUB, SHRUB, SHRUB, VOID, VOID, VOID},
            {VOID, BRICK, SHRUB, VOID, VOID, VOID, SHRUB, ROCK, ROCK},
            {ROCK, VOID, SHRUB, VOID, VOID, VOID, SHRUB, VOID, VOID},
            {BRICK, VOID, SHRUB, VOID, VOID, VOID, SHRUB, BRICK, VOID},
            {BRICK, ROCK, VOID, SHRUB, SHRUB, SHRUB, VOID, BRICK, VOID},
            {BRICK, BRICK, VOID, BRICK, BRICK, BRICK, VOID, VOID, VOID},
            {VOID, VOID, VOID, BRICK, EAGLE, BRICK, VOID, VOID, VOID}};

    private String[][] secondMap = {
            {"V", "V", "R", "B", "R", "V", "W", "V", "V"},
            {"V", "B", "V", "R", "V", "V", "V", "V", "V"},
            {"V", "V", "V", "S", "B", "V", "R", "W", "W"},
            {"V", "R", "V", "S", "W", "V", "V", "V", "V"},
            {"V", "B", "V", "S", "W", "V", "V", "V", "B"},
            {"B", "R", "V", "S", "W", "V", "V", "V", "B"},
            {"B", "B", "V", "S", "S", "S", "V", "V", "V"},
            {"R", "B", "V", "B", "B", "B", "V", "V", "B"},
            {"V", "B", "V", "B", "E", "B", "V", "B", "B"}};

    public String[][] getMap(int idx) {
        return maps.get(idx);
    }

    public int getMapSize() {
        return maps.size();
    }
}
