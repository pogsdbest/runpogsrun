package com.game.framework.net;

import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Disposable;
import com.game.framework.utils.L;

public class ClientConnection implements Runnable, Disposable {

	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private Thread thread;
	private boolean isConnected;
	private ConnectionCallback callback;
	private NetworkServer server;

	public ClientConnection(NetworkServer server ,Socket socket,ConnectionCallback callback) {
		this.server = server;
		this.socket = socket;
		this.callback = callback;
		this.inputStream = socket.getInputStream();
		this.outputStream = socket.getOutputStream();
		this.thread = new Thread(this);
		thread.start();

		setConnected(true);
		callback.onConnect(socket);
	}

	@Override
	public void dispose() {
		try {
			if (socket != null) {
				inputStream.close();
				outputStream.close();
				socket.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
			error("Error at dispose...");
		}
		L.wtf("client connection end...");
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
			setConnected(false);
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

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public void error(String txt) {
		L.e(txt);
		callback.onError();
	}
	
	public void end() {
		callback.onEnd();
		server.removeClient(this);
	}

}
