package com.pogs.runpogsrun.display.screen;

import com.game.framework.display.DisplayScreen;
import com.pogs.runpogsrun.display.object.SpriterDisplay;
import com.pogs.runpogsrun.util.Assets;
import com.pogs.runpogsrun.util.Config;

public class MainScreen extends DisplayScreen {

	public MainScreen() {
		super(Config.SCREEN_WIDTH , Config.SCREEN_HEIGHT);
		
		//SpriterDisplay pogs = new SpriterDisplay(Assets.getInstance().loader,batch,Assets.getInstance().player);
		//addActor(pogs);
	}

}
