package netKit;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import Constant.Constants;
import data.MsgEntry;

/*
 * 接收一个socekt,解析其内容,输出MsgEntry对象的引用。
 * 与Coder做相反的事情
 * */

/*
 *  使用方法：
 *  socket 		从对方来的socekt, 其中填充着信息
 *  MsgEntry msg = new DeCoder(socket).getMsg();
 * */
public class DeCoder implements Constants{
	
	public DeCoder(){}
	
	/*
	 * 	socket -> MsgEntry
	 * 	解码失败返回null
	 * */
	public MsgEntry deCode2Msg(Socket socket) {
		MsgEntry msg = new MsgEntry();
		String res = "";
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			//使用UTF编码从一个socket的inputStream里面读取String
			System.out.println("Decoder recvMsg: " + (res = dis.readUTF()));
			msg = msg.Str2Msg(res);
		} catch (IOException e) {
			System.out.println("./src/Net/DeCoder.DeCoder(): init dis");
			e.printStackTrace();
			return null;
		}
		return msg;
	}
	
	public MsgEntry deCode(Socket socket) {
		MsgEntry msg = new MsgEntry();
		String res = "";
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			//使用UTF编码从一个socket的inputStream里面读取String
			System.out.println("Decoder recvMsg: " + (res = dis.readUTF()));
			msg = msg.Str2Msg(res);
		} catch (IOException e) {
			System.out.println("./src/Net/DeCoder.DeCoder(): init dis");
			e.printStackTrace();
			return null;
		}
		return msg;
	}
	
	/*
	 * 	解析状态码
	 * */
	public static String parseStateCode(int state){
		String str = null;
		switch (state) {
			case STATE_SUCCESS:
				str = "成功";
				break;
			case STATE_NO_EXIST:
				str = "用户不存在";
				break;
			case STATE_NO_MATCH:
				str = "用户名密码不匹配";
				break;
			case STATE_ALREADY_LOGIN:
				str = "该用户已存在";
				break;
			case STATE_ALREADY_EXIST:
				str = "用户已存在";
				break;
			case STATE_UPD_MYSQL_ERROE:
				str = "更新数据库失败";
				break;
			case STATE_REQ_PROBLEM_NOT_ENOUGH:
				str = "数据库题目少于请求数, 已更新题库...请重新请求";
				break;
			case STATE_ERROR:
				str = "不明错误";
				break;
				
			/*for test*/
			case -10:
				str = "题库lastSeq返回-1";
				break;
			default:
				break;
		}
		return str;
	}
	
	public static boolean isSuccessState(int state) {
		return state == STATE_SUCCESS;
	}
}
