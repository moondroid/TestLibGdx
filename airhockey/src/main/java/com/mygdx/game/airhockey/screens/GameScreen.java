package com.mygdx.game.airhockey.screens;

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
import com.mygdx.game.airhockey.Assets;
import com.mygdx.game.airhockey.Box2DFactory;
import com.mygdx.game.airhockey.ComputerAI;
import com.mygdx.game.airhockey.Constants;
import com.mygdx.game.airhockey.SoundManager;
import com.mygdx.game.airhockey.Utils;

import static com.mygdx.game.airhockey.Box2DFactory.*;

/**
 * Created by Marco on 29/09/2014.
 */
public class GameScreen extends InputAdapter implements Screen, ContactListener {

    final Game game;
    private boolean enableCpu;

    /* Use Box2DDebugRenderer, which is a model renderer for debug purposes */
    private Box2DDebugRenderer debugRenderer;

    private SpriteBatch batch;
    private SpriteBatch fontBatch;
    private OrthographicCamera camera;
    private OrthographicCamera fontCamera;
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
    private Body player1;
    private Body player2;

    /* Use a long to store current time and calculate user touches duration */
    private Long timer;

    private Vector3 touchPositionPlayer1 = new Vector3();
    private int pointerPlayer1;
    private MouseJoint mouseJointPlayer1;
    private MouseJointDef mouseJointDefPlayer1;

    private Vector3 touchPositionPlayer2 = new Vector3();
    private int pointerPlayer2;
    private MouseJoint mouseJointPlayer2;
    private MouseJointDef mouseJointDefPlayer2;

    private GameState state = GameState.PLAY;

    private enum GameState {
        PLAY, BALL_REPOSITION_PLAYER1, BALL_REPOSITION_PLAYER2
    }

    public enum PlayMode {
        SINGLE_PLAYER, DOUBLE_PLAYER
    }
    private BitmapFont font;
    private int player2Score = 0;
    private int player1Score = 0;

    private ComputerAI computerAI;

    public GameScreen(Game game, PlayMode mode) {
        this.game = game;
        if (mode == PlayMode.SINGLE_PLAYER){
            enableCpu = true;
        }else {
            enableCpu = false;
        }
    }

    @Override
    public void render(float delta) {

        /* Clear screen with a black background */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (state == GameState.BALL_REPOSITION_PLAYER1 || state == GameState.BALL_REPOSITION_PLAYER2) {

            destroyJoints();
            world.destroyBody(ball);
            world.destroyBody(player1);
            world.destroyBody(player2);

            BallPosition position = state == GameState.BALL_REPOSITION_PLAYER1 ? BallPosition.PLAYER : BallPosition.COMPUTER;
            ball = Box2DFactory.createBall(this.world, position);
            player1 = Box2DFactory.createPaddle(this.world, new Vector2(0.0f, -Constants.PLAYER_REPOSITION_OFFSET));
            player2 = Box2DFactory.createPaddle(this.world, new Vector2(0.0f, Constants.PLAYER_REPOSITION_OFFSET));

            computerAI = new ComputerAI(player2, ball, enableCpu);

            state = GameState.PLAY;
        }

        /* Render background */
        batch.enableBlending();
        batch.begin();
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 0.4f);//set alpha to 0.4
        batch.draw(Assets.playfields[Assets.playfieldRegion], -Utils.getHalfWidth(), -Utils.getHalfHeight(), Utils.getWidth(), Utils.getHeight());
        batch.setColor(c.r, c.g, c.b, 1f); //set alpha to 1
        batch.end();

        /* Render ball and paddles */
        batch.begin();
        Vector2 ballPosition = ball.getPosition();
        Vector2 playerPosition = player1.getPosition();
        Vector2 computerPosition = player2.getPosition();
        batch.draw(Assets.ballregions[Assets.ballRegion], ballPosition.x - Constants.BALL_RADIUS, ballPosition.y
                - Constants.BALL_RADIUS, Constants.BALL_RADIUS * 2.0f, Constants.BALL_RADIUS * 2.0f);

        batch.draw(Assets.paddleRegions[Assets.paddleRegion0], playerPosition.x - Constants.PADDLE_RADIUS, playerPosition.y
                - Constants.PADDLE_RADIUS, Constants.PADDLE_RADIUS * 2.0f, Constants.PADDLE_RADIUS * 2.0f);
        batch.draw(Assets.paddleRegions[Assets.paddleRegion1], computerPosition.x - Constants.PADDLE_RADIUS, computerPosition.y
                - Constants.PADDLE_RADIUS, Constants.PADDLE_RADIUS * 2.0f, Constants.PADDLE_RADIUS * 2.0f);
        batch.end();


        /* Render all graphics before do physics step */
        debugRenderer.render(world, camera.combined);

        //draw fps and scores
        fontBatch.enableBlending();
        fontBatch.begin();
        font.setScale(1.0f);
        font.draw(fontBatch, "fps: " + Gdx.graphics.getFramesPerSecond(), -Utils.getHalfPlayfieldWidth() / Constants.SCALE + 15f, -Utils.getHalfPlayfieldHeight() / Constants.SCALE + font.getCapHeight());
        font.setScale(2.0f);
        font.draw(fontBatch, String.valueOf(player2Score), Utils.getHalfPlayfieldWidth() / Constants.SCALE - 60f, font.getCapHeight() + 20f);
        font.draw(fontBatch, String.valueOf(player1Score), Utils.getHalfPlayfieldWidth() / Constants.SCALE - 60f, -20.0f);
        fontBatch.end();

        //computerAI.updateAutoplayer(delta);

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
        this.fontBatch = new SpriteBatch();
        this.world = new World(new Vector2(0.0f, 0.0f), true);
        world.setContactListener(this);

