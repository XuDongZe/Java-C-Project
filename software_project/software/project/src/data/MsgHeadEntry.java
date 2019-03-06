package data;

import Constant.Constants;

public abstract class MsgHeadEntry implements Constants{
	protected int head;

	public MsgEntry setHead(int msgHead) {
		this.head = msgHead;
		return (MsgEntry) this;
	}
	public int getHead(){
		return this.head;
	}
	
	public MsgHeadEntry() {
		head = LOGIN;
	}
	
	//将消息内容按分隔符组织成string
	abstract String Msg2Str();
	abstract MsgHeadEntry Str2Msg(String str);
}
