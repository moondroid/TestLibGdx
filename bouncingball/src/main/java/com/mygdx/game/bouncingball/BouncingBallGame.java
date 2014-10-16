package com.mygdx.game.bouncingball;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class BouncingBallGame extends Game {
    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //this.setScreen(new BouncingBallScreen(this));
        this.setScreen(new TideMapScreen(this));
    }

    @Override
    public void render() {
        super.render(); //important!
    }

    @Override
    public void dispose() {

    }
}
