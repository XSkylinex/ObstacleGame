package com.obstacle.alex;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.obstacle.alex.screen.GameScreen;

public class MainObstacle extends Game {//game class is implements listener interface and it help me have multiple screens
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new GameScreen());
	}

}
