package com.mygdx.game.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by Marco on 25/09/2014.
 */
public class Pong extends Game {
    boolean firstTimeCreate = true;

    @Override
    public void create() {
        Assets.load();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); //important!
    }

    @Override
    public void dispose() {

    }
}
