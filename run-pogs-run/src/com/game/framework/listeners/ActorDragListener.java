package com.game.framework.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.game.framework.utils.L;

public class ActorDragListener extends InputListener {

	private Actor actor;

	@Override
	public boolean handle(Event e) {
		// TODO Auto-generated method stub
		this.actor = e.getTarget();
		return super.handle(e);
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		actor.setPosition((actor.getX() + x) - actor.getWidth() / 2,
				(actor.getY() + y) - actor.getHeight() / 2);
		return true;
	}

	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		actor.setPosition((actor.getX() + x) - actor.getWidth() / 2,
				(actor.getY() + y) - actor.getHeight() / 2);
		super.touchDragged(event, x, y, pointer);
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) {
		super.touchUp(event, x, y, pointer, button);
		L.wtf("Actor is at (" + (int) actor.getX() + "," + (int) actor.getY()
				+ ")");
	}
}
