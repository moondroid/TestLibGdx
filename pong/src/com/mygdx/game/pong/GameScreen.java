package com.mygdx.game.pong;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by Marco on 26/09/2014.
 */
public class GameScreen implements Screen, InputProcessor {

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_WON = 2;

    int state;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
    SpriteBatch batcher;
    World world;
    World.WorldListener worldListener;
    WorldRenderer renderer;
    int player1Score;
    int player2Score;

    FPSLogger fpslogger;

    final Game game;

    public GameScreen(Game game){

        this.game = game;

        state = GAME_READY;
        guiCam = new OrthographicCamera(480,320);
        guiCam.position.set(480 / 2, 320 / 2, 0);
        touchPoint = new Vector3();
        batcher = new SpriteBatch();
        worldListener = new World.WorldListener(){
            @Override
            public void bump() {
                Assets.playSound(Assets.bounceSound);
            }
        };
        world = new World(worldListener);
        renderer = new WorldRenderer(batcher, world);
        player1Score = 0;
        player2Score = 0;

        fpslogger = new FPSLogger();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL20.GL_TEXTURE_2D);

        renderer.render();

        guiCam.update();
        batcher.setProjectionMatrix(guiCam.combined);
        batcher.enableBlending();
        batcher.begin();

        if (delta > 0.1f) delta = 0.1f;

        switch (state){
            case GAME_READY:
                presentReady();
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(delta);
                presentRunning();
                break;
            case GAME_WON:
                presentWon();
                updateWon();
                break;
        }
        batcher.end();

    }


    public void presentReady(){
        //waits for a click or touchscreen, doesn't render anything
    }

    public void presentRunning(){
        Assets.font.draw(batcher, String.valueOf(player1Score), 36, 300);
        Assets.font.draw(batcher, String.valueOf(player2Score), 420, 300);
    }

    public void presentWon(){
    }


    public void updateReady(){
        if(Gdx.input.justTouched()){
            state = GAME_RUNNING;
        }
    }

    private void updateRunning (float deltaTime){
        if (Gdx.app.getType() == Application.ApplicationType.Android){

            world.update(deltaTime, 0, 0);
        }

        if (world.scoreP1 != player1Score || world.scoreP2 != player2Score){
            player1Score = world.scoreP1;
            player2Score = world.scoreP2;
        }

        if (world.scoreP1 >= 10 || world.scoreP2 >= 10){
            state = GAME_WON;
        }

        fpslogger.log();
    }

    private void updateWon(){
        game.setScreen(new MainMenuScreen(game));
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

    /*
    * INPUTPROCESSOR INTERFACE
    */

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < 480 / 2){
            world.paddleP1.position.y = 32 - screenY / 10;
        }
        if(screenX > 480 / 2){
            world.paddleP2.position.y = 32 - screenY / 10;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(screenX < 480 / 2){
            world.paddleP1.position.y = 32 - screenY / 10;
        }
        if(screenX > 480 / 2){
            world.paddleP2.position.y = 32 - screenY / 10;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
