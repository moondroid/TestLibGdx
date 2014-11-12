package com.mygdx.game.airhockey.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.airhockey.Constants;

/**
 * Created by Marco on 10/11/2014.
 */
public class MainMenuScreen implements Screen {

    final Game game;
    private Stage stage;
    private Table table;
    // For debug drawing
    private ShapeRenderer shapeRenderer;

    private Skin skin;
    private Screen nextScreen;

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        table.drawDebug(shapeRenderer); // This is optional, but enables debug lines for tables.
    }

    @Override
    public void resize(int width, int height) {
        //stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Constants.WIDTH/Constants.SCALE, Constants.HEIGHT/Constants.SCALE)); //480*800
        Gdx.input.setInputProcessor(stage);

        //setupSkin();
        skin = new Skin(Gdx.files.internal("airhockey/uiskin.json"));

        table = new Table();
        table.setFillParent(true);


        Label titleLabel = new Label("AIR HOCKEY", skin);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        TextButton onePlayerButton = new TextButton("1 PLAYER", skin);
        onePlayerButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                nextScreen = new GameScreen(game, GameScreen.PlayMode.DOUBLE_PLAYER);
//                Gdx.input.setInputProcessor(null);
//                setOutroTween();
                game.setScreen(nextScreen);
            }
        });

        TextButton twoPlayerButton = new TextButton("2 PLAYERS", skin);
        twoPlayerButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                nextScreen = new GameScreen(game, GameScreen.PlayMode.SINGLE_PLAYER);
//                Gdx.input.setInputProcessor(null);
//                setOutroTween();
                game.setScreen(nextScreen);
            }
        });

        TextButton settingsButton = new TextButton("SETTINGS", skin);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
//                nextScreen = new SettingsScreen(game);
//                Gdx.input.setInputProcessor(null);
//                setOutroTween();
            }
        });

        TextButton aboutButton = new TextButton("ABOUT", skin);
        aboutButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
//                nextScreen = new CreditsScreen(game);
//                Gdx.input.setInputProcessor(null);
//                setOutroTween();
            }
        });

        table.add(titleLabel).pad(30);
        table.row();
        table.add(onePlayerButton).width(300).height(75);
        table.row();
        table.add(twoPlayerButton).width(300).height(75).padBottom(30);
        table.row();
        table.add(settingsButton).width(300).height(75);
        table.row();
        table.add(aboutButton).width(300).height(75);

        stage.addActor(table);

        shapeRenderer = new ShapeRenderer();

        // TODO Add widgets to the table here.
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
        stage.dispose();
        shapeRenderer.dispose();
    }

    private void setupSkin(){
        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        skin.add("default", new BitmapFont());

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);
    }


}
