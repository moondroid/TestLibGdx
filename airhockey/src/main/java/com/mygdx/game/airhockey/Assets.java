package com.mygdx.game.airhockey;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by marco.granatiero on 30/10/2014.
 */
public class Assets {

    public static AssetManager manager = null;

    public static int playfieldCount = 1;
    public static int playfieldRegion = 0;
    public static TextureRegion[] playfieldRegions = null;
    public static Texture[] playfields = null;

    public static int ballCount = 12;
    public static int ballRegion = 0;
    public static TextureRegion[] ballregions = null;

    public static int paddleCount = 12;
    public static int paddleRegion0 = 0;
    public static int paddleRegion1 = 1;
    public static TextureRegion[] paddleRegions = null;

    static  {
        Assets.manager = new AssetManager();
        Assets.playfields = new Texture[Assets.playfieldCount];
        Assets.playfieldRegions = new TextureRegion[Assets.playfieldCount];

        Assets.ballregions = new TextureRegion[Assets.ballCount];
        Assets.paddleRegions = new TextureRegion[Assets.paddleCount];

    }

    public static void loadPictures() {
        Assets.loadPicture("airhockey/background.jpg");
//        Assets.loadPicture("airhockey/background0.jpg");
//        Assets.loadPicture("airhockey/background1.jpg");
//        Assets.loadPicture("airhockey/background2.jpg");
//        Assets.loadPicture("airhockey/background3.jpg");
//        Assets.loadPicture("airhockey/background4.jpg");
//        Assets.loadPicture("airhockey/background5.jpg");

    }

    private static void loadPicture(String file) {
        manager.load(file, Texture.class);
    }

    public static void loadTextureAtlas() {
        //Assets.loadTextureAtlas("airhockey/menu");
        Assets.loadTextureAtlas("airhockey/play");
    }

    private static void loadTextureAtlas(String file) {
        manager.load(file, TextureAtlas.class);
    }

    public static void post_load() {
        if(Assets.manager.isLoaded("airhockey/background.jpg", Texture.class)) {
            playfields[0] = Assets.manager.get("airhockey/background.jpg", Texture.class);
            playfields[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        TextureAtlas atlas = Assets.manager.get("airhockey/play", TextureAtlas.class);
        for(int i = 0; i < 6; i++) {
            String ballName = new String("b(") + Integer.toString(i+1) + new String(")");
            String malletName = new String("m(") + Integer.toString(i+1) + new String(")");
            Assets.ballregions[i] = new TextureRegion(atlas.findRegion(ballName));
            Assets.paddleRegions[i] = new TextureRegion(atlas.findRegion(malletName));
        }
        for(int i = 0; i < 6; i++) {
            String ballName = new String("b0") + Integer.toString(i+1);
            String malletName = new String("m0") + Integer.toString(i+1);
            Assets.ballregions[i + 6] = new TextureRegion(atlas.findRegion(ballName));
            Assets.paddleRegions[i + 6] = new TextureRegion(atlas.findRegion(malletName));
        }
    }
}
