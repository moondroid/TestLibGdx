package com.mygdx.game.airhockey;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.airhockey.screens.LoadingScreen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class AirHockey extends Game {

    public TweenManager tweenManager;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setupTweenManager();
        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        super.render(); //important!
    }

    @Override
    public void dispose() {

    }

    private void setupTweenManager() {
        tweenManager = new TweenManager();
        Tween.registerAccessor(Camera.class, new CameraAccessor());
//        Tween.registerAccessor(Table.class, new TableAccessor());
//        Tween.registerAccessor(Paddle.class, new PaddleAccessor());
    }
}
