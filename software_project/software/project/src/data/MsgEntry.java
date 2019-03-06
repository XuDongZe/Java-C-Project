package data;

import java.util.ArrayList;
import data.Problem;

/*
 * 	消息实体，客户端设计的关键类
 * 	客户端view 层 和 net层的接口, 服务器端Data层和net层的接口
 * 	即维护了客户端再一次测试中的数据结构，又将部分成员变量组合成序列给net发送出去
 * 	封装五种消息和对应的回应信息
 * */

/*
 * 	在一次测试的最多五次连接里面，客户端逐步采集 Msg信息，每次采集完后填充一部分
 * 	MsgEntry对象的生命周期:
 * 	开始：	一个客户端在	点击第一个按钮: 登录或者注册  之后
 * 	结束：	客户端退出登录之后 , 可以使合法的完成测试在结束, 也可以是非法的
 * 	MsgEntry生命历程:
 * 	用户面板			操作者		操作		成员变量
 *  LoginPanel		Client		add		id,	psw;
 *  UserCofigPanel	Client		add		problemNums, probelmLevs;
 *  AnswerPanel		Server		add		queue of answers;	每一个元素是一个answer类
 *  ReportPAnel		Client 		add		correctNums;
 * */

/*
 * 	发送端：
 * 	view -> MsgEntry:		使用set方法将view层的用户输入封装到该对象中，填充Msg
 * 							之所以用分散的set()方法是因为, Msg对象是逐步填充的
 * 	MsgEntry -> socket		new Coder(socket, Msg);
 * 							获得已经绑定数据的socket 
 * 	接收端：
 * 	socket -> MsgEntry		MsgEntry msg = DeCoder(socket);	socket是接收端的socket
 *  MsgEntry -> 封装成员变量 	使用get()方法获得该MsgEntry对象的各个成员变量
 * */

/*
 * 	尝试将该类设计成 客户端 和 服务器端的公共类
 * 	User Problem MsgEntry	-> C-S公共类
 * */

/*
 *  设计出现了错误
 *  实际上我们应该在网络Msg层和view层中间再设计一个类
 *  这个中间类执行的功能是收集view层的数据将其包装成该类
 *  而这个中间类在客户端和服务器端是不同的
 *  用来屏蔽客户端和服务器在底层数据结构的不同
 *  
 *  网络层的Msg类应该在C-S端是相同的，以进行Code和Decode操作
 *
 *  而我们在这个简单的设计中使用特定分隔符的String来实现
 *  从而将中间层和Msg层柔和到一起
 *  
 *  但是客户端和服务器端的数据结构是不一样的这个问题
 * */

/*
 * 	关于数据包的定长还是变长
 * 	目前使用的是变长数据包	, 每一个成员变量代表传输的字段	
 * 
 * 	每一个字段只能通过外部填充, 构造函数不会对其初始化, 所以未填充时为默认值
 * 	
 * 	有利于协议的拓展
 * 	比如增加一个消息类型:	只需要重定义MsgEntry类, 其余代码不需要更改
 * 		在Contants中定义该类型的头 常量
 * 		在MsgEntry类定义中增加该类型消息所必要的字段
 * 		拓展Msg2Str() 和 Str2Msg方法()	: 在switch语句中增加一个case, 序列化/反序列化该字段
 * 	
 * 	不好:
 * 		健壮性差		按照字段集合 转化为strs[]按下标访问
 * 		如果某一个字段未填比如ID为null, 那么由于该字段为null, 解析时会将ID的下一字段psw解析为ID
 * 		也会导致解析时数组极易越界
 * 	解决办法：
 * 		在发送MsgEntry的地方, 按head检查相应内容其是否为空, 若为空那么不发送, 提示错误
 * */

/*
 * 	server端将MsgEntry与SData区分开来
 * 	仅仅在网络通信的时候使用新建的MsgEntry, 生命周期为一次通信, 一个
 * 	sData记录
 * */
public class MsgEntry extends MsgHeadEntry{
	/*
	 * 	本次通讯的成功与否
	 * 	状态码有Constant中定义
	 * */
	private int state;
	
	/*
	 * 	Client to Server:
	 * */
	private String ID;			
	private String psw;			
	private int problemNums;	//本次测试中用户请求的总题目数
	private int problemLevs;	//本次测试中用户请求的
	/*
	 * 	使用一个ArrayList是因为在AnswerPanel会用到它做题目索引界面
	 * 	会出现随即索引的要求
	 * 	ArrayList 随即访问快速	在指定的地方插入对象或者删除对象较慢
	 * */
	private ArrayList<Answer> ansList;
	int correctNums;
	
	public MsgEntry() {
		head = LOGIN;
		//容器类要初始化, 其内部数据填充分散在get、set方法
		//不然会出现NullPointerException
		ansList = new ArrayList<Answer>();
	}
	public MsgEntry(int head) {
		this.head = head;
		ansList = new ArrayList<Answer>();
	}
	
