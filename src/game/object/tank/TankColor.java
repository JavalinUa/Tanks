package game.object.tank;

public enum TankColor {

    YELLOW {
        @Override
        int getBaseX() {
            return 0;
        }

        @Override
        int getBaseY() {
            return 0;
        }
    },
    WHITE {
        @Override
        int getBaseX() {
            return 128;
        }

        @Override
        int getBaseY() {
            return 0;
        }
    },
    GREEN {
        @Override
        int getBaseX() {
            return 0;
        }

        @Override
        int getBaseY() {
            return 128;
        }
    },
    RED {
        @Override
        int getBaseX() {
            return 128;
        }

        @Override
        int getBaseY() {
            return 128;
        }
    };

    abstract int getBaseX();

    abstract int getBaseY();
}
