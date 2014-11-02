package com.mygdx.game.airhockey;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Marco on 02/11/2014.
 */
public class ComputerAI {

    private static final int AI_MODE_ATTACK = 2;
    private static final int AI_MODE_DEFENCE = 1;

    static final float EASY = 0.35f;
    static final float MEDIUM = 0.6f;
    static final float HARD = 0.9f;

    public static float difficulty;
    private static float autoMaxV;
    private static float velocity;

    private int ai_mode;
    private float attack_count;
    private final int attack_hit;
    private final int attack_reaction;
    private int attack_state;
    private float attack_time;

    private final Body ball;
    private final Body computer;

    private Vector2 player2_position;

    public ComputerAI(Body computer, Body ball){

        this.computer = computer;
        this.ball = ball;

        ai_mode = AI_MODE_DEFENCE;
        difficulty = EASY;

        autoMaxV = difficulty * 40f;
        velocity = 0;
        this.attack_reaction = 0;
        this.attack_hit = 1;
        this.attack_state = 0;
        this.attack_time = 0.8f;

        player2_position = new Vector2(0.0f, Constants.PLAYER_REPOSITION_OFFSET);
    }

    public void updateAutoplayer(float deltaTime) {

        Vector2 ballPosition = this.ball.getPosition();
        Vector2 ballVelocity = this.ball.getLinearVelocity();

        if(ai_mode == AI_MODE_DEFENCE) {
            velocity = 0f;
            defence();
        }
        else if(ai_mode == AI_MODE_ATTACK) {
            attack(deltaTime);
        }

//        if(ballPosition.y > PlayScreen2.world_SIZE.y - PlayScreen2.box2_v1 - PlayScreen2.box2_ball_R * 2f && (
//                ballPosition.x < PlayScreen2.box2_ball_R * 2f + PlayScreen2.box2_v1 || ballPosition.x > PlayScreen2.world_SIZE
//                        .x - PlayScreen2.box2_ball_R * 2f - PlayScreen2.box2_v1)) {
//            this.ai_mode = AI_MODE_DEFENCE;
//            this.corner = true;
//            this.corner_count = 0.1f;
//        }
//
//        if(this.corner) {
//            this.corner_count -= deltaTime;
//            if(this.corner_count < 0f) {
//                this.corner = false;
//            }
//        }
//
        if(this.ai_mode == AI_MODE_ATTACK && ballPosition.y >0 ) {//TODO < PlayScreen2.world_SIZE.y / 2f
            this.ai_mode = AI_MODE_DEFENCE;
        }
//        else if(!this.corner && this.ai_mode == AI_MODE_DEFENCE && ballPosition.y > PlayScreen2.world_SIZE.y / 2f - PlayScreen2
//                .box2_ball_R && ballVelocity.y > -20f) {

        else if(this.ai_mode == AI_MODE_DEFENCE && ballPosition.y > 0 && ballVelocity.y < 20f) {
            this.ai_mode = AI_MODE_ATTACK;
            this.computer.setLinearVelocity(0f, 0f);
            this.attack_state = 0;
            this.attack_count = this.attack_time * (((float)Math.random())) * (1f - difficulty);
        }
//
//        if(!this.corner && this.ai_mode == AI_MODE_DEFENCE && ballVelocity.y > 100f && Math.abs(ballVelocity.x) > 20f) {
//            this.ai_mode = AI_MODE_ATTACK;
//            this.computer.setLinearVelocity(0f, 0f);
//            this.attack_state = 0;
//            this.attack_count = this.attack_time * (((float)Math.random())) * (1f - difficulty);
//        }
//
//        if((this.player2HitBall) && ballVelocity.y < -20f) {
//            this.ai_mode = AI_MODE_DEFENCE;
//        }
    }

