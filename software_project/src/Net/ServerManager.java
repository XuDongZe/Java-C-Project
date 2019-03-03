package Net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import Config.Config;
import Constant.Constants;
import Dao.MySQLDao;
import data.Answer;
import data.MsgEntry;
import data.Problem;
import data.SCommonData;
import data.User;
import netKit.*;
import view.ServerWindow;

/*
 * 用于测试client的虚拟服务器
 * 只用于收信息-> 测试客户端的编码解码器
 * */

/*
 * 	关于类型转换错误
 * 	
 * 	两种解决方法
 * 	第一种采用项目 software的结构重构系统
 * 	第二种:仅仅重构服务器端
 * 	net端使用相同的MsgEntry, 废弃sMsgEntry类, 同时增加serverData类作为数据管理
 * 	即将 服务器端的数据管理和网络管理拆分开来
 * 	而客户端的数据全部需要经过网络传输, 所以不妨让网络管理中的数据部分作为整个客户端的数据管理
 * 	并且将客户端的视图-数据接口也放置于ClientNet部分
 * 	
 * 
 * 	评价:
 * 	第一种更加合理化, 使得客户端的功能更加易于拓展, 试想当增加客户端的功能需要增加必要的数据结构时,
 * 	会将该数据结构存在MsgEntry实体中, 是结构不清晰
 * 	不过这并不会引起流量上的浪费, 因为在传输之前进行socket编码时, 我们仅仅将网络所需要传输的数据剥离开来
 * 	
 * */

/*
 * 	关于服务器端socket的关闭问题:
 * 	不会主动关闭, 等待客户端关闭之后自然自动关闭, 但是时间长度为设定不知道如何进行
 * */

/*
 * 	我们现在用一个socket开启一个线程, 但是不应该这样, 应该是一个客户端一个线程, 
 * 	一个客户端 ----一个线程 ---- 多个socket通信
 * */
public class ServerManager extends NetManager {
	
	/*	一个服务器端多个serverNet, 每个serverNet是一个
	 * 	使用多线程模型解决多个客户端连接问题
	 * 	一个连接对应一个客户端, 一个sMsg实体, 一个clientNet, 
	 * 	服务器端仅有一个公共窗体, 仅有一个公共serverSocket
	 * */
	private static ServerSocket SERVER_SOCKET = null;
	public static SCommonData sData = null;
	public static ServerWindow faWnd;		//外部实例化
	public static MySQLDao DAO;
	
