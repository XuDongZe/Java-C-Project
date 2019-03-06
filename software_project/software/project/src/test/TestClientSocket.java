package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestClientSocket {
	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket("132.232.10.223", 8898);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF("hello server");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			System.out.println("from " + socket.getLocalAddress() +" :" +
					dis.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
