package com.game.framework.net;

import com.badlogic.gdx.net.Socket;

/*
 * interface that act as a receiver.  every time the connection receives
 * an update, the method obtainedData() will be call.
 */

public interface ConnectionCallback {

	public void onConnect(Socket socket);

	public void onError();

	public void onEnd();

	public void onUpdate(byte[] data);
}
