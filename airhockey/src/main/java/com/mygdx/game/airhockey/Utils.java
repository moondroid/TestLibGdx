package com.mygdx.game.airhockey;

import com.badlogic.gdx.Gdx;


/**
 * Created by Marco on 30/09/2014.
 */
public class Utils {
    private static float halfPlayfieldHeight;
    private static float halfPlayfieldWidth;
    private static float width;
    private static float height;
    private static float realHeight;
    private static float realWidth;


    static {
        width = Constants.WIDTH;
        height = Constants.HEIGHT;
        halfPlayfieldWidth = Constants.WIDTH / 2.0f - Constants.SCENERY_OFFSET;
        halfPlayfieldHeight = height / 2.0f - Constants.SCENERY_OFFSET;

        realWidth = (float) Gdx.graphics.getWidth();
        realHeight = (float) Gdx.graphics.getHeight();
    }

    public static float getHalfPlayfieldHeight() {
        return halfPlayfieldHeight;
    }

    public static float getHalfPlayfieldWidth() {
        return halfPlayfieldWidth;
    }

    public static float getWidth(){
        return width;
    }

    public static float getHeight() {
        return height;
    }

    public static float getHalfWidth(){
        return width / 2.0f;
    }

    public static float getHalfHeight() {
        return height / 2.0f;
    }

    public static float getRealHeight() {
        return realHeight;
    }

    public static float getRealWidth() {
        return realWidth;
    }

    public static float getGoalLineHeight(){
        return getHalfHeight()-Constants.GOAL_OFFSET;
    }
}
