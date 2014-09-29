package com.mygdx.game.airhockey;

import com.badlogic.gdx.Game;

public class AirHockey extends Game {
    @Override
    public void create() {
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render(); //important!
    }

    @Override
    public void dispose() {

    }
}
