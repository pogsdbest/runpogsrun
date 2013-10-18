package com.game.framework.utils;

public class GameTimer {

	private float duration;
	private float time;
	private boolean continues;
	private boolean isDone;

	public GameTimer(float duration,boolean continues) {
		this.duration = duration;
		this.continues = continues;
		time = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void update(float delta) {
		
		if(time>=duration) {
			if(continues) {
				time -= duration;
			}
			isDone = true;
		} else {
			time+=delta;
			isDone = false;
		}

	}
	
	public boolean isDone() {
		// TODO Auto-generated method stub
				return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	public void reset() {
		time = 0;
		isDone = false;
	}
}
