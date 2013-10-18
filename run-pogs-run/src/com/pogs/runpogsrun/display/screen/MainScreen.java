package com.pogs.runpogsrun.display.screen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.framework.display.DisplayObject;
import com.game.framework.display.DisplayScreen;
import com.pogs.runpogsrun.display.object.SpriterDisplay;
import com.pogs.runpogsrun.util.Assets;
import com.pogs.runpogsrun.util.Config;

public class MainScreen extends DisplayScreen {

	public MainScreen() {
		super(Config.SCREEN_WIDTH , Config.SCREEN_HEIGHT);
		
		TextureAtlas atlas = Assets.getInstance().get("gfx/assets.pack");
		TextureRegion bg = atlas.findRegion("bg");
		DisplayObject bgDisplay = new DisplayObject(bg);
		addActor(bgDisplay);
		
		SpriterDisplay pogs = new SpriterDisplay(Assets.getInstance().loader,getSpriteBatch(),Assets.getInstance().player);
		pogs.setX(100);
		pogs.setY(100);
		addActor(pogs);
		
	}

}
