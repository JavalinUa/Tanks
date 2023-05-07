package game.object.tank;

public enum Direction {

    UP {
        @Override
        public int getFirstTankImageX() {
//            return 4;
            return 0;
        }

        @Override
        public int getSecondTankImageX() {
//            return 68;
            return 16;
        }

        @Override
        public int getTankImageWidth() {
//            return 52;
            return 15;
        }

        @Override
        public int getTankImageHeight() {
//            return 60;
            return 16;
        }

        @Override
        void move(Tank tank) {
            tank.moveVertical(-1);
        }

        @Override
        int getBulletX() {
//            return 1320;
            return 323;
        }

        @Override
        int getBulletWidth() {
//            return 12;
            return 4;
        }

        @Override
        int getBulletHeight() {
//            return 16;
            return 4;
        }

        @Override
        void move(Bullet bullet) {
            bullet.updateY(-1);
        }
    }, DOWN {
        @Override
        public int getFirstTankImageX() {
//            return 264;
            return 64;
        }

        @Override
        public int getSecondTankImageX() {
//            return 328;
            return 80;
        }

        @Override
        public int getTankImageWidth() {
//            return 52;
            return 15;
        }

        @Override
        public int getTankImageHeight() {
//            return 60;
            return 16;
        }

        @Override
        void move(Tank tank) {
            tank.moveVertical(1);
        }

        @Override
        int getBulletX() {
//            return 1384;
            return 339;
        }

        @Override
        int getBulletWidth() {
//            return 12;
            return 4;
        }

        @Override
        int getBulletHeight() {
//            return 16;
            return 4;
        }

        @Override
        void move(Bullet bullet) {
            bullet.updateY(1);
        }
    }, LEFT {
        @Override
        public int getFirstTankImageX() {
//            return 128;
            return 32;
        }

        @Override
        public int getSecondTankImageX() {
//            return 196;
            return 48;
        }

        @Override
        public int getTankImageWidth() {
//            return 60;
            return 16;
        }

        @Override
        public int getTankImageHeight() {
//            return 56;
            return 15;
        }

        @Override
        void move(Tank tank) {
            tank.moveHorizontal(-1);
        }

        @Override
        int getBulletX() {
//            return 1348;
            return 330;
        }

        @Override
        int getBulletWidth() {
//            return 16;
            return 4;
        }

        @Override
        int getBulletHeight() {
//            return 12;
            return 4;
        }

        @Override
        void move(Bullet bullet) {
            bullet.updateX(-1);
        }
    }, RIGHT {
        @Override
        public int getFirstTankImageX() {
//            return 388;
            return 96;
        }

        @Override
        public int getSecondTankImageX() {
//            return 456;
            return 112;
        }

        @Override
        public int getTankImageWidth() {
//            return 60;
            return 16;
        }

        @Override
        public int getTankImageHeight() {
//            return 56;
            return 15;
        }

        @Override
        void move(Tank tank) {
            tank.moveHorizontal(1);
        }

        @Override
        int getBulletX() {
//            return 1412;
            return 346;
        }

        @Override
        int getBulletWidth() {
//            return 16;
            return 4;
        }

        @Override
        int getBulletHeight() {
//            return 12;
            return 4;
        }

        @Override
        void move(Bullet bullet) {
            bullet.updateX(1);
        }
    };

    public abstract int getFirstTankImageX();

    public abstract int getSecondTankImageX();

    public abstract int getTankImageWidth();

    public abstract int getTankImageHeight();

    abstract void move(Tank tank);

    abstract int getBulletX();

    abstract int getBulletWidth();

    abstract int getBulletHeight();

    abstract void move(Bullet bullet);
}