    private void defence() {
        Vector2 ballPosition = this.ball.getPosition();
        Vector2 ballVelocity = this.ball.getLinearVelocity();
        Vector2 computerPosition = this.computer.getPosition();
        Vector2 computerVelocity = this.computer.getLinearVelocity();
        float v6 = player2_position.y - 2f;

        if(ballPosition.y > 0) {//TODO < PlayScreen2.world_SIZE.y / 2f
            if(computerVelocity.x > 0.3f * autoMaxV) {
                computerVelocity.x = 0.3f * autoMaxV;
            }
            else if(computerVelocity.x < -0.3f * autoMaxV) {
                computerVelocity.x = -0.3f * autoMaxV;
            }
            else {
                computerVelocity.x = ballVelocity.x;
            }

            if(computerPosition.x < 5f && computerVelocity.x < 0f) {
                computerVelocity.x = 0f;
            }
            else if(computerPosition.x > 19f && computerVelocity.x > 0f) {
                computerVelocity.x = 0f;
            }

            if(computerPosition.y < v6) {
                computerVelocity.y = 0.5f * autoMaxV;
            }
            else {
                computerVelocity.y = 0f;
            }

            if(computerPosition.y - v6 >= 5f) { //TODO <=
                //goto label_80;
                computerVelocity.y = 0.5f * autoMaxV;
            }

//            computerVelocity.y = -0.5f * autoMaxV;
//            goto label_49;

//            label_80:
            if(computerPosition.y - v6 <= -5f) {//TODO >=
                //goto label_49;
                computerVelocity.y = -0.5f * autoMaxV;
            }

//            computerVelocity.y = 0.5f * autoMaxV;

        } else {
//            float v3 = 0f;
//            if(Math.random() > 0.5) {
//                v3 = ((float)((((double)PlayScreen2.offScaleX)) * (Math.random() - 0.5)));
//            }
//
//            Vector2 v7 = computerPosition.tmp().sub(player2_position.x + v3, v6);
//            float v2 = v7.len();
//            v7.mul(-1f);
//            if(v7.len() != 0f) {
//                v7.nor();
//            }
//
//            if((((double)v2)) < 0.5 * (((double)PlayScreen2.box2_mattle_R))) {
//                v7.set(0f, 0f);
//            }
//
//            computerVelocity.set(v7);
//            computerVelocity.mul(0.5f * autoMaxV);
        }

        //label_49:
        this.computer.setLinearVelocity(computerVelocity);
        this.computer.setTransform(computerPosition, 0f);
    }


    private void attack(float deltaTime) {

//        switch(attack_state) {
//            case 0: {
//                goto label_5;
//            }
//            case 1: {
//                goto label_13;
//            }
//        }
//
//        return;
//        label_5:
//        this.attack_count -= deltaTime;
//        if(this.attack_count >= 0f) {
//            return;
//        }
//
//        this.attack_state = 1;
//        return;
//        label_13:
//        if(velocity >= autoMaxV) {
//            goto label_55;
//        }
//
//        velocity += PlayScreen2.a * deltaTime;
//        goto label_21;
//        label_55:
//        velocity = autoMaxV;
//        label_21:
//        Vector2 v0 = this.ball.getPosition();
//        Vector2 v1 = this.ball.getLinearVelocity();
//        Vector2 v5 = this.computer.getPosition();
//        Vector2 v6 = this.computer.getLinearVelocity();
//        float v7 = v1.len();
//        if(v5.y <= v0.y) {
//            goto label_58;
//        }
//
//        if(v1.y > 0f) {
//            goto label_39;
//        }
//
//        if(v7 >= 0.1f * autoMaxV) {
//            goto label_58;
//        }
//
//        label_39:
//        Vector2 v2 = v0.tmp().sub(v5);
//        v2.add(v1.x / 20f, v1.y / 20f);
//        if(v2.len() == 0f) {
//            goto label_49;
//        }
//
//        v2.nor();
//        label_49:
//        v6.set(v2);
//        v6.mul(velocity);
//        goto label_52;
//        label_58:
//        Vector2 v3 = v0.tmp().sub(v5);
//        if(v3.y == 0f) {
//            goto label_84;
//        }
//
//        v3.nor();
//        float v4 = (PlayScreen2.world_SIZE.y - v0.y) * v3.x / (PlayScreen2.world_SIZE.y - v3.y);
//        if(v4 <= PlayScreen2.box2_doorStartX) {
//            goto label_80;
//        }
//
//        if(v4 >= PlayScreen2.doorEndX) {
//            goto label_80;
//        }
//
//        v6.y = 0f;
//        goto label_52;
//        label_80:
//        v6.set(v3);
//        v6.mul(velocity);
//        goto label_52;
//        label_84:
//        v6.y = 0f;
//        if(v0.x <= PlayScreen2.world_SIZE.x / 2f) {
//            goto label_95;
//        }
//
//        v6.x = -velocity;
//        goto label_52;
//        label_95:
//        v6.x = velocity;
//        label_52:
//        computer.setLinearVelocity(v6);
    }
}
