package Constant;

public interface Constants {
	
	//连接服务器端口
	static final int PORT = 8988;
	//协议常量
	//登录
	static final int LOGIN = 10;
	static final int LOGIN_RES = 11;		//登录回应
	//注册
	static final int REG = 20;
	static final int REG_RES = 21;
	//请求发放题目队列
	static final int REQ = 30;
	static final int REQ_RES = 31;
	//更新用户信息
	static final int UPD = 40;
	static final int UPD_RES = 41;
	//退出登录
	static final int EXIT = 50;
	static final int EXIT_RES = 51;
	
	//自定义 状态
	static final int STATE_SUCCESS = 1;		//不能用0,, 因为0是默认值
	static final int STATE_ERROR = -1;		//一般错误(无法知道错误原因)
	static final int STATE_NO_EXIST = -2;	//用户名不存在
	static final int STATE_NO_MATCH = -3;	//用户名与密码不匹配
	static final int STATE_ALREADY_EXIST = -4;	//注册时用户ID已存在
	static final int STATE_ALREADY_LOGIN = -5;	//登录时用户已经在线
	static final int STATE_UPD_MYSQL_ERROE = -6; //更新用户时数据库操作失败
	static final int STATE_REQ_PROBLEM_NOT_ENOUGH = -7; //请求题目时题库题目太少, 自动更新, 请重新请求
	
	//自定义 信息间编码 间隔符
	//因为要与多个类型做拼接操作，所以使用String类型最好
	/*
	 * 	传输一个类:
	 * 	head + SPLITER + mem1 + INNER_SPLITER + mem2 + INNER_SPLITER + ... +
	 *  INNER_SPLITER + lastMem
	 *  传输一个单变量:
	 *  head + SPLITER + ID
	 * */
	static final String INNER_SPLITER = "&@";		//使用"&@"作为类内成员变量间隔符
	static final String SPLITER = "\t";				//使用"\t"作为类间或者单变量间隔符
	static final String SHOW_SPLITER = "  ";		//使用两个空格作为显示的时候的分隔符
	
}
