package com.game.framework.display;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.game.framework.utils.ActorTweenAccessor;

public class DisplayScreen extends Stage implements Screen {

	protected SpriteBatch batch;
	protected OrthographicCamera camera;
	protected TweenManager manager;
	
	protected float width;
	protected float height;
	private BitmapFont font;
	protected boolean isDebug;
	protected float zoom;

	public DisplayScreen(float width, float height) {
		super(width,height,true);
		//this.name = name;
		batch = new SpriteBatch();
		manager = new TweenManager();
		camera = new OrthographicCamera(width, height);
		camera.position.set(width / 2, height / 2 , 0);
		camera.zoom = 1;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		setCamera(camera);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		this.width = width;
		this.height = height;
		isDebug = false;
		zoom = 1;
		
		setFont(new BitmapFont());
		Tween.registerAccessor(Actor.class, new ActorTweenAccessor());
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//draw tile maps 
		//batch.setProjectionMatrix(camera.combined);
		
		drawTileMapLayers();
		batch.begin();
		//draw stage and actors
		draw();//must not override
		if(isDebug)//draw table UI lines
			Table.drawDebug(this);
		batch.end();
		//draw
		
		//update
				
		Vector3 temp = new Vector3();
		temp.set(0, 0, 0);
		camera.unproject(temp);
		float zoomHolder = camera.zoom;
		camera.update();
		
		
		//draw screen with zoom
		camera.zoom = zoom;
		batch.begin();
		drawScreen(batch);
		if(isDebug) {
			getFont().draw(batch, "Delta Time : " + Gdx.graphics.getDeltaTime() , 20, height - 20);
			getFont().draw(batch, "Camera : x : " + camera.position.x + " y : " + camera.position.y, 20, height - 40);
			getFont().draw(batch, "FPS : " + Gdx.graphics.getFramesPerSecond(), 20, height - 60);
			getFont().setScale(1, 1);
			
		}
		batch.end();
		camera.zoom = zoomHolder;
		
		act(delta);
		update(delta);
		manager.update(delta);
		
	}

	protected void drawScreen(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}
	
	protected void drawActual(TextureRegion region,float x,float y) {
		Sprite sprite = new Sprite(region);
		sprite.setOrigin(0, 0);
		sprite.setPosition(x, y);
		sprite.setScale(getScaleRatio().x, getScaleRatio().y);
		sprite.draw(batch);
	}
	
	protected void drawTileMapLayers() {
		// TODO Auto-generated method stub
		
	}

	protected void update(float delta) {
		// TODO Auto-generated method stub
		
		
	}
	
	public void disableInput() {
		Gdx.input.setInputProcessor(null);
	}
	
	public void enableInput() {
		Gdx.input.setInputProcessor(this);
	}
	
	public Vector2 screenToLocal(int x,int y) {
		float px = ( (float)x / (float)Gdx.graphics.getWidth() ) * width;
    	float py =  height  - ( y / (float)Gdx.graphics.getHeight() ) * height;
		
		return new Vector2(px,py);
	}
	public Vector2 screenToLocal(float x,float y) {
		return screenToLocal((int)x,(int)y);
	}
	
	public Vector2 localToScreen(int x,int y) {
		float scaleX = getScaleRatio().x;
		float scaleY = getScaleRatio().y;
		
		float px = x * scaleX;
    	float py = y * scaleY;
		
		return new Vector2(px,py);
	}
	
	public Vector2 localToScreen(float x,float y) {
		return localToScreen((int)x, (int)y);
	}
	
	public Vector2 getScaleRatio() {
		float scaleX = width/Gdx.graphics.getWidth();
		float scaleY = height/Gdx.graphics.getHeight();
		return new Vector2(scaleX,scaleY);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	
	public void init() {
		
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		// TODO Auto-generated method stub
		
		return super.keyDown(keyCode);
	}
	
	@Override
	public boolean keyUp(int keyCode) {
		// TODO Auto-generated method stub
		return super.keyUp(keyCode);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Vector2 size = Scaling.fit.apply(this.width, this.height, width, height);
		int viewportX = (int)(width - size.x) / 2;
		int viewportY = (int)(height - size.y) / 2;
		int viewportWidth = (int)size.x;
		int viewportHeight = (int)size.y;
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		setViewport(this.width, this.height, true, viewportX, viewportY, viewportWidth, viewportHeight);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		getFont().dispose();
		super.dispose();
	}
}
