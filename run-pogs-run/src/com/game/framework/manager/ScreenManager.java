package com.game.framework.manager;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.game.framework.display.DisplayScreen;


public final class ScreenManager {

	private static ScreenManager instance;

	private Game game;
	
	public HashMap<String,Class<?>> screens;

	private ScreenManager() {
		screens = new HashMap<String,Class<?>>();
	}

	public static ScreenManager getInstance() {
		if (null == instance) {
			instance = new ScreenManager();
		}
		return instance;
	}
	
	public void initialize(Game game) {
		this.game = game;
	}
	
	public void addScreen(Class<?> screen) {
		if(screen!=null) {
			screens.put(screen.toString(),screen);
		}
	}
	
	public void setScreen(Class<? extends DisplayScreen> screenClass) {
		String name = screenClass.toString();
		if(game.getScreen()!=null) {
			game.getScreen().dispose();
		}
		Class<?> c = screens.get(name);
		DisplayScreen currentScreen = null;
		try{
			currentScreen = (DisplayScreen) Class.forName(c.getName()).newInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
		game.setScreen(currentScreen);
		//currentScreen.init();
	}

	public void dispose() {
		if(game.getScreen()!=null) {
			game.getScreen().dispose();
		}
		instance = null;
	}
}
