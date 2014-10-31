package com.mygdx.game.airhockey;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by marco.granatiero on 30/10/2014.
 */
public class Assets {

    public static AssetManager manager = null;

    public static int playfieldCount = 0;
    public static int playfieldRegion = 0;
    public static TextureRegion[] playfieldRegions = null;
    public static Texture[] playfields = null;
    public static Texture p1 = null;

    static  {
        Assets.manager = new AssetManager();
        Assets.playfieldCount = 6;
        Assets.playfields = new Texture[Assets.playfieldCount];
        Assets.playfieldRegions = new TextureRegion[Assets.playfieldCount];

    }

    public static void loadPictures() {
        Assets.loadPicture("airhockey/background.jpg");
//        Assets.loadPicture("airhockey/background0.jpg");
//        Assets.loadPicture("airhockey/background1.jpg");
//        Assets.loadPicture("airhockey/background2.jpg");
//        Assets.loadPicture("airhockey/background3.jpg");
//        Assets.loadPicture("airhockey/background4.jpg");
//        Assets.loadPicture("airhockey/background5.jpg");

        while (!manager.update()){
            //wait
        }
        post_load();
    }

    public static void loadPicture(String file) {
        manager.load(file, Texture.class);
    }

    public static void post_load() {
        if(Assets.manager.isLoaded("airhockey/background.jpg", Texture.class)) {
            playfields[0] = Assets.manager.get("airhockey/background.jpg", Texture.class);
            playfields[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }
}
