package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.net.Socket;

import viewKit.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Constant.Constants;
import Net.ClientNet;
import data.CCommonData;
import data.MsgEntry;
import netKit.Coder;
import netKit.DeCoder;
import test.TestPanels;

/*
 * 组件命名规则		组件名称 + 后缀
 * 名称 ：
 * 用户名	id
 * 密码		psw
 * 登录		login
 * 注册		reg
 * JTextField	Fld
 * JButton		But
 * Jlabel		Lbl
 * 
 * Window(JFram)		Wnd
 * MsgEntry		Msg
 * ClientNet	CNet
 * */

public class LoginPanel extends BgPanel {
	//下面三个参数为外部传入, 与其他面板保持一致, 信息交换
	public static ClientWindow cWnd;//全局的窗体管理 	用于做面板切换(Listener中使用)
	public static ClientNet cNet;	//全局的网络管理器
	public static CCommonData cData;
	
	//private MsgEntry serverMsg;	//处理服务器来的消息, 内部使用
	//组件和布局
	private final int WIDTH = 500;
	private final int HEIGHT = 300;
	//label
	private JLabel idLbl;
	private JLabel pswLbl;
	//id域 	psw域	login按钮 	register按钮
	private JTextField idFld;
	private JPasswordField pswFld;
	private JButton loginBtn;
	private JButton regBtn;
	
	public LoginPanel() {
		/*
		 * 在此可以增加参数：img的URL,默认URL为"img/login.png"
		 * super(url);
		 * */
		super();
		
		idFld = new JTextField(15);
		pswFld = new JPasswordField(15);
		loginBtn = new JButton("登录");
		regBtn = new JButton("注册");
		idLbl = new JLabel("用户名");
		pswLbl = new JLabel("密码");
		
		//Bnt绑定监听
		loginBtn.addMouseListener(new MyLoginListener());
		regBtn.addMouseListener(new MyRegListener());
		
		this.setLayout(new GridBagLayout());
		this.add(idLbl,new GBC(0, 0)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		this.add(pswLbl,new GBC(1,0)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		this.add(regBtn,new GBC(2,0)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		this.add(idFld,new GBC(0,1)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		this.add(pswFld,new GBC(1,1)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		this.add(loginBtn,new GBC(2,1)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		
		setSize(WIDTH, HEIGHT);
	}
	
	/*
	 * 	登录按钮  注册按钮点击事件 点击事件
	 * 	填充消息实体->编码Msg->发送socket
	 * 	接收socket->解码填充Msg->验证信息->提示用户
	 * */
	class MyLoginListener extends MouseAdapter implements Constants{
		public void mouseClicked(MouseEvent evt){
			CheckInput judgeInput = new CheckInput();
			CheckState judgeState = new CheckState(idFld.getParent());
			if( judgeInput.setComponent(idFld).getResult("用户名不能为空") 
				&& judgeInput.setComponent(pswFld).getResult("密码不能为空") ) 
			{
				//此时id和psw内容非空
				MsgEntry sendMsg = cData.getMsgEntry();
				sendMsg.setID(idFld.getText()).setPsw(new String(pswFld.getPassword()));//填充msg
				sendMsg.setHead(LOGIN);
				cNet.openNet();
				cNet.sendMsg(cNet.getSocket(), sendMsg);
				MsgEntry recvMsg = cNet.recvMsg(cNet.getSocket());
				cNet.closeNet();
				//按照返回的state分析并给出用户回应
				if ( judgeState.setState(recvMsg.getState()).getResult("登录") ) {
					 cData.setIsOnLine(true);
					 cData.setMsgEntry(sendMsg);
					 cWnd.switchToPanel(new UserConfigPanel());
				}else {
					return;
				}
			}
		}
	}
	
	class MyRegListener extends MouseAdapter implements Constants{
		public void mouseClicked(MouseEvent evt){
			CheckInput judgeInput = new CheckInput();
			CheckState judgeState = new CheckState(idFld.getParent());
			if( judgeInput.setComponent(idFld).getResult("用户名不能为空") 
				&& judgeInput.setComponent(pswFld).getResult("密码不能为空") ) 
			{
				//此时id和psw内容非空
				MsgEntry sendMsg = new MsgEntry();
				sendMsg.setID(idFld.getText()).setPsw(new String(pswFld.getPassword()));//填充msg
				sendMsg.setHead(REG);
				cNet.openNet();
				cNet.sendMsg(cNet.getSocket(), sendMsg);
				MsgEntry recvMsg = cNet.recvMsg(cNet.getSocket());
				cNet.closeNet();
				//按照返回的state分析并给出用户回应
				if ( judgeState.setState(recvMsg.getState()).getResult("注册") ) {
					 //cWnd.switchToNextPanel();
					//不修改状态cData
				}else {
					//这里必须要有一个return 从监听状态出去, 否则一直处于监听状态卡死
					return;
				}
			}
		}
	}
}
