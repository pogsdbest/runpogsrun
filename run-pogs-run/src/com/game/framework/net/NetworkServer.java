package com.game.framework.net;

import java.net.InetAddress;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Disposable;
import com.game.framework.utils.L;

public class NetworkServer implements Runnable,Disposable{
	
	public static final int DEFAULT_PORT = 11589;
	private Thread thread;
	private ServerSocket serverSocket;
	private int port;
	private boolean isAcceptingClient;
	private ServerCallback serverCallback;
	
	public NetworkServer(ServerCallback serverCallback) {
		this.serverCallback = serverCallback;
		port = DEFAULT_PORT;
		isAcceptingClient = false;
		
	}
	
	public void startServer() {
		if(thread==null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run() {
		try{
			L.wtf("Starting a new Server...");
			ServerSocketHints hints = new ServerSocketHints();
			hints.acceptTimeout = 0;
			serverSocket = Gdx.net.newServerSocket(Protocol.TCP, port, hints);
			isAcceptingClient = true;
			L.wtf("Server start..");
			L.wtf("port: "+port);
			L.wtf("host: "+InetAddress.getLocalHost().getHostAddress());
			while(isAcceptingClient) {
				Socket socket = serverSocket.accept(null);
				ClientConnection client = new ClientConnection(socket);
				serverCallback.onConnect(client);
				L.wtf("Client connected: ");
				
			}
		} catch (Exception e){
			e.printStackTrace();
			L.e("Error while server is running...");
		}
	}

	@Override
	public void dispose() {
		if(serverSocket!=null){
			serverSocket.dispose();
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void close() {
		isAcceptingClient = false;
		dispose();
	}

}
