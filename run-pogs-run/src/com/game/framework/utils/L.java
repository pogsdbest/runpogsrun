package com.game.framework.utils;

import com.badlogic.gdx.Gdx;

public class L {

	public static void wtf(String log) {
		Gdx.app.log("[WTF] "	, log);
	}
	public static void e(String err) {
		Gdx.app.log("[ERROR] ", err);
	}
	public static void wtf(float log) {
		wtf(log+"");
	}
}
