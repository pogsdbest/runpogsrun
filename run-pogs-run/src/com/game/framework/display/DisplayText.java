package com.game.framework.display;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DisplayText extends Actor{
	
	private String text;
	private BitmapFont font;
	private HAlignment alignment;
	private SpriteBatch batch;
	private float width;
	private float height;
	private boolean autoResize;

	public DisplayText() {
		this("",new BitmapFont());
		// TODO Auto-generated constructor stub
	}
	
	public DisplayText(String text) {
		this(text,new BitmapFont());
		// TODO Auto-generated constructor stub
	}
	
	public DisplayText(String text,BitmapFont font) {
		super();
		this.text = text;
		this.font = font;
		setWidth(getTextWidth());
		setHeight(getTextHeight());
		this.setAlignment(HAlignment.LEFT);
		this.autoResize = true;
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		this.batch = batch;
		if(autoResize) {
			setWidth(getTextWidth());
			setHeight(getTextHeight());
		}
		font.drawWrapped(batch, text, getX(), getY() + height , width,getAlignment());
		super.draw(batch, parentAlpha);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
	
	@Override
	public void setSize(float width, float height) {
		// TODO Auto-generated method stub
		setWidth(width);
		setHeight(height);
		super.setSize(width, height);
	}
	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return width;
	}
	
	@Override
	public void setWidth(float width) {
		// TODO Auto-generated method stub
		this.width = width;
		super.setWidth(width);
	}
	
	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
	
	@Override
	public void setHeight(float height) {
		// TODO Auto-generated method stub
		this.height = height;
		super.setHeight(height);
	}

	public HAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(HAlignment alignment) {
		this.alignment = alignment;
	}
	
	public float getTextWidth() { 
		return font.getBounds(text).width;
	}
	
	public float getTextHeight() {
		return font.getBounds(text).height;
	}

}
