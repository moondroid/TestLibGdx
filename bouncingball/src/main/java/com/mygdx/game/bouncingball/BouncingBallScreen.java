package com.mygdx.game.bouncingball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by marco.granatiero on 14/10/2014.
 */
public class BouncingBallScreen implements Screen, InputProcessor {

    final Game game;

    /* As always, we need a camera to be able to see the objects */
    private OrthographicCamera camera;

    /* Define a world to hold all bodies and simulate reactions between them */
    private World world;

    /* Use Box2DDebugRenderer, which is a model renderer for debug purposes */
    private Box2DDebugRenderer debugRenderer;

    /* Use a long to store current time and calculate user touches duration */
    private Long timer;
    private Vector2 touchDownVector;
    private Vector3 testPoint = new Vector3();

    /* Define a body to later apply impulses to it */
    private Body ball;

    /**
     * a hit body *
     */
    Body hitBody = null;

    public BouncingBallScreen(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);
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

        Gdx.app.debug("BouncingBallScreen", "show()");

        /*
         * Create world with a common gravity vector (9.81 m/s2 downwards force)
		 * and tell world that we want objects to sleep. This last value
		 * conserves CPU usage.
		 */
        world = new World(new Vector2(0, -9.81f), true);

        		/*
		 * Define camera viewport. Box2D uses meters internally so the camera
		 * must be defined also in meters. We set a desired width and adjust
		 * height to different resolutions.
		 */
        camera = new OrthographicCamera(20,
                20 * (Gdx.graphics.getHeight() / (float) Gdx.graphics
                        .getWidth()));


        /* Create renderer */
        debugRenderer = new Box2DDebugRenderer();

		/* Create the ball */
        Shape shape = Box2DFactory.createCircleShape(1.0f);
        FixtureDef fixtureDef = Box2DFactory.createFixture(shape, 0.2f, 0.25f, 0.7f, false);
        ball = Box2DFactory.createBody(world, BodyDef.BodyType.DynamicBody, fixtureDef, new Vector2(6, 5));

		/* Create the ramp */
        Vector2[] rampVertices = new Vector2[]{new Vector2(-2.5f, -1), new Vector2(2.5f, 1), new Vector2(2.5f, -1), new Vector2(-2.5f, -1)};
        shape = Box2DFactory.createChainShape(rampVertices);
        fixtureDef = Box2DFactory.createFixture(shape, 0, 0.3f, 0.3f, false);
        Box2DFactory.createBody(world, BodyDef.BodyType.StaticBody, fixtureDef, new Vector2(6.5f, 0));

		/* Create the walls */
        Box2DFactory.createWalls(world, camera.viewportWidth, camera.viewportHeight, 1);
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
    * INPUTPROCESSOR
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

        Gdx.app.debug("BouncingBallScreen", "touchDown");
        /*
		 * Get current time in milliseconds. We will use this to calculate the
		 * time the user has touched the screen.
		 */
        this.timer = System.currentTimeMillis();
        this.touchDownVector = new Vector2(screenX, -screenY);
        //Gdx.app.debug("BouncingBallScreen", "touchDown: "+screenX+", "+screenY);

        // translate the mouse coordinates to world coordinates
        testPoint.set(screenX, screenY, 0);
        camera.unproject(testPoint);

        hitBody = null;
        world.QueryAABB(new QueryCallback() {

                            @Override
                            public boolean reportFixture(Fixture fixture) {

                                if (fixture.getBody() == ball) {
                                    hitBody = fixture.getBody();
                                    return false;
                                } else {
                                    return true;
                                }
                                // if the hit point is inside the fixture of the body
                                // we report it
//                                if (fixture.testPoint(testPoint.x, testPoint.y)) {
//                                    hitBody = fixture.getBody();
//                                    return false;
//                                } else {
//                                    hitBody = null;
//                                    return true;
//                                }
                            }
                        },

                testPoint.x - 0.1f, testPoint.y - 0.1f, testPoint.x + 0.1f, testPoint.y + 0.1f);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        /* Calculate the time the user has touched the screen */
        long touchedTime = System.currentTimeMillis() - timer;

        Vector2 touchMoveVector = (new Vector2(screenX, -screenY)).sub(touchDownVector).nor();
        //Vector2 touchMoveVector = (touchDownVector).sub(new Vector2(screenX, screenY)).limit(10f);

		/* Every second touching the screen will increment by 20 the impulse */
        float impulse = Math.max(10f, touchedTime != 0 ? 3000 / touchedTime : 10f); //ball.getMass()


        Gdx.app.debug("BouncingBallScreen", "touchUp: " + impulse);

		/*
		 * The impulse is applied to the body's center, and only on the Y axis.
		 * It will aggregate the impulse to the current Y speed, so, if the
		 * object is falling the impulse will be the difference between the
		 * object speed and the given value.
		 */
//        ball.applyLinearImpulse(new Vector2(0, impulse), ball.getWorldCenter(),
//                true);

        if(hitBody!=null){
            ball.applyLinearImpulse(touchMoveVector.scl(impulse), ball.getWorldCenter(), true);
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
