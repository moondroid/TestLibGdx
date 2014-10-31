package com.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

/**
 * Created by marco.granatiero on 31/10/2014.
 */
public class ImageScreen implements Screen {

    final Game game;

    Skin skin;
    Stage ui;
    Table root;
    TextureRegion image2;

    public ImageScreen(Game game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ui.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        ui.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("images_test/uiskin.json"));
        image2 = new TextureRegion(new Texture(Gdx.files.internal("images_test/badlogic.jpg")));
        ui = new Stage();
        Gdx.input.setInputProcessor(ui);

        root = new Table();
        root.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ui.addActor(root);
        root.debug();

        Image image = new Image(image2);
        image.setScaling(Scaling.fill);
        root.add(image).width(image2.getRegionWidth()).height(image2.getRegionHeight());
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
        ui.dispose();
        skin.dispose();
        image2.getTexture().dispose();
    }
}
