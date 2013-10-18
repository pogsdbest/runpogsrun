package com.game.framework.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

public class DisplayObject extends Group {

	private TextureRegion texture;
	protected boolean flipX;
	protected boolean flipY;
	protected float alpha;
	//public boolean visible;
	
	public DisplayObject() {
		// TODO Auto-generated constructor stub
		this(null);
	}
	public DisplayObject(TextureRegion texture) {
		super();
		this.setTexture(texture);
		flipX = false;
		flipY = false;
		alpha = 1f;
		setVisible(true);
		if(texture!=null) {
			setSize(texture.getRegionWidth(), texture.getRegionHeight());
		}
		
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		if(texture!=null) {
			batch.setColor(1f, 1f, 1f, alpha);
			batch.draw(getTexture().getTexture(), getX(), getY(), getOriginX(), getOriginY(),
					getWidth(), getHeight(), getScaleX(), getScaleY(),
					getRotation(), getTexture().getRegionX(), getTexture().getRegionY(),
					getTexture().getRegionWidth(), getTexture().getRegionHeight(), flipX,
					flipY);
			batch.setColor(1f, 1f, 1f, 1f);
		}
		
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}
	
	public Rectangle getBounds() {
		if(texture==null) {
			return new Rectangle();
		}
		return new Rectangle(getX(),getY(),getTexture().getRegionWidth()*getScaleX(),getTexture().getRegionHeight()*getScaleY());
	}

	public TextureRegion getTexture() {
		return texture;
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
}
