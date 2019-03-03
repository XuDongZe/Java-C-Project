package Net;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import Constant.Constants;
import data.MsgEntry;
import netKit.*;

/*
 * 	使用异常处理, 当服务器端不可访问时, 提示出错
 * 	现在还未做
 * */
public class NetManager implements Constants{
	protected String IP;
	protected int port;
	
	public NetManager() {
		port = PORT;
	}
	
	public void sendMsg(Socket socket, MsgEntry msg){
		//使用编码器将socket和要发送的消息序列绑定
		//现在信息已经在该socket的DataOutputStream里面了
		new Coder().enCode(socket, msg);
	}
	
	public MsgEntry recvMsg(Socket socket){
		//使用解码器返回绑定了消息序列的socket中的消息实体
		return new DeCoder().deCode(socket);
	}
	
	//InetAddr与 Str的互换
	protected InetAddress str2Inet(String IP) {
		try {
			return InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected String inet2Str(InetAddress inet) {
		String ip = inet.toString();	//"/127.0.0.1"
		return ip.substring(ip.lastIndexOf("/") + 1);
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}