package com.game.framework.display;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedDisplayObject extends DisplayObject {
	
	protected TextureRegion[] textures;
	protected Animation animation;
	private float stateTime;
	protected TextureRegion currentFrame;
	protected float duration;
	protected boolean looping;
	
	public AnimatedDisplayObject(TextureRegion[] textures) {
		// TODO Auto-generated constructor stub
		super(textures[0]);
		this.duration = 0.1f;
		this.stateTime = 0f;
		this.looping = true;
		setTextures(duration,textures);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if(textures.length > 1) {
			stateTime += delta;
			setTexture( animation.getKeyFrame(stateTime,looping));
		}
	}
	
	public void setTextures(float duration,TextureRegion[] textures) {
		this.duration = duration;
		this.textures = textures;
		this.animation = new Animation(duration,textures);
		if(looping)
			this.animation.setPlayMode(Animation.LOOP);
	}
	
	public void resetAnimation() {
		stateTime = 0f;
		this.animation = new Animation(duration,textures);
		this.animation.setPlayMode(Animation.LOOP);
	}
}