	/*
	 * 	由Msg类得到String类, 只有发送方在发送socket的时候会使用
	 * 	客户端	: Login	Reg	Req	Upd Exit
	 * 	服务器	: res
	 * 	break千万不能少
	 * */
	@Override
	public String Msg2Str() {
		String res = null;
		switch (head) {
			case LOGIN: case REG:
				res = head + SPLITER + ID + SPLITER + psw;
				break;
			case REQ:
				res = head + SPLITER + ID + SPLITER + problemNums + SPLITER + problemLevs;
				break;
			case UPD:
				res = head + SPLITER + ID + SPLITER + problemNums + SPLITER + correctNums;
				break;
			case EXIT:
				res = head + SPLITER + ID;
				break;
			case LOGIN_RES: case REG_RES: case UPD_RES: case EXIT_RES:
				res = head + SPLITER + state;
				break;
			case REQ_RES:
				//将题目队列(从mySql中得到)序列化编码
				res = head + SPLITER + CodeProblems() + SPLITER + state;
				break;
			default:
				break;
		}
		return res;
	}
	
	/*
	 *  只有在接收端会调用该方法
	 *  客户端	: RES
	 *  服务器端	: LOGIN REG REQ UPD EXIT
	 * */
	@Override
	public MsgEntry Str2Msg(String str) {
		//通过分隔符得到head
		String [] strs = str.split(SPLITER);
		//新建一个消息处理
		if (strs != null) {
			setHead(Integer.parseInt(strs[0]));
		}else {
			System.out.println("消息未定义, 分割字符串出错");
			return null;
		}
		switch( getHead() ) {
			case LOGIN_RES: case REG_RES: case UPD_RES: case EXIT_RES:
				setState(Integer.parseInt(strs[1]));
				break;
			case REQ_RES:
				//得到题目队列 从序列里面解码
				DecodeProblems(strs);
				setState(Integer.parseInt(strs[strs.length-1]));
				break;
			case LOGIN:	case REG://id psw
				setID(strs[1]).setPsw(strs[2]);
				break;
			case REQ:	//id, pNums, pLevs
				setID(strs[1]);
				setProblemNums(Integer.parseInt(strs[2]));
				setProblemLevs(Integer.parseInt(strs[3]));
				break;
			case UPD:	//id, pNums, correctNums
				setID(strs[1]);
				setProblemNums(Integer.parseInt(strs[2]));
				setCorrectNums(Integer.parseInt(strs[3]));
				break;
			case EXIT:
				setID(strs[1]);
				break;
			default:
				break;
		}
		return this;	//本方法的调用者, 也就是这个MsgEntry实体对象
	}
	
	/* 	
	 * 	客户端类数据网络传输编码
	 * 	ArrayList<Problem>problems		Req、ReqRes
	 * 	
	 * 	每一个problem:
	 * 		seq + INNER_SPLITER + content + INNER_SPLITER + answer
	* 		+ INNER_SPLITER + score + INNER_SPLITER + level + SPLITER;
	*/
	//使用 strs填充ansList的Problem域
	//每一个strs[i]对应一个problem的类的成员变量, 使用INNER_SPLITER分割
	
	private void DecodeProblems(String [] strs) {
		/*	
		 * 	strs[0]->head
			strs[strs.length-1]->state
			strs[strs.length-2]->""		由于Problem的toString()方法在每个类的最后添加一各SPLITER
			那么ArrayList<Problem> 的toString()方法就会在序列的最后面加一个多余的SPLTER
		*/
		int endInx = strs.length-2;
		for(int idx=1; idx<endInx; idx++) {
			//ss是一个problem对应的信息
			String[] ss = strs[idx].split(INNER_SPLITER);
			for(String s: ss) {
				System.out.print(s+"\t");
			}
			System.out.println();
			Problem p = new Problem();
			p.setSeq(Integer.parseInt(ss[0]));
			p.setContent(ss[1]);
			p.setAnswer(ss[2]);
			p.setScore(Integer.parseInt(ss[3]));
			p.setLevel(Integer.parseInt(ss[4]));
			//向数据域插入一个没有回答的answer
			addAnswer(new Answer(p, null));
 		}
	}
	
	private String CodeProblems(){
		String res = "";
		for(Answer ans: ansList) {
			Problem p = ans.getProblem();
			res += p.toString();
		}
		return res;
	}
	
	//set 方法每一个都返回自身引用，所以可以连续赋值
	//由于各个方法不同，所以需要将msg的赋值方式下放到各个应用场景
	//set方法负责将客户端的用户界面层数据封装到MsgEntry类内
	public MsgEntry setState(int state) {
		this.state = state;
		return this;
	}
	public MsgEntry setID(String id) {
		ID = id;
		return this;
	}
	public MsgEntry setPsw(String psw) {
		this.psw = psw;
		return this;
	}
	public MsgEntry setProblemNums(int problemNums) {
		this.problemNums = problemNums;
		return this;
	}
	public MsgEntry setProblemLevs(int level) {
		this.problemLevs = level;
		return this;
	}
	public MsgEntry setCorrectNums(int correctNums) {
		this.correctNums = correctNums;
		return this;
	}
	//向答案队列末尾增加一项
	private void addAnswer(Answer ans) {
		ansList.add(ans);
	}
	//向序列号为seq的问题增加答案ans
	public MsgEntry setAnswer(int seq, String ans) {
		ansList.get(seq).getProblem().setAnswer(ans);
		return this;
	}
	
	public int getState() {
		return state;
	}
	public String getID() {
		return ID;
	}
	public String getPsw(){
		return psw;
	}
	public int getProblemNums(){
		return problemNums;
	}
	public int getProblemLevs(){
		return problemLevs;
	}
	public int getCorrectNums(){
		return correctNums;
	}
	public ArrayList<Answer> getAnsList() {
		return ansList;
	}
	
	public MsgEntry initAnsList() {
		ansList = new ArrayList<Answer>();
		return this;
	}
}
