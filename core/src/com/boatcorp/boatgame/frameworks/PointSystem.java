package com.boatcorp.boatgame.frameworks;

public class PointSystem {
    private int points;

    public PointSystem() {
        this.points = 0;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void incrementPoint() {
        this.points++;
    }

}
