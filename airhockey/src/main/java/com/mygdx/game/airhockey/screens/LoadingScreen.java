package com.mygdx.game.airhockey.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.airhockey.Assets;

/**
 * Created by Marco on 01/11/2014.
 */
public class LoadingScreen implements Screen {

    final Game game;

    public LoadingScreen(Game game) {
        this.game = game;

        Assets.manager.clear();
        Assets.loadPictures();
        Assets.loadTextureAtlas();

    }

    @Override
    public void render(float delta) {

        /* Clear screen with a black background */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Assets.manager.update()){
            Assets.post_load();
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
