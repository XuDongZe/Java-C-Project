package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Constant.Constants;
import Net.ClientNet;
import data.CCommonData;
import data.MsgEntry;
import Config.Config;
import viewKit.ShowCenter;


public class ClientWindow extends JFrame{
	private ClientNet cNet;		//客户端网络连接管理
	/*	客户端本地数据
	 * 	只传递给需要的面板即可
	 */
	private CCommonData cData;	
	
	//四个面板	客户端界面管理
	private LoginPanel loginPanel;
	private UserConfigPanel userConfigPanel;
	private AnswerPanel answerPanel;
	private ReportPanel reportPanel;
	
	public ClientWindow() {
		super("Client");
		//test 192.168.137.1
		String ip = Config.properties.getProperty("IP");
		int port = Integer.parseInt( Config.properties.getProperty("PORT") );
		cNet = new ClientNet(ip, port);
		cData = new CCommonData();
		
		//必须要在Panels实例化之前实例化其静态变量
		addManagersToPanel();
		
		loginPanel = new LoginPanel();
		userConfigPanel = new UserConfigPanel();
		answerPanel = new AnswerPanel();
		//reportPanel = new ReportPanel();
		
		setContentPane(loginPanel);
		setSize(getContentPane().getSize());
		setVisible(true);
		addWindowListener(new myExitListener());
		new ShowCenter(this);
	}
	
	/* 
	 * 	公共的控制视图的方法
	 * */
	public void switchToPanel(JPanel panel){
		if(panel == null) System.exit(0);
		
		setContentPane(panel);
		setSize(getContentPane().getSize());
		new ShowCenter(this);
	}
	
	/*
	 * 	对每一个附属的panel增加信息管理器、网络管理器、窗口视图管理器
	 * 	要求每一个面板首先要初始化
	 * 	然后都得具有以下public的成员
	 * */
	private void addManagersToPanel(){
		LoginPanel.cWnd = this;
		LoginPanel.cNet = cNet;
		LoginPanel.cData = cData;
		
		UserConfigPanel.cWnd = this;
		UserConfigPanel.cNet = cNet;
		UserConfigPanel.cData = cData;
		
		AnswerPanel.cWnd = this;
		AnswerPanel.cNet = cNet;
		AnswerPanel.cData = cData;
		
		ReportPanel.cWnd = this;
		ReportPanel.cNet = this.cNet;
		ReportPanel.cData = cData;
	}
	
	/*
	 * 	直接在Window级别监视非exitBnt退出
	 * */
	private class myExitListener extends WindowAdapter implements Constants{
		@Override
		public void windowClosing(WindowEvent evt) {
			if( cData.getIsOnline() ) {	//服务器端在线列表中已有该用户项
				MsgEntry sendMsg = cData.getMsgEntry().setHead(EXIT);
				if( cNet.isClosed() )	cNet.openNet();
				cNet.sendMsg(cNet.getSocket(), sendMsg);
				//因为使用TCP, 所以一定会发送到对端, 所以发送完毕立刻关闭, 不进行握手 
				cNet.closeNet();
				cData.setIsOnLine(false);
			}
			System.exit(0);	//视为正常退出
		}
	}	
	
	public static void main(String[] args) {
		new ClientWindow();
	}
}
