package view;

import java.awt.PopupMenu;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import Constant.Constants;
import Net.*;
import viewKit.ShowCenter;

public class ServerWindow extends JFrame implements Constants{
	private static final int WIDTH = 900;
	private static final int HEIGHT = 500;
	/*
	 * 	网络管理
	 * 	ServerNet内部封装线程, 每个线程会封装一个处理客户端和数据库端的数据类: SmsgEntry
	 * 	ServerData与SmsgEntry表示的信息不同
	 *  ServerData维护服务器端的公有数据,比如当前在线用户列表等
	 *  sMsgEntry每个线程维护与该客户端相关的信息
	 * */
	
	//组件
	private JTextArea msgArea;
	private JComboBox<String> userBox;		//在线用户列表显示
	private JPanel infoPanel;				
	private JScrollPane scrollPane;	
	
	public ServerWindow() {
		super("Server");
		
		/*
		 * 	逻辑线程数据结构
		 * 	这个必须要在ServerManager对象实例化之前
		 * 	ServerManager对象由于具有监听阻塞线程, 其实例化必须要放到不同于main线程的线程中
		 * */
		ServerManager.faWnd = this;	
		
		/*
		 * 	视图线程视图布局
		 * */
		msgArea = new JTextArea( (int)(HEIGHT*0.6/12), (int)(WIDTH*0.9/12) );
		msgArea.setEditable(false);		//服务器端只显示, 不可写
		scrollPane = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		userBox = new JComboBox<String>();
		userBox.add(new PopupMenu());
		infoPanel = new JPanel();
		infoPanel.add(userBox);
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		add(scrollPane);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 5, SpringLayout.WEST, this);
		add(infoPanel);
		layout.putConstraint(SpringLayout.WEST, infoPanel, 5, SpringLayout.EAST, scrollPane);
		layout.putConstraint(SpringLayout.NORTH, infoPanel, 5, SpringLayout.NORTH, this);
		
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setResizable(false);
		new ShowCenter(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		 * 	服务器端的网络线程是一直运行的
		 * 	所以一定要放到构造函数的最后面
		 * 	否则会阻塞该线程后面的方法
		 * */
		Thread thread = new Thread(new ServerManagerThread());
		thread.start();
	}
	
	/*
	 * 	网络线程, 处理服务器端网络功能
	 * */
	class ServerManagerThread implements Runnable{
		@Override
		public void run() {
			new ServerManager(); 
		}
	}
	
	/*
	 * 	组件更新接口
	 * 	appendToMsgArea:	向msgArea增添信息	服务器窗口日志
	 * 	appendToUserBox:	向userBox增添信息	在线用户列表显示
	 * 	
	 * 	甚至我们可以做在线用户列表的 按字典序排序、 搜索、 图标JComboBox显示(类似QQ)
	 * */
	public void appendToMsgArea(String str){
		msgArea.append(str +"\n");
	}
	public void appendToUserBox(String str) {
		userBox.addItem(str);
	}
	public void deleteFromUserBox(String str) {
		//因为在线用户经过检查id是一样的
	}
	
	//主函数main方法, 启动ServerWnd
	public static void main(String[] args) {
		new ServerWindow();
	}
}
