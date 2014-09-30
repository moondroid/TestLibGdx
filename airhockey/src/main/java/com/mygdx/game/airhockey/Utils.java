package com.mygdx.game.airhockey;

import com.badlogic.gdx.Gdx;

/**
 * Created by Marco on 30/09/2014.
 */
public class Utils {
    private static float halfHeight;
    private static float halfWidth;
    private static float height;
    private float realHeight;
    private float realWidth;
    private float virtualHeight;
    private float virtualWidth;

    static {
        height = 20.0f * (((float) Gdx.graphics.getHeight()) / ((float) Gdx.graphics.getWidth()));
        halfWidth = Constants.WIDTH / 2.0f - Constants.OFFSET;
        halfHeight = height / 2.0f - Constants.OFFSET;
    }

    public Utils() {
        this.virtualWidth = 480.0f;
        this.virtualHeight = 800.0f;
        this.realWidth = (float) Gdx.graphics.getWidth();
        this.realHeight = (float) Gdx.graphics.getHeight();
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHeight() {
        return height;
    }

    public float getRealHeight() {
        return this.realHeight;
    }

    public float getRealWidth() {
        return this.realWidth;
    }

    public float getVirtualHeight() {
        return this.virtualHeight;
    }

    public float getVirtualWidth() {
        return this.virtualWidth;
    }
}
