package com.mygdx.game.airhockey;

import com.badlogic.gdx.Gdx;

/**
 * Created by Marco on 30/09/2014.
 */
public class Utils {
    private static float halfHeight;
    private static float halfWidth;
    private static float width;
    private static float height;
    private static float realHeight;
    private static float realWidth;


    static {
        width = Constants.WIDTH;
        height = Constants.WIDTH * (((float) Gdx.graphics.getHeight()) / ((float) Gdx.graphics.getWidth()));
        halfWidth = Constants.WIDTH / 2.0f - Constants.SCENERY_OFFSET;
        halfHeight = height / 2.0f - Constants.SCENERY_OFFSET;

        realWidth = (float) Gdx.graphics.getWidth();
        realHeight = (float) Gdx.graphics.getHeight();
    }

    public static float getHalfHeight() {
        return halfHeight;
    }

    public static float getHalfWidth() {
        return halfWidth;
    }

    public static float getWidth(){
        return width;
    }

    public static float getHeight() {
        return height;
    }

    public static float getRealHeight() {
        return realHeight;
    }

    public static float getRealWidth() {
        return realWidth;
    }


}
