package com.mygdx.game.pong;

/**
 * Created by marco.granatiero on 26/09/2014.
 */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Help2Screen implements Screen {
    OrthographicCamera guiCam;
    SpriteBatch batcher;
    Rectangle backBounds;
    Vector3 touchPoint;

    final Game game;

    public Help2Screen(Game game){

        this.game = game;

        guiCam = new OrthographicCamera(480,320);
        guiCam.position.set(480 / 2, 320 / 2, 0);
        batcher = new SpriteBatch();
        backBounds = new Rectangle(350, 10, 121, 19);
        touchPoint = new Vector3();
    }



    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)){
                Assets.playSound(Assets.bounceSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        batcher.setProjectionMatrix(guiCam.combined);

        batcher.disableBlending();
        batcher.begin();
        batcher.draw(Assets.help2ScreenRegion, 0, 0, 480, 320);
        batcher.end();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