        this.camera = new OrthographicCamera(Utils.getWidth(), Utils.getHeight());
        this.fontCamera = new OrthographicCamera(Utils.getWidth() / Constants.SCALE, Utils.getHeight() / Constants.SCALE);
        batch.setProjectionMatrix(camera.combined);
        fontBatch.setProjectionMatrix(fontCamera.combined);

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

        this.player1 = Box2DFactory.createPaddle(this.world, new Vector2(0.0f, -Constants.PLAYER_REPOSITION_OFFSET));
        this.player2 = Box2DFactory.createPaddle(this.world, new Vector2(0.0f, Constants.PLAYER_REPOSITION_OFFSET));

        /* Define the mouse joint. We use walls as the first body of the joint */
        mouseJointDefPlayer1 = createMouseJointDefinition(halfLine);
        mouseJointDefPlayer2 = createMouseJointDefinition(halfLine);


        //font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        computerAI = new ComputerAI(player2, ball, enableCpu);
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
    public boolean touchDown(int screenX, int screenY, final int pointer, int button) {
        Gdx.app.debug("GameScreen", "touchDown pointer: " + pointer);

        if (state == GameState.PLAY) {

            // translate the mouse coordinates to world coordinates
            final Vector3 touchPosition = new Vector3();
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

                    if (fixture.testPoint(touchPosition.x,
                            touchPosition.y) && (fixture.getBody() == player1)) {
                        touchPositionPlayer1 = touchPosition;
                        pointerPlayer1 = pointer;
                        mouseJointDefPlayer1.bodyB = fixture.getBody();
                        mouseJointDefPlayer1.target.set(touchPositionPlayer1.x, touchPositionPlayer1.y);
                        mouseJointDefPlayer1.maxForce = 200000.0f * fixture.getBody().getMass();
                        mouseJointPlayer1 = (MouseJoint) world.createJoint(mouseJointDefPlayer1);
                        return true;
                    }

                    if (!enableCpu && fixture.testPoint(touchPosition.x,
                            touchPosition.y) && (fixture.getBody() == player2)) {
                        touchPositionPlayer2 = touchPosition;
                        pointerPlayer2 = pointer;
                        mouseJointDefPlayer2.bodyB = fixture.getBody();
                        mouseJointDefPlayer2.target.set(touchPositionPlayer2.x, touchPositionPlayer2.y);
                        mouseJointDefPlayer2.maxForce = 200000.0f * fixture.getBody().getMass();
                        mouseJointPlayer2 = (MouseJoint) world.createJoint(mouseJointDefPlayer2);
                        return true;
                    }

                    return false;
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
            if (mouseJointPlayer1 != null && pointer == pointerPlayer1) {

			/* Translate camera point to world point */
                camera.unproject(touchPositionPlayer1.set(screenX, screenY, 0));
                mouseJointPlayer1.setTarget(new Vector2(touchPositionPlayer1.x, touchPositionPlayer1.y));

                processed = true;
            }

            if (mouseJointPlayer2 != null && pointer == pointerPlayer2) {

			/* Translate camera point to world point */
                camera.unproject(touchPositionPlayer2.set(screenX, screenY, 0));
                mouseJointPlayer2.setTarget(new Vector2(touchPositionPlayer2.x, touchPositionPlayer2.y));

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
            if (mouseJointPlayer1 != null && pointer == pointerPlayer1) {
                world.destroyJoint(mouseJointPlayer1);
                mouseJointPlayer1 = null;

                processed = true;
            }

            if (mouseJointPlayer2 != null && pointer == pointerPlayer2) {
                world.destroyJoint(mouseJointPlayer2);
                mouseJointPlayer2 = null;

                processed = true;
            }
        }


        return processed;
    }


    private MouseJointDef createMouseJointDefinition(Body body) {
        MouseJointDef jointDef;
        jointDef = new MouseJointDef();
        jointDef.bodyA = body;
        jointDef.collideConnected = true;
        jointDef.frequencyHz = 100;
        jointDef.dampingRatio = 0.0f;
        return jointDef;
    }

    private synchronized void destroyJoints() {
        if (mouseJointPlayer1 != null) {
            world.destroyJoint(mouseJointPlayer1);
            mouseJointPlayer1 = null;
        }
        if (mouseJointPlayer2 != null) {
            world.destroyJoint(mouseJointPlayer2);
            mouseJointPlayer2 = null;
        }
    }


    /*
    * CONTACTLISTENER INTERFACE
    */

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();


        if (a == ball || b == ball) {
            if (a == goalLineUp || b == goalLineUp) {
                Gdx.app.debug("GameScreen.beginContact", "goalLineUp");
                state = GameState.BALL_REPOSITION_PLAYER2;
                player1Score++;
                SoundManager.playSound(SoundManager.goalSound);
            }

            if (a == goalLineDown || b == goalLineDown) {
                Gdx.app.debug("GameScreen.beginContact", "goalLineDown");
                state = GameState.BALL_REPOSITION_PLAYER1;
                player2Score++;
                SoundManager.playSound(SoundManager.goalSound);
            }

            if(a == player1 || b == player1 || a == player2 || b == player2){
                SoundManager.playSound(SoundManager.ballPaddleSound);
            }

            Short GroupA = a.getUserData() != null? (Short)a.getUserData() : Box2DFactory.GROUP_NONE;
            Short GroupB = b.getUserData() != null? (Short)b.getUserData() : Box2DFactory.GROUP_NONE;

            if(GroupA == Box2DFactory.GROUP_SCENERY || GroupB == Box2DFactory.GROUP_SCENERY){
                SoundManager.playSound(SoundManager.ballPlaygroundSound);
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
