package com.pogs.runpogsrun.display.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.game.framework.display.DisplayScreen;
import com.game.framework.display.DisplayText;
import com.game.framework.manager.ScreenManager;
import com.pogs.runpogsrun.util.Assets;
import com.pogs.runpogsrun.util.Config;

public class LoadingScreen extends DisplayScreen{

	private DisplayText loadingText;

	public LoadingScreen() {
		super(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		// TODO Auto-generated constructor stub
		Assets.getInstance().initialize();
		
		loadingText = new DisplayText("Loading...",getFont());
		loadingText.setPosition((width - loadingText.getWidth())/2, ( height - loadingText.getHeight())/2);
		addActor(loadingText);
	}
	
	@Override
	protected void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
		AssetManager manager = Assets.getInstance().manager;
		if(manager.update()) {
			ScreenManager.getInstance().setScreen(MainScreen.class);
		}
		loadingText.setText("Loading..."+ (int)(manager.getProgress() * 100) + "%");
		//L.wtf(""+manager.getProgress());
	}

}
