package com.mygdx.game.airhockey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Marco on 29/09/2014.
 */
public class GameScreen extends InputAdapter implements Screen {

    final Game game;
    /* Use Box2DDebugRenderer, which is a model renderer for debug purposes */
    private Box2DDebugRenderer debugRenderer;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private World world;

    private Body cornerLineLeftDown;
    private Body cornerLineLeftUp;
    private Body cornerLineRightDown;
    private Body cornerLineRightUp;


    public GameScreen(Game game){
        this.game = game;
    }

    @Override
    public void render(float delta) {

        /* Clear screen with a black background */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Render all graphics before do physics step */
        debugRenderer.render(world, camera.combined);

		/* Step the simulation with a fixed time step of 1/60 of a second */
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        /* Create renderer */
        debugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(this);

        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0.0f, 0.0f), true);
        this.camera = new OrthographicCamera(Utils.getWidth(), Utils.getHeight());

        this.cornerLineLeftUp = Box2DFactory.createCornerLineLeftUp(this.world);
        this.cornerLineLeftDown = Box2DFactory.createCornerLineLeftDown(this.world);
        this.cornerLineRightUp = Box2DFactory.createCornerLineRightUp(this.world);
        this.cornerLineRightDown = Box2DFactory.createCornerLineRightDown(this.world);

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
        debugRenderer.dispose();
        world.dispose();
    }


    /*
    * InputAdapter INTERFACE
    */

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

}
