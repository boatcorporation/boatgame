package com.boatcorp.boatgame.frameworks;

public class PointSystem {
    private static float points;

    public static int getPoints() {
        return (int)points;
    }

    public static void setPoints(int newPoints) {
        points = newPoints;
    }

    public static void incrementPoint() {
        points += 1f;
    }

    public static void incrementPoint(float amount) {
        points += amount;
    }

    public static void update(final float delta) {

    }

}
