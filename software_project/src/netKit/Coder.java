package netKit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Constant.Constants;
import data.MsgEntry;

/*
 * 接受一个socket, 一个MsgEntry对象
 * 将Msg的string序列作为socket的内容填充socket		s <- msg
 * */

/*
 * 	使用方法：
 * 	Socket s;
 * 	MsgEntry msg;
 * 	返回一个已经填充信息的socket
 * 	s = Coder(s, msg).getSocket();		s <- msg
 * */
public class Coder implements Constants{
	
	public Coder(){
	}
	
	/*
	 * 	msgEntry -> socket
	 * */
	public void enCode(Socket socket, MsgEntry msg) {
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			//使用UTF编码将一个字符串写入到socket的outputStream里面
			dos.writeUTF(msg.Msg2Str());
			System.out.println("Coder sendMeg: " + msg.Msg2Str());
		} catch (IOException e) {
			System.out.println("./src/Net/Coder.Coder: init dos");
			e.printStackTrace();
		}
	}
}
