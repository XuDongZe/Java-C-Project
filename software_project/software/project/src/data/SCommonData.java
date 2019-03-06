package data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
 * 	server端公共的数据类,
 * 	与服务器window类同级别
 * 	直接用public的属性域, 简单, 但不严谨
 * 	*/
public class SCommonData {
	//在线用户列表
	//private LinkedList<User> onLineUsers;
	/*
	 * 	维护在线用户表, 经常做查询和插入、删除操作
	 * 	hashSet较好
	 * */
	public HashSet<User> userSet;
	/*
	 * 	id - msg	映射
	 * 	记录用户在服务器端的整个生命周期中的通讯信息
	 * 	生命周期:
	 * 	create				LoginRes()
	 * 	add ID、psw			LoginRes()
	 * 	add pNums、pLevs		ReqRes()
	 * 	add ansList			
	 * */
	public HashMap<String, MsgEntry> msgMap;	 
	
	public SCommonData() {
		userSet = new HashSet<User>();
		msgMap = new HashMap<String, MsgEntry>();
	}
	
	/*public boolean appendToUserSet(User user) {
		//因为登录时已经保证了不会有相同的user(ID不同)
		//所以这个返回值一定一直是true, 也就是没有重复的
		return onLineUsers.add(user);
	}
	
	public boolean removeFromUserSet(User user) {
		if( isOnline(user) ) {
			onLineUsers.remove(user);
			return true;
		}
		return false;
	}
	public boolean isOnline(User user) {
		return onLineUsers.contains(user);
	}
	public HashSet<User> getOnlineUsers(){
		return onLineUsers;
	}
	
	public HashMap<String, MsgEntry> getMsgMap() {
		return msgMap;
	}*/
	
	public void showOnlineUsers() {
		String res = "";
		for(User user: userSet)	res += user+"\n";
		System.out.println(res);
	}
}
