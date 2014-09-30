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


public class Box2DFactory {
    static Utils utils;

    static {
        utils = new Utils();
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

    public static Shape createBoxShape(Vector2 center, float angle) {
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(utils.getHalfWidth(), utils.getHalfHeight(), center, angle);
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
        cornerLineLeftDown[0] = new Vector2(-17.0f, (-utils.getHalfHeight()) - 14.5f);
        cornerLineLeftDown[1] = new Vector2(-2.0f * utils.getHalfWidth(), (-utils.getHalfHeight()) - 12.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(cornerLineLeftDown), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createCornerLineLeftUp(World world) {
        Vector2[] cornerLineLeftUp = new Vector2[2];
        cornerLineLeftUp[0] = new Vector2(-17.0f, (-utils.getHalfHeight()) + 14.5f);
        cornerLineLeftUp[1] = new Vector2(-2.0f * utils.getHalfWidth(), (-utils.getHalfHeight()) + 12.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(cornerLineLeftUp), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createCornerLineRightDown(World world) {
        Vector2[] cornerLineRightDown = new Vector2[2];
        cornerLineRightDown[0] = new Vector2(0.0f, (-utils.getHalfHeight()) - 12.5f);
        cornerLineRightDown[1] = new Vector2(-0.21f * utils.getHalfWidth(), (-utils.getHalfHeight()) - 14.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(cornerLineRightDown), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createCornerLineRightUp(World world) {
        Vector2[] cornerLineRightUp = new Vector2[2];
        cornerLineRightUp[0] = new Vector2(-2.0f, (-utils.getHalfHeight()) + 14.5f);
        cornerLineRightUp[1] = new Vector2(utils.getHalfWidth() * 0.0f, (-utils.getHalfHeight()) + 12.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(cornerLineRightUp), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
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

    public static Body createGoalLineDown(World world) {
        Vector2[] goalLineDownVertices = new Vector2[2];
        goalLineDownVertices[0] = new Vector2(-8.0f, (-utils.getHalfHeight()) - 15.0f);
        goalLineDownVertices[1] = new Vector2(-1.2f * utils.getHalfWidth(), (-utils.getHalfHeight()) - 15.0f);
        FixtureDef fixtureDef = createFixture(createChainShape(goalLineDownVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) -1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createGoalLineLeft(World world) {
        Vector2[] LeftLineVertices = new Vector2[2];
        LeftLineVertices[0] = new Vector2(-12.0f, (-utils.getHalfHeight()) - 14.5f);
        LeftLineVertices[1] = new Vector2(-2.0f * utils.getHalfWidth(), (-utils.getHalfHeight()) - 14.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(LeftLineVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createGoalLineLeftUp(World world) {
        Vector2[] UpLeftLineVertices = new Vector2[2];
        UpLeftLineVertices[0] = new Vector2(-12.0f, (-utils.getHalfHeight()) + 14.5f);
        UpLeftLineVertices[1] = new Vector2(-2.0f * utils.getHalfWidth(), (-utils.getHalfHeight()) + 14.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(UpLeftLineVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createGoalLineRight(World world) {
        Vector2[] rightLineVertices = new Vector2[2];
        rightLineVertices[0] = new Vector2(0.0f, (-utils.getHalfHeight()) - 14.5f);
        rightLineVertices[1] = new Vector2(-2.0f * utils.getHalfWidth() + 12.0f, (-utils.getHalfHeight()) - 14.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(rightLineVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createGoalLineRightUp(World world) {
        Vector2[] upRightLineVertices = new Vector2[2];
        upRightLineVertices[0] = new Vector2(0.0f, (-utils.getHalfHeight()) + 14.5f);
        upRightLineVertices[1] = new Vector2(-2.0f * utils.getHalfWidth() + 12.0f, (-utils.getHalfHeight()) + 14.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(upRightLineVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createGoalLineUp(World world) {
        Vector2[] goalLineUpVertices = new Vector2[2];
        goalLineUpVertices[0] = new Vector2(-8.0f, (-utils.getHalfHeight()) + 15.0f);
        goalLineUpVertices[1] = new Vector2(-1.2f * utils.getHalfWidth(), (-utils.getHalfHeight()) + 15.0f);
        FixtureDef fixtureDef = createFixture(createChainShape(goalLineUpVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) -1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createHalfLine(World world) {
        Vector2[] halfLineVertices = new Vector2[2];
        halfLineVertices[0] = new Vector2(0.0f, -utils.getHalfHeight());
        halfLineVertices[1] = new Vector2(-2.0f * utils.getHalfWidth(), -utils.getHalfHeight());
        FixtureDef fixtureDef = createFixture(createChainShape(halfLineVertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) -1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
    }

    public static Body createInvisibleWalls(World world) {
        Utils utils = new Utils();
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(-utils.getHalfWidth(), -utils.getHalfHeight());
        vertices[1] = new Vector2(utils.getHalfWidth(), -utils.getHalfHeight());
        vertices[2] = new Vector2(utils.getHalfWidth(), utils.getHalfHeight());
        vertices[3] = new Vector2(-utils.getHalfWidth(), utils.getHalfHeight());
        vertices[4] = new Vector2(-utils.getHalfWidth(), -utils.getHalfHeight());
        FixtureDef fixtureDef = createFixture(createChainShape(vertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) -1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0.0f, 0.0f));
    }

    public static Body createLeftLine(World world) {
        Vector2[] vertices = new Vector2[2];
        vertices[0] = new Vector2(-utils.getHalfWidth(), -utils.getHalfHeight());
        vertices[1] = new Vector2(-utils.getHalfWidth(), utils.getHalfHeight());
        FixtureDef fixtureDef = createFixture(createChainShape(vertices), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0.0f, 0.0f));
    }

    public static Shape createPolygonShape(Vector2[] vertices) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);
        return polygonShape;
    }

    public static Body createRightLine(World world) {
        Vector2[] rightLineVercites = new Vector2[2];
        rightLineVercites[0] = new Vector2(0.0f, (-utils.getHalfWidth()) + 9.5f);
        rightLineVercites[1] = new Vector2(utils.getHalfWidth() * 1.0f - 9.5f, (-utils.getHalfHeight()) - 14.5f);
        FixtureDef fixtureDef = createFixture(createChainShape(rightLineVercites), 1.0f, 0.5f, 0.0f, false);
        fixtureDef.filter.groupIndex = (short) 1;
        return createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(utils.getHalfWidth(), utils.getHalfHeight()));
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
        vector2Arr[0] = new Vector2(-utils.getHalfWidth(), -utils.getHalfHeight());
        vector2Arr[1] = new Vector2(0.0f, utils.getHalfHeight());
        vector2Arr[2] = new Vector2(utils.getHalfWidth(), -utils.getHalfHeight());
        triangleShape.set(vector2Arr);
        return triangleShape;
    }
}
