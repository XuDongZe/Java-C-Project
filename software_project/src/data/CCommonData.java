package data;

import Constant.Constants;

/*
 * 	ClientWindow的共有数据
 * 	只保存独立于MsgEntry以外的数据, 也就是本地数据,
 * 	和网络通信无关的本地数据的集合
 * */

/*	
 * 	成员变量			来源				含义
 * 	solvedNums		anwserPanel 	用户当前已经做的题目	
 * 	isOnLine		loginPanel		服务器端用户已在线	
 * */
public class CCommonData implements Constants{
	private MsgEntry cMsg;		//记录用户整体的消息
	private int solvedNums;		//记录用户当前已经做的题目
	private int currentPNo;		//当前用户正在做得题目编号/指针
	private boolean isOnLine; 	//在服务器端用户已经在线
	public CCommonData(){
		isOnLine = false;
		solvedNums = 0;
		currentPNo = 0;
		cMsg = new MsgEntry();
	}
	
	public int getSolvedNums(){
		return solvedNums;
	}
	public void setSolvedNums(int pNums) {
		solvedNums = pNums;
	}
	public boolean getIsOnline() {
		return isOnLine;
	}
	public void setIsOnLine(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}
	
	public MsgEntry getMsgEntry() {
		return cMsg;
	}
	
	/*
	 * 	可能更改数据结构的操作
	 * */
	public void setMsgEntry(MsgEntry msg) {
		switch(msg.getHead()) {
		case LOGIN:	//sendMsg
			cMsg.setID(msg.getID()).setPsw(msg.getPsw());
			break;
		case REQ: //sendMsg
			cMsg.setProblemNums(msg.getProblemNums()).setProblemLevs(msg.getProblemLevs());
			break;
		case UPD:	//sendMsg
			cMsg.setCorrectNums(msg.getCorrectNums());
			break;
		case REQ_RES:	//recvMsg
			for(Answer ans: msg.getAnsList()) {
				System.out.println("add a problem " + ans.getProblem());
				cMsg.getAnsList().add(new Answer(ans.getProblem(), null));
			}
		break;
		default:
			break;
		}
	}
	
	public int getCurrentPNo() {
		return currentPNo;
	}
	public void setCurrentPNo(int pNo) {
		//越界不修改
		if(pNo >= 0 && pNo < cMsg.getAnsList().size()) {
			this.currentPNo = pNo;
		}else {
			System.out.println("越界! in setCurrentPNo: " + pNo + "size: " + cMsg.getAnsList().size());
		}
	}
	
	public Answer getUserAnswerEntry(int pNo) {
		if(pNo >=0 && pNo < cMsg.getAnsList().size())
			return cMsg.getAnsList().get(pNo);
		return null;
	}
	
}
