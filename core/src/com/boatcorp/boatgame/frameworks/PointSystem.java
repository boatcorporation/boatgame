package com.boatcorp.boatgame.frameworks;

public class PointSystem {
    private static int points;

    public static int getPoints() {
        return points;
    }

    public static void setPoints(int points) {
        points = points;
    }

    public static void incrementPoint() {
        points++;
    }

    public static void update(final float delta) {

    }

}
