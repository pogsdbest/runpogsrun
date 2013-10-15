package com.game.framework.net;

import java.net.InetAddress;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Disposable;
import com.game.framework.utils.L;

public class NetworkServer implements Runnable,Disposable{
	
	private Thread thread;
	private ServerSocket serverSocket;
	private int port;
	private boolean isAcceptingClient;
	private ArrayList<ClientConnection> clients;
	private ConnectionCallback callback;
	private boolean isRunning;
	
	public NetworkServer(ConnectionCallback callback) {
		this.callback = callback;
		port = 13456;
		isAcceptingClient = false;
		setRunning(false);
		clients = new ArrayList<ClientConnection>();
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
			setRunning(true);
			L.wtf("Server start..");
			L.wtf("port: "+port);
			L.wtf("host: "+InetAddress.getLocalHost().getHostAddress());
			while(isAcceptingClient && isRunning) {
				Socket socket = serverSocket.accept(null);
				ClientConnection client = new ClientConnection(this,socket,callback);
				L.wtf("Client connected: ");
				clients.add(client);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		if(serverSocket!=null){
			serverSocket.dispose();
		}
		if(clients!=null) {
			for (ClientConnection client : clients) {
				if(client.isConnected()) {
					client.setConnected(false);
				}
				clients.remove(client);
			}
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void removeClient(ClientConnection clientConnection) {
		if(clients!=null) {
			if(clients.contains(clientConnection)) {
				clients.remove(clientConnection);
			}
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
