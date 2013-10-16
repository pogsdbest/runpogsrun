package com.pogs.runpogsrun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.brashmonkey.spriter.player.SpriterPlayer;
import com.game.framework.manager.ScreenManager;
import com.pogs.runpogsrun.display.screen.LoadingScreen;
import com.pogs.runpogsrun.display.screen.MainScreen;
import com.pogs.runpogsrun.util.SpriterDrawer;

public class RunPogsRunGame extends Game {

	private Stage stage;
	private SpriteBatch batch;
	private SpriterDrawer drawer;
	private SpriterPlayer player;

	@Override
	public void create() {
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().addScreen(MainScreen.class);
		ScreenManager.getInstance().addScreen(LoadingScreen.class);
		ScreenManager.getInstance().setScreen(LoadingScreen.class);
		/*
		batch = new SpriteBatch();
		this.stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false, this.batch);
		
		SpriterLoader loader = new SpriterLoader(true);
		Spriter spriter = FileHandleSCMLReader.getSpriter(Gdx.files.internal("scml/pogs.scml"), loader);
		player = new SpriterPlayer(spriter,0,loader);
		player.setAnimation("idle", 1, 20);
		
		drawer = new SpriterDrawer(batch);
		*/
		
	}
	/*
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		this.stage.act();
		stage.draw();
		batch.begin();
		drawer.draw(player);
		player.update(100, 100);
		batch.end();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
	*/
	
}
