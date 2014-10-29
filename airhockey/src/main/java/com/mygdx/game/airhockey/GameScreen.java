package com.mygdx.game.airhockey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

import static com.mygdx.game.airhockey.Box2DFactory.*;

/**
 * Created by Marco on 29/09/2014.
 */
public class GameScreen extends InputAdapter implements Screen, ContactListener {

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

    private Body halfLine;
    private Body leftLine;
    private Body leftUpLine;
    private Body rightLine;
    private Body rightUpLine;

    private Body rightGoalLine;
    private Body leftGoalLine;

    private Body goalLineDown;
    private Body goalLineUp;

    private Body invisibleWalls;


    private Body ball;
    private Body player;
    private Body computer;

    /* Use a long to store current time and calculate user touches duration */
    private Long timer;
    private Vector3 touchPosition = new Vector3();

    /*
     * Used to define a mouse joint for a body. This point will track a
	 * specified world point.
	 */
    private MouseJoint mouseJoint;
    private MouseJointDef mouseJointDef;

    private GameState state = GameState.PLAY;

    private enum GameState {
        PLAY, BALL_REPOSITION_PLAYER, BALL_REPOSITION_COMPUTER
    }

    private BitmapFont font;
    private int computerScore = 0;
    private int playerScore = 0;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {

        /* Clear screen with a black background */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Render all graphics before do physics step */
        debugRenderer.render(world, camera.combined);

        if (state == GameState.BALL_REPOSITION_PLAYER || state == GameState.BALL_REPOSITION_COMPUTER) {

            destroyJoint();
            world.destroyBody(ball);
            world.destroyBody(player);
            world.destroyBody(computer);

            BallPosition position = state == GameState.BALL_REPOSITION_PLAYER ? BallPosition.PLAYER : BallPosition.COMPUTER;
            ball = Box2DFactory.createBall(this.world, position);
            player = Box2DFactory.createPaddle(this.world, new Vector2(0.0f, -Utils.getHalfHeight() / 2.0f));
            computer = Box2DFactory.createPaddle(this.world, new Vector2(0.0f, Utils.getHalfHeight() / 2.0f));

            state = GameState.PLAY;
        }

        batch.begin();
        font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 15f, font.getCapHeight() + 20f);
        font.draw(batch, String.valueOf(computerScore), Utils.getRealWidth() - 60f, Utils.getRealHeight() / 2.0f + 20.0f + font.getCapHeight());
        font.draw(batch, String.valueOf(playerScore), Utils.getRealWidth() - 60f, Utils.getRealHeight() / 2.0f - 20.0f);
        batch.end();

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
        world.setContactListener(this);

        this.camera = new OrthographicCamera(Utils.getWidth(), Utils.getHeight());

        this.cornerLineLeftUp = Box2DFactory.createCornerLineLeftUp(this.world);
        this.cornerLineLeftDown = Box2DFactory.createCornerLineLeftDown(this.world);
        this.cornerLineRightUp = Box2DFactory.createCornerLineRightUp(this.world);
        this.cornerLineRightDown = Box2DFactory.createCornerLineRightDown(this.world);

        this.leftLine = Box2DFactory.createLeftLine(this.world);
        this.rightLine = Box2DFactory.createRightLine(this.world);

        this.rightUpLine = Box2DFactory.createLineRightUp(this.world);
        this.leftUpLine = Box2DFactory.createLineLeftUp(this.world);

        this.rightGoalLine = Box2DFactory.createLineRightDown(this.world);
        this.leftGoalLine = Box2DFactory.createLineLeftDown(this.world);

        this.halfLine = Box2DFactory.createHalfLine(this.world);

        this.goalLineUp = Box2DFactory.createGoalLineUp(this.world);
        this.goalLineDown = Box2DFactory.createGoalLineDown(this.world);

        this.invisibleWalls = Box2DFactory.createInvisibleWalls(this.world);


        this.ball = Box2DFactory.createBall(this.world, BallPosition.CENTER);

        this.player = Box2DFactory.createPaddle(this.world, new Vector2(0.0f, -Utils.getHalfHeight() / 2.0f));
        this.computer = Box2DFactory.createPaddle(this.world, new Vector2(0.0f, Utils.getHalfHeight() / 2.0f));

        /* Define the mouse joint. We use walls as the first body of the joint */
        createMouseJointDefinition(halfLine);

        //font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
        font = new BitmapFont();
        font.setScale(2.0f);
        font.setColor(Color.WHITE);
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
        font.dispose();
        world.dispose();
    }


    /*
    * InputAdapter INTERFACE
    */

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.debug("GameScreen", "touchDown " + state.name());

        if (state == GameState.PLAY) {

            // translate the mouse coordinates to world coordinates
            touchPosition.set(screenX, screenY, 0);
            camera.unproject(touchPosition);
        /*
         * Define a new QueryCallback. This callback will be used in
		 * world.QueryAABB method.
		 */
            QueryCallback queryCallback = new QueryCallback() {

                @Override
                public boolean reportFixture(Fixture fixture) {
                    boolean testResult;

				/*
				 * If the hit point is inside the fixture of the body, create a
				 * new MouseJoint.
				 */
                    if (testResult = fixture.testPoint(touchPosition.x,
                            touchPosition.y) && (fixture.getBody() == player || fixture.getBody() == computer)) {
                        mouseJointDef.bodyB = fixture.getBody();
                        mouseJointDef.target.set(touchPosition.x, touchPosition.y);
                        mouseJointDef.maxForce = 10000.0f * fixture.getBody().getMass();
                        mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);
                    }

                    return testResult;
                }
            };

            world.QueryAABB(queryCallback,
                    touchPosition.x - 0.1f, touchPosition.y - 0.1f, touchPosition.x + 0.1f, touchPosition.y + 0.1f);
        }


        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        /* Whether the input was processed */
        boolean processed = false;

        if (state == GameState.PLAY) {
            /*
		    * If a MouseJoint is defined, update its target with current position.
		    */
            if (mouseJoint != null) {

			/* Translate camera point to world point */
                camera.unproject(touchPosition.set(screenX, screenY, 0));
                mouseJoint.setTarget(new Vector2(touchPosition.x, touchPosition.y));

                processed = true;
            }
        }

        return processed;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.debug("GameScreen", "touchUp " + state.name());

        /* Whether the input was processed */
        boolean processed = false;

        if (state == GameState.PLAY) {
            /* If a MouseJoint is defined, destroy it */
            if (mouseJoint != null) {
                world.destroyJoint(mouseJoint);
                mouseJoint = null;

                processed = true;
            }
        }


        return processed;
    }


    private void createMouseJointDefinition(Body body) {
        mouseJointDef = new MouseJointDef();
        mouseJointDef.bodyA = body;
        mouseJointDef.collideConnected = true;
        mouseJointDef.frequencyHz = 100;
        mouseJointDef.dampingRatio = 0.0f;
    }

    private synchronized void destroyJoint() {
        if (mouseJoint != null) {
            world.destroyJoint(mouseJoint);
            mouseJoint = null;
        }
    }


    /*
    * CONTACTLISTENER INTERFACE
    */

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(a == ball || b == ball){
            if (a == goalLineUp || b == goalLineUp) {
                Gdx.app.debug("GameScreen.beginContact", "goalLineUp");
                state = GameState.BALL_REPOSITION_COMPUTER;
                playerScore++;
            }

            if (a == goalLineDown || b == goalLineDown) {
                Gdx.app.debug("GameScreen.beginContact", "goalLineDown");
                state = GameState.BALL_REPOSITION_PLAYER;
                computerScore++;
            }
        }


    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
