package com.mygdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.airhockey.AirHockey;
import com.mygdx.game.bouncingball.BouncingBallGame;
import com.mygdx.game.pong.Pong;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new MyGdxGame(), config);
        //initialize(new Pong(), config);
        //initialize(new AirHockey(), config);
        initialize(new BouncingBallGame(), config);
	}
}
