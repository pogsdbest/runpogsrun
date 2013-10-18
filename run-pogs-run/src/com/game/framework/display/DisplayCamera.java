package com.game.framework.display;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class DisplayCamera extends Group {
	
	private Actor target;
	private Camera camera;
	private float leftBoundary;
	private float rightBoundary;
	private float topBoundary;
	private float bottomBoundary;

	public DisplayCamera(Camera camera) {
		this.camera = camera;
		// TODO Auto-generated constructor stub
	}
	
	public void setTarget(Actor target) {
		this.target = target;
	}
	
	public Actor getTarget() {
		return target;
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		if(target!=null) {
			if(target.getX() > leftBoundary && target.getX() < rightBoundary) {
				this.camera.position.x = target.getX();
			}
			if(target.getY() > bottomBoundary && target.getY() < topBoundary) {
				this.camera.position.y = target.getY();
			}
		}
		setX(camera.position.x - camera.viewportWidth/2);
		setY(camera.position.y - camera.viewportHeight/2);
		super.act(delta);
	}

	public void setBoundaries(Actor boundaries) {
		leftBoundary = 0 + camera.viewportWidth/2;
		rightBoundary = boundaries.getWidth() - camera.viewportWidth/2;
		topBoundary = boundaries.getHeight() - camera.viewportHeight/2;
		bottomBoundary = 0 + camera.viewportHeight/2;
	}
	
	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return camera.viewportWidth;
	}
	
	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return camera.viewportHeight;
	}

}
