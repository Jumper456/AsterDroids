package net.yotvoo.asterd.app;

class Constants {
    static final double MAX_ENEMY_SIZE = 20d;
    static final double MIN_ENEMY_SIZE = 5d;
    static final double MAX_ENEMY_SPEED = 3d;
    static final double MAX_ENEMY_PROXIMITY = 100d;
    static final double MAX_ENEMY_COUNT = 20d;
    static final double ENEMY_SPAWN_RATIO = 0.02d;

    static final double MAX_STAR_SIZE = 4d;
    static final int STARS_NUMBER = 500;

    static final double BULLETS_INTERVAL = 300d;

    //player object acceleration made during one update cycle
    static final double ACCELERATION = 0.1d;
    //player object rotation step made during one update cycle
    static final double ROTATE_STEP = 3d;

    static final int MAX_BULLET_AGE = 1000; // 1 sek

    static final String HIGH_SCORE_FILE_NAME = "AsterDroids.score";
}
