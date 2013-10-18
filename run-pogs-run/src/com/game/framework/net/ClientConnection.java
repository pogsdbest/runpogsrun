package com.game.framework.net;

import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Disposable;
import com.game.framework.utils.L;

public class ClientConnection implements Runnable, Disposable , ConnectionCallback {

	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private Thread thread;
	private boolean isConnected;
	private ConnectionCallback callback;

	public ClientConnection(Socket socket) {
		this.socket = socket;
		this.inputStream = socket.getInputStream();
		this.outputStream = socket.getOutputStream();
		this.callback = this;
		this.thread = new Thread(this);
		thread.start();
		isConnected = true;
	}

	@Override
	public void dispose() {
		try {
			if (socket != null) {
				socket.dispose();
				inputStream.close();
				outputStream.close();
				
				L.wtf("client connection end...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			error("Error at dispose...");
		}
	}

	@Override
	public void run() {
		try {
			byte[] buffer  = new byte[1024];
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
		L.wtf("client thread end...");
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

	public boolean isConnected() {
		return isConnected;
	}
	
	public void error(String txt) {
		L.e(txt);
		callback.onError();
	}
	
	public void end() {
		callback.onEnd();
	}
	
	public void setCallback(ConnectionCallback callback) {
		this.callback = callback;
	}
	
	public void close() {
		isConnected = false;
		dispose();
	}

	@Override
	public void onConnect(Socket socket) {
	}

	@Override
	public void onError() {
	}

	@Override
	public void onEnd() {
	}

	@Override
	public void onUpdate(byte[] data) {
	}

}
