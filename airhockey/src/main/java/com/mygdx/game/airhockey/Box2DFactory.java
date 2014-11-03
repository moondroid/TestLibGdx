package com.mygdx.game.airhockey;

/**
 * Created by Marco on 30/09/2014.
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Box2DFactory {

    public final static short GROUP_NONE = 0;
    public final static short GROUP_PADDLE = 1;//positive = always collide between each other
    public final static short GROUP_BALL = -2;//negative = never collide between each other
    public final static short GROUP_GOALS = -3;
    public final static short GROUP_SCENERY = -4;

    final static float FRICTION_BALL = 0.2f;
    final static float FRICTION_SCENERY = 0.1f;
    final static float FRICTION_PADDLES = 0.3f;

    public enum BallPosition {
        CENTER, PLAYER, COMPUTER
    }

    public static Body createBody(World world, BodyType bodyType, FixtureDef fixtureDef, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position);
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        fixtureDef.shape.dispose();
        return body;
    }

    public static Body createBall(World world, BallPosition position){
        Shape entityShape = Box2DFactory.createCircleShape(Constants.BALL_RADIUS);
        float restitutionBALL = 0.9f;
        FixtureDef entityFixture = Box2DFactory.createFixture(entityShape, 0.1f, FRICTION_BALL, restitutionBALL, false);
        entityFixture.filter.groupIndex = GROUP_BALL;
        Vector2 startPosition;
        switch (position){
            case PLAYER:
                startPosition = new Vector2(0f, -Constants.BALL_REPOSITION_OFFSET);
                break;
            case COMPUTER:
                startPosition = new Vector2(0f, Constants.BALL_REPOSITION_OFFSET);
                break;
            default:
                startPosition = new Vector2(0f, 0f);

        }
        return Box2DFactory.createBody(world, BodyType.DynamicBody, entityFixture, startPosition);
    }

    public static Body createPaddle(World world, Vector2 position){
        Shape entityShape = Box2DFactory.createCircleShape(Constants.PADDLE_RADIUS);
        float restitution = 0.1f;
        FixtureDef entityFixture = Box2DFactory.createFixture(entityShape, 1.0f, FRICTION_PADDLES, restitution, false);
        entityFixture.filter.groupIndex = GROUP_PADDLE;
        Body body = Box2DFactory.createBody(world, BodyType.DynamicBody, entityFixture, position);
        body.setFixedRotation(true);
        return body;
    }

    public static Shape createBoxShape(Vector2 center, float angle) {
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(Utils.getHalfPlayfieldWidth(), Utils.getHalfPlayfieldHeight(), center, angle);
        return boxShape;
    }

    public static Shape createChainShape(Vector2[] vertices) {
        ChainShape chainShape = new ChainShape();
        chainShape.createChain(vertices);
        return chainShape;
    }

    public static Shape createCircleShape(float radius) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        return circleShape;
    }

    public static Body createCornerLineLeftDown(World world) {
        Vector2[] cornerLineLeftDown = new Vector2[2];
        cornerLineLeftDown[0] = new Vector2(-Utils.getHalfPlayfieldWidth()+Constants.SCENERY_CORNER_RADIUS, - Utils.getGoalLineHeight());
        cornerLineLeftDown[1] = new Vector2(- Utils.getHalfPlayfieldWidth(), - Utils.getGoalLineHeight() + Constants.SCENERY_CORNER_RADIUS);
        FixtureDef fixtureDef = createFixture(createChainShape(cornerLineLeftDown), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
    }

    public static Body createCornerLineLeftUp(World world) {
        Vector2[] cornerLineLeftUp = new Vector2[2];
        cornerLineLeftUp[0] = new Vector2(-Utils.getHalfPlayfieldWidth()+Constants.SCENERY_CORNER_RADIUS, Utils.getGoalLineHeight());
        cornerLineLeftUp[1] = new Vector2(-Utils.getHalfPlayfieldWidth(), Utils.getGoalLineHeight() - Constants.SCENERY_CORNER_RADIUS);
        FixtureDef fixtureDef = createFixture(createChainShape(cornerLineLeftUp), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
    }

    public static Body createCornerLineRightDown(World world) {
        Vector2[] cornerLineRightDown = new Vector2[2];
        cornerLineRightDown[0] = new Vector2(Utils.getHalfPlayfieldWidth(), -Utils.getGoalLineHeight()+Constants.SCENERY_CORNER_RADIUS);
        cornerLineRightDown[1] = new Vector2(Utils.getHalfPlayfieldWidth() -Constants.SCENERY_CORNER_RADIUS, - Utils.getGoalLineHeight());
        FixtureDef fixtureDef = createFixture(createChainShape(cornerLineRightDown), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
    }

    public static Body createCornerLineRightUp(World world) {
        Vector2[] cornerLineRightUp = new Vector2[2];
        cornerLineRightUp[0] = new Vector2(Utils.getHalfPlayfieldWidth()-Constants.SCENERY_CORNER_RADIUS, Utils.getGoalLineHeight());
        cornerLineRightUp[1] = new Vector2(Utils.getHalfPlayfieldWidth(), Utils.getGoalLineHeight() - Constants.SCENERY_CORNER_RADIUS);
        FixtureDef fixtureDef = createFixture(createChainShape(cornerLineRightUp), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
    }

    public static FixtureDef createFixture(Shape shape, float density, float friction, float restitution, boolean isSensor) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        return fixtureDef;
    }

    public static Body createGoalLineUp(World world) {
        Vector2[] goalLineUpVertices = new Vector2[2];

        goalLineUpVertices[0] = new Vector2(-Constants.GOAL_WIDTH/2.0f, Utils.getGoalLineHeight()+Constants.GOAL_OFFSET*0.9f);
        goalLineUpVertices[1] = new Vector2(Constants.GOAL_WIDTH/2.0f, Utils.getGoalLineHeight()+Constants.GOAL_OFFSET*0.9f);

        FixtureDef fixtureDef = createFixture(createChainShape(goalLineUpVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_GOALS;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
    }

    public static Body createGoalLineDown(World world) {
        Vector2[] goalLineDownVertices = new Vector2[2];

        goalLineDownVertices[0] = new Vector2(-Constants.GOAL_WIDTH/2.0f, -Utils.getGoalLineHeight()-Constants.GOAL_OFFSET*0.9f);
        goalLineDownVertices[1] = new Vector2(Constants.GOAL_WIDTH/2.0f, -Utils.getGoalLineHeight()-Constants.GOAL_OFFSET*0.9f);

        FixtureDef fixtureDef = createFixture(createChainShape(goalLineDownVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_GOALS;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
    }


    public static Body createLineLeftUp(World world) {
        Vector2[] UpLeftLineVertices = new Vector2[2];
        UpLeftLineVertices[0] = new Vector2(-Constants.GOAL_WIDTH/2.0f, Utils.getGoalLineHeight());
        UpLeftLineVertices[1] = new Vector2(-Utils.getHalfPlayfieldWidth(), Utils.getHalfHeight()-Constants.GOAL_OFFSET);
        FixtureDef fixtureDef = createFixture(createChainShape(UpLeftLineVertices), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        Body body = createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
        body.setUserData(GROUP_SCENERY);
        return body;
    }

    public static Body createLineRightUp(World world) {
        Vector2[] upRightLineVertices = new Vector2[2];
        upRightLineVertices[0] = new Vector2(Utils.getHalfPlayfieldWidth(), Utils.getGoalLineHeight());
        upRightLineVertices[1] = new Vector2(Constants.GOAL_WIDTH/2.0f, Utils.getHalfHeight()-Constants.GOAL_OFFSET);
        FixtureDef fixtureDef = createFixture(createChainShape(upRightLineVertices), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        Body body = createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
        body.setUserData(GROUP_SCENERY);
        return body;
    }

    public static Body createLineLeftDown(World world) {
        Vector2[] LeftLineVertices = new Vector2[2];
        LeftLineVertices[0] = new Vector2(-Constants.GOAL_WIDTH/2.0f, - Utils.getGoalLineHeight());
        LeftLineVertices[1] = new Vector2(- Utils.getHalfPlayfieldWidth(), - (Utils.getHalfHeight()-Constants.GOAL_OFFSET));
        FixtureDef fixtureDef = createFixture(createChainShape(LeftLineVertices), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        Body body = createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
        body.setUserData(GROUP_SCENERY);
        return body;
    }

    public static Body createLineRightDown(World world) {
        Vector2[] rightLineVertices = new Vector2[2];
        rightLineVertices[0] = new Vector2(Utils.getHalfPlayfieldWidth(), - Utils.getGoalLineHeight());
        rightLineVertices[1] = new Vector2(Constants.GOAL_WIDTH/2.0f, - (Utils.getHalfHeight()-Constants.GOAL_OFFSET));
        FixtureDef fixtureDef = createFixture(createChainShape(rightLineVertices), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        Body body = createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
        body.setUserData(GROUP_SCENERY);
        return body;
    }

    public static Body createInvisibleWalls(World world) {
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(-Utils.getHalfPlayfieldWidth(), -Utils.getHalfHeight());
        vertices[1] = new Vector2(Utils.getHalfPlayfieldWidth(), -Utils.getHalfHeight());
        vertices[2] = new Vector2(Utils.getHalfPlayfieldWidth(), Utils.getHalfHeight());
        vertices[3] = new Vector2(-Utils.getHalfPlayfieldWidth(), Utils.getHalfHeight());
        vertices[4] = new Vector2(-Utils.getHalfPlayfieldWidth(), -Utils.getHalfHeight());
        FixtureDef fixtureDef = createFixture(createChainShape(vertices), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0.0f, 0.0f));
    }

    public static Body createLeftLine(World world) {
        Vector2[] vertices = new Vector2[2];
        vertices[0] = new Vector2(-Utils.getHalfPlayfieldWidth(), -Utils.getHalfHeight());
        vertices[1] = new Vector2(-Utils.getHalfPlayfieldWidth(), Utils.getHalfHeight());
        FixtureDef fixtureDef = createFixture(createChainShape(vertices), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        Body body = createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0.0f, 0.0f));
        body.setUserData(GROUP_SCENERY);
        return body;
    }

    public static Body createRightLine(World world) {
        Vector2[] vertices = new Vector2[2];
        vertices[0] = new Vector2(Utils.getHalfPlayfieldWidth(), -Utils.getHalfHeight());
        vertices[1] = new Vector2(Utils.getHalfPlayfieldWidth(), Utils.getHalfHeight());
        FixtureDef fixtureDef = createFixture(createChainShape(vertices), 1.0f, FRICTION_SCENERY, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_SCENERY;
        Body body = createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0.0f, 0.0f));
        body.setUserData(GROUP_SCENERY);
        return body;
    }

    public static Body createHalfLine(World world) {
        Vector2[] halfLineVertices = new Vector2[2];
        halfLineVertices[0] = new Vector2(0.0f, -Utils.getHalfPlayfieldHeight());
        halfLineVertices[1] = new Vector2(-2.0f * Utils.getHalfPlayfieldWidth(), -Utils.getHalfPlayfieldHeight());
        FixtureDef fixtureDef = createFixture(createChainShape(halfLineVertices), 1.0f, 0.0f, 0.0f, false);
        fixtureDef.filter.groupIndex = GROUP_BALL;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(Utils.getHalfPlayfieldWidth(), Utils.getHalfPlayfieldHeight()));
    }

    public static Shape createPolygonShape(Vector2[] vertices) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);
        return polygonShape;
    }

    public static Body createScoreBoardComputer(World world) {
        Vector2[] vertices = new Vector2[2];
        vertices[0] = new Vector2(9.5f, 1.0f);
        vertices[1] = new Vector2(9.5f, 2.0f);
        FixtureDef fixtureDef = createFixture(createChainShape(vertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) -1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0.0f, 0.0f));
    }

    public static Body createScoreBoardPlayer(World world) {
        Vector2[] vertices = new Vector2[2];
        vertices[0] = new Vector2(9.5f, -1.0f);
        vertices[1] = new Vector2(9.5f, -2.0f);
        FixtureDef fixtureDef = createFixture(createChainShape(vertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) -1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0.0f, 0.0f));
    }

    public static Shape createTriangleShape() {
        PolygonShape triangleShape = new PolygonShape();
        Vector2[] vector2Arr = new Vector2[3];
        vector2Arr[0] = new Vector2(-Utils.getHalfPlayfieldWidth(), -Utils.getHalfPlayfieldHeight());
        vector2Arr[1] = new Vector2(0.0f, Utils.getHalfPlayfieldHeight());
        vector2Arr[2] = new Vector2(Utils.getHalfPlayfieldWidth(), -Utils.getHalfPlayfieldHeight());
        triangleShape.set(vector2Arr);
        return triangleShape;
    }
}
