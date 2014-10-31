package com.mygdx.game.airhockey;

/**
 * Created by Marco on 30/09/2014.
 */
public class Constants {
    public static float SCENERY_OFFSET;
    public static float SCENERY_CORNER_RADIUS;
    public static float BALL_RADIUS;
    public static float BALL_REPOSITION_OFFSET; //ball offset position from center when scoring
    public static float PADDLE_RADIUS;
    public static float GOAL_WIDTH;
    public static float GOAL_OFFSET;
    public static float WIDTH;
    public static float HEIGHT;
    private float ballRadius;
    private float computerBallRadius;

    static {
        BALL_RADIUS = 1.0f;
        BALL_REPOSITION_OFFSET = 3.0f;
        PADDLE_RADIUS = BALL_RADIUS * 2.0f;
        GOAL_WIDTH = PADDLE_RADIUS * 4.0f;
        SCENERY_OFFSET = 0.5f;
        SCENERY_CORNER_RADIUS = 2.0f;
        GOAL_OFFSET = 2.5f;
        WIDTH = 480.0f;
        HEIGHT = 800.0f;
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
