package com.pogs.runpogsrun.display.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brashmonkey.spriter.player.SpriterPlayer;
import com.game.framework.display.DisplayObject;
import com.pogs.runpogsrun.util.SpriterDrawer;
import com.pogs.runpogsrun.util.SpriterLoader;

public class SpriterDisplay extends DisplayObject {
	
	private SpriterDrawer drawer;
	private SpriterPlayer player;

	public SpriterDisplay(SpriterLoader loader,SpriteBatch batch,SpriterPlayer player) {
		this.player = player;
		drawer = new SpriterDrawer(batch);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		drawer.draw(player);
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		player.update(getX(), getY());
	}

}
