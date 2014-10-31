package com.example;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class ImagesGame extends Game {
    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        this.setScreen(new ImageScreen(this));
    }

    @Override
    public void render() {
        super.render(); //important!
    }

    @Override
    public void dispose() {

    }
}
