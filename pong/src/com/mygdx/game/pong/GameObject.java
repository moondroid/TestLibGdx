package com.mygdx.game.pong;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Marco on 27/09/2014.
 */
public class GameObject extends Rectangle {
    public final Vector2 position;
    public final Rectangle bounds;
    public final Vector2 velocity;
    public final Vector2 accel;

    public GameObject(float x, float y, float width, float height){
        this.position = new Vector2(x,y);
        this.bounds = new Rectangle(x-width/2, y-height/2, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }

}
