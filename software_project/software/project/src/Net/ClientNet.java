package Net;

import java.io.IOException;
import java.net.Socket;

/*
 * 	openNet:	新生成一个socket与服务器通信
 * 	closeNet:	关闭socket连接
 * 	
 * 	由于我们秉持一个socket一个线程
 * */
public class ClientNet extends NetManager{
	
	private Socket socket;
	
	public ClientNet() {
		super();
	}
	
	public ClientNet(String IP, int port) {
		this.IP= IP;
		this.port = port;
	}

	public void openNet() {
		try {
			socket = new Socket(IP, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeNet() {
		try {
			if( socket != null ) {
				socket.close();
			}
		}catch (Exception e) {
			System.out.println("./src/Net/ClientNet/close(): error");
			e.printStackTrace();
		}
	}
	
	public boolean isClosed() {
		return socket.isClosed();
	}
	
	public Socket getSocket() {
		return socket;
	}
}
