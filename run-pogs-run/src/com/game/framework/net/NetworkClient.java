package com.game.framework.net;

import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Disposable;
import com.game.framework.utils.L;

public class NetworkClient implements Runnable, Disposable {

	private Socket socket;
	private OutputStream outputStream;
	private InputStream inputStream;
	public boolean isDebug;
	private boolean isConnected;
	private ConnectionCallback callback;

	public NetworkClient(ConnectionCallback callback) {
		this.callback = callback;
		isDebug = false;
	}

	public void connectTo(int port, String host) {
		if (host == null)
			throw new IllegalArgumentException("ip cannot be null.");
		try {
			SocketHints hints = new SocketHints();
			Socket socket = Gdx.net.newClientSocket(Protocol.TCP, host, port,
					hints);
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			isConnected = true;
			callback.onConnect(socket);
			Thread thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			isConnected = false;
			error("connection failed to host " + host);
			callback.onEnd();
			//e.printStackTrace();
		}

	}

	@Override
	public void dispose() {
		if(isConnected) isConnected = false;
		try {
			if (socket != null) {
				inputStream.close();
				outputStream.close();
				socket.dispose();
				L.wtf("client dispose...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		try {
			byte[] buffer = new byte[1024];
			while (isConnected()) {
				buffer = new byte[1024];
				inputStream.read(buffer);
				callback.onUpdate(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			error("Error while retreiving data...");
		} finally {
			isConnected = false;
			dispose();
			end();
		}
		L.wtf("thread end...");
	}

	public void sendData(byte[] data) {
		try {
			outputStream.write(data);
		} catch (Exception e) {
			e.printStackTrace();
			dispose();
			error("Error while sending data...");
			end();
		}
	}

	private void end() {
		callback.onEnd();
	}

	private void error(String txt) {
		L.e(txt);
		callback.onError();
	}

	public boolean isConnected() {
		return isConnected;
	}

}
