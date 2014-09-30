package com.mygdx.game.airhockey;

/**
 * Created by Marco on 30/09/2014.
 */
public class Constants {
    public static float OFFSET;
    public static float PLAYERBALLRADIUS;
    public static float WIDTH;
    private float ballRadius;
    private float computerBallRadius;

    static {
        PLAYERBALLRADIUS = 1.5f;
        OFFSET = 0.5f;
        WIDTH = 20.0f;
    }

    public Constants() {
        this.computerBallRadius = 1.5f;
        this.ballRadius = 1.0f;
    }

    public float getBallRadius() {
        return this.ballRadius;
    }

    public float getComputerBallRadius() {
        return this.computerBallRadius;
    }

    public void setBallRadius(float ballRadius) {
        this.ballRadius = ballRadius;
    }

    public void setComputerBallRadius(float computerBallRadius) {
        this.computerBallRadius = computerBallRadius;
    }
}
