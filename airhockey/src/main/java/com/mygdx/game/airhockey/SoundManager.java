package com.mygdx.game.airhockey;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Marco on 01/11/2014.
 */
public class SoundManager {

    public static AssetManager manager = new AssetManager();

    private static boolean soundEnabled = true;
    private static long soundStartTime = 0;

    public static Music music;
    public static Sound ballPaddleSound;
    public static Sound ballPlaygroundSound;
    public static Sound goalSound;

    public static void loadSounds() {
        //Assets.manager.load("airhockey/menu.ogg", Music.class);
        manager.load("airhockey/ball_paddle.ogg", Sound.class);
        manager.load("airhockey/ball_playground.ogg", Sound.class);
        manager.load("airhockey/goal.ogg", Sound.class);
    }

    public static void post_loadSound() {
        //music = Assets.manager.get("airhockey/menu.ogg", Music.class);
        ballPaddleSound = manager.get("airhockey/ball_paddle.ogg", Sound.class);
        ballPlaygroundSound = manager.get("airhockey/ball_playground.ogg", Sound.class);
        goalSound = manager.get("airhockey/goal.ogg", Sound.class);

    }


    public static void playSound(Sound sound) {
        if(sound != null && (soundEnabled) && ((System.currentTimeMillis())) - soundStartTime >= 100) {
            soundStartTime = System.currentTimeMillis();
            sound.play(1f);
        }
    }
}
