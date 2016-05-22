package com.mygdx.game;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//default is false - for screen rotation https://github.com/libgdx/libgdx/wiki/Gyroscope
		//see AndroidManifest http://gamedev.stackexchange.com/questions/72749/why-doesnt-my-android-application-rotate-when-i-rotate-my-screen
		config.useGyroscope = true;
		initialize(new MyGdxGame(), config);
	}
}
