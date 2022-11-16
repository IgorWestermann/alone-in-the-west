package utils;

public class Constants {
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DIE = 4;


        public static int GetSpriteAmount(int player_action) {
            return switch (player_action) {
                case RUNNING -> 8;
                case IDLE, ATTACK -> 6;
                case HIT -> 1;
                case DIE -> 14;
                default -> 0;

            };
        }
    }
}

