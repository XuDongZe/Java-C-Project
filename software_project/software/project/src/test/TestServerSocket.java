package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServerSocket {
	public static void main(String[] args) {
		ServerSocket SERVER_SOCKET = null;
		try {
			SERVER_SOCKET = new ServerSocket(8898);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			try {
				Socket socket = SERVER_SOCKET.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				System.out.println("from " + socket.getLocalAddress() +" :" +
						dis.readUTF());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF("hello client");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