	static {
		try {
			int port = Integer.parseInt(Config.properties.getProperty("PORT"));
			SERVER_SOCKET = new ServerSocket(port);
			sData = new SCommonData();
			DAO = new MySQLDao();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ServerManager() {
		super();
		while(true) {
			try {
				Socket socket = SERVER_SOCKET.accept();
				//faWnd.showMsgArea("接收来自客户端:\t" + inet2Str(socket.getInetAddress()) + "的连接" );
				//每个线程一个私有的socket
				ServerThread serverThread = new ServerThread(socket);
				new Thread(serverThread).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 	仅在该线程内部调用网络消息
	 * 	msg在外部不可用, 如何解决呢?
	 * 	解决办法:
	 * 	1.	
	 * 		增加private的smgEntry和 MySQLManager的对象 及对应的封装方法
	 * 	2.	分析一下什么时候会用到该信息
	 * 		事实上, 我们在这里拥有 faWnd的窗口句柄, 所以对视图的操作可以在该类内部完成
	 * 		而SData作为ServerManager的类变量, 也可以在本类中获得其引用
	 * 		所以结论是:
	 * 			我们根本不需要将每个客户端的信息传递出去, 因为这里就是信息中心
	 * 			我们只需要在接收客户端的信息处理后的结果传递出去即可	
	 * 				-> 客户端消息引起数据库改变 : 	使用线程局部变量MySQLManager
	 * 				-> 客户端消息引起公共数据改变: 使用类变量SData
	 * 				->	客户端消息需要响应:		使用线程局部变量MsgEntry
	 * */
	class ServerThread implements Runnable, Constants{
		private MsgEntry msg;
		private Socket socket;
		//外部传入socket: 从ServerSocket的accept()方法返回并传入
		public ServerThread(Socket socket) {
			msg = new MsgEntry();
			this.socket = socket;
		}
		
		@Override
		//在这个函数里面处理socket的回应事件
		public void run() {
			msg = recvMsg(socket);
			if(msg == null) {	//解码失败
				System.out.println("no message from client");
				return;
			}
			int head = msg.getHead();
			System.out.println("head: "+ head);
			switch (head) {
			case REG:
				//给出客户端Reg事件的回应
				handleReg();
				break;
			case LOGIN:
				handleLogin();
				break;
			case REQ:
				handleReq();
				break;
			case UPD:
				handleUpd();
				break;
			case EXIT:
				handleExit();
				break;
			default:
				break;
			}
		}
		
		/*
		 * 	注册后要经过登录才可以进入服务界面
		 * 	所以注册这一步不需要修改客户端在服务器端的信息(位于sData中)
		 * */
		private void handleReg()  {
			//这里应该加一些出错代码
			String id = msg.getID();
			String psw = msg.getPsw();
			User user = new User();
			if( DAO.queryUser(id, user) ) 
			{
				msg.setState(STATE_ALREADY_EXIST);
				
			}else {
				if ( DAO.regUser(id, psw) )	
					msg.setState(STATE_SUCCESS);
				else 
					msg.setState(STATE_ERROR);
			}
			faWnd.appendToMsgArea( 	inet2Str(socket.getInetAddress()) + ":" + SHOW_SPLITER + 
					"客户端命令:" + "注册" + SHOW_SPLITER +
					"用户名:" + id +  SHOW_SPLITER + 
					"密码:" + psw + SHOW_SPLITER +
					"状态:" + DeCoder.parseStateCode(msg.getState()) );
			msg.setHead(REG_RES);
			sendMsg(socket, msg);	//send完不关闭, 等待客户端关闭
	   }
		
		/*
		 * 	注意除了mySql操作之外
		 * 	登录还要检测用户实时在线状态, 通过该用户对应的sMsg.getIsOnline()获得true为Online
		 * 
		 * 	但上述方法不可行
		 * 	因为之前登录的用户对应的sMsg是分布在一个个线程里的, 目前无法系统访问
		 * 	而该新登录的用户其sMsg是刚刚初始化的, 所以其isOnline状态一定是 初始化的false
		 * 	
		 * 解决办法:
		 * 	一是在ServerWindow层次维护一个在线用户列表, 然后一层层传递引用到网络层
		 * 	而是在数据库tb_user中添加isOnline列, 登录时就像检查密码一样检查isOnline状态
		 * 	然后在退出登录时, 修改数据库的isOnline状态
		 * 
		 * 比较:
		 * 第一个要在现在基础上更改更多的东西
		 * 第二个要更加频繁地访问数据库, 并且增加了数据库user表的列的数目, 效率低
		 * 
		 * 采用:
		 * 第二种, 实现简单嘿嘿		
		 * */
		private void handleLogin() {
			//这里应该加一些出错代码
			String id = msg.getID();
			String psw = msg.getPsw();
			User user = new User();
			//重复登录
			if( sData.userSet.contains(user.setID(id)) ) {
				msg.setState(STATE_ALREADY_LOGIN);
			}
			else {
				if( DAO.queryUser(id, user) ) {
					if( psw.equals(user.getPsw()) ) {
						msg.setState(STATE_SUCCESS);
						sData.userSet.add(user);
						sData.msgMap.put(id, new MsgEntry().setID(id).setPsw(psw));
					}
					else {
						msg.setState(STATE_NO_MATCH);
					}
				}else {
					//数据库未找到
					msg.setState(STATE_NO_EXIST);
				}
			}
			faWnd.appendToMsgArea( 	inet2Str(socket.getInetAddress()) + ":" + SHOW_SPLITER + 
					"客户端命令:" + "登录" + SHOW_SPLITER +
					"用户名:" + id +  SHOW_SPLITER + 
					"密码:" + psw + SHOW_SPLITER +
					"状态:" + DeCoder.parseStateCode(msg.getState()) );
			msg.setHead(LOGIN_RES);
			sendMsg(socket, msg);
		}
		
		/*
		 * 	pNums, pLevs得到题目队列ArrayList<Problem>problems
		 * 	将problems发送出去
		 * 	我们只需要管将SQL数据填充msg相应数据域即可
		 * 	网络编码/解码器 Coder/DeCoder会自动处理类数据的编码
		 * 	也就是传输一个int和传输一个类没什么区别, 在MsgEntry的上层
		 * */
		private void handleReq(){
			String id = msg.getID();
			int pNums = msg.getProblemNums();
			int pLevs = msg.getProblemLevs();
			System.out.println("enter handleReq");
			/*
			 * 	本意是使用异常做题目不够时自己添加的 但没做出来 貌似不能出发自定义异常
			 * 	所以直接用最后的题目编号和最开始的题目编号和用户请求题目数量做比较即可
			 * 	而我们设定第一道题编号为0
			 * 	所以只用最后的题目编号+1 和 pNums比较即可
			 * 
			 * 	一旦编号间关系不再是自增1, 那么就需要修改
			 * */
			int lastSeq = DAO.queryLastSeq();
			if( lastSeq == -1 ) {msg.setState(-10); return;}
			if( (lastSeq - DAO.queryFirstSeq() + 1) < pNums ) {
				//题目不够, 使用Problem类出题, 使用DAO更新数据库, 出题策略, 每次出20道
				int beginSeq = lastSeq + 1;
				for( int i=0; i<20; i++ ) {
					Problem p = new Problem(beginSeq + i);	//随机生成一道题
					DAO.regProblem(p);
				}
				msg.setState(STATE_REQ_PROBLEM_NOT_ENOUGH);
			} 
			else { //题库中题目够
				if ( DAO.queryProblems( pNums, pLevs, msg.initAnsList().getAnsList() )) {
					msg.setState(STATE_SUCCESS);
					for(Answer ans : msg.getAnsList() ) {
						sData.msgMap.get(id).getAnsList().add(ans);
					}
				} else {
					msg.setState(STATE_ERROR);
				}
			}
			
			faWnd.appendToMsgArea( 	inet2Str(socket.getInetAddress()) + ":" + SHOW_SPLITER + 
					"客户端命令:" + "请求题目" + SHOW_SPLITER +
					"用户名:" + id +  SHOW_SPLITER + 
					"题目数量:" + pNums + SHOW_SPLITER +
					"题目难度:" + pLevs + SHOW_SPLITER +
					"状态:" + DeCoder.parseStateCode(msg.getState()) );
			msg.setHead(REQ_RES);
			System.out.println("in handleReq: " + msg.getState());
			sendMsg(socket, msg);
		}
		
		/*	
		 * 	更新用户信息: sumPNums, correctNums到数据库
		 * 	返回UPD_RES包到客户端
		 * */
		private void handleUpd() {
			//这里应该加一些出错代码
			String id = msg.getID();
			int pNums = msg.getProblemNums();
			int correctNums = msg.getCorrectNums();
			if( DAO.updUser(id, pNums, correctNums) ) {
				msg.setState(STATE_SUCCESS);
				sData.msgMap.put(msg.getID(), new MsgEntry().setCorrectNums(correctNums));
			} else {
				//数据库未找到
				msg.setState(STATE_UPD_MYSQL_ERROE);
			}
			
			faWnd.appendToMsgArea( inet2Str(socket.getInetAddress()) + ":" + SHOW_SPLITER + 
					"客户端命令:" + "更新用户信息" + SHOW_SPLITER +
					"用户名:" + id +  SHOW_SPLITER + 
					"本次总题目:" + pNums + SHOW_SPLITER +
					"本次正确题目:" + correctNums + SHOW_SPLITER +
					"状态:" + DeCoder.parseStateCode(msg.getState()) );
			msg.setHead(UPD_RES);
			sendMsg(socket, msg);
		}
		
		/*
		 * 	更改在线用户列表
		 * 	目前没想到什么情况下客户端会退出失败
		 * 	发出的MsgEntry也不知道在客户端的哪里会用到
		 * */
		private void handleExit() {
			System.out.println("enter exit");
			//注意要setID, remove按照ID的hashCode识别
			String id = msg.getID();
			User user = new User().setID(id);	
			sData.userSet.remove(user);
			msg.setState(STATE_SUCCESS);

			faWnd.appendToMsgArea( 	inet2Str(socket.getInetAddress()) + ":" + SHOW_SPLITER + 
					"客户端命令:" + "退出" + SHOW_SPLITER +
					"用户名:" + id +  SHOW_SPLITER + 
					"状态:" + DeCoder.parseStateCode(msg.getState()) );
			msg.setHead(EXIT_RES);
			sendMsg(socket, msg);
		}
	}
}
