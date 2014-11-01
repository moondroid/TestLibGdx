package com.mygdx.game.airhockey;

/**
 * Created by Marco on 30/09/2014.
 */
public class Constants {
    public static float SCALE = 0.1f;
    public static float SCENERY_OFFSET;
    public static float SCENERY_CORNER_RADIUS;
    public static float BALL_RADIUS;
    public static float BALL_REPOSITION_OFFSET; //ball offset position from center when scoring
    public static float PLAYER_REPOSITION_OFFSET; //player offset position from center when scoring
    public static float PADDLE_RADIUS;
    public static float GOAL_WIDTH;
    public static float GOAL_OFFSET;
    public static float WIDTH;
    public static float HEIGHT;
    private float ballRadius;
    private float computerBallRadius;

    static {

        WIDTH = 480.0f * SCALE;
        HEIGHT = 800.0f * SCALE;

        GOAL_WIDTH = 180.0f * SCALE;
        SCENERY_OFFSET = 27.0f * SCALE;
        SCENERY_CORNER_RADIUS = 20.0f * SCALE;
        GOAL_OFFSET = 57.0f * SCALE;

        PADDLE_RADIUS = GOAL_WIDTH * 0.25f;
        BALL_RADIUS = PADDLE_RADIUS * 0.5f;
        BALL_REPOSITION_OFFSET = 135.0f * SCALE;
        PLAYER_REPOSITION_OFFSET = 260.0f * SCALE;
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
