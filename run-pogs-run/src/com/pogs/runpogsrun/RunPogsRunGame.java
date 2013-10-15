package com.pogs.runpogsrun;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.brashmonkey.spriter.Spriter;
import com.brashmonkey.spriter.player.SpriterPlayer;
import com.brashmonkey.spriter.xml.FileHandleSCMLReader;
import com.pogs.runpogsrun.util.SpriterDrawer;
import com.pogs.runpogsrun.util.SpriterLoader;

public class RunPogsRunGame implements ApplicationListener {

	private Stage stage;
	private SpriteBatch batch;
	private SpriterDrawer drawer;
	private SpriterPlayer player;

	@Override
	public void create() {
		batch = new SpriteBatch();
		this.stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false, this.batch);
		
		SpriterLoader loader = new SpriterLoader(true);
		Spriter spriter = FileHandleSCMLReader.getSpriter(Gdx.files.internal("scml/pogs.scml"), loader);
		player = new SpriterPlayer(spriter,0,loader);
		player.setAnimation("idle", 1, 20);
		
		drawer = new SpriterDrawer(batch);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
	
}
