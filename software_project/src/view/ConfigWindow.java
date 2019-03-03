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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Config.Config;
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

public class ConfigWindow extends JFrame {
	
	//组件和布局
	private final int WIDTH = 500;
	private final int HEIGHT = 300;
	//label
	private JLabel ipLbl;
	private JLabel portLbl;
	//id域 	psw域	login按钮 	register按钮
	private JTextField ipFld;
	private JTextField portFld;
	private JButton changeBtn;
	private JButton clearBtn;
	private BgPanel bgPanel;
	
	public ConfigWindow() {
		/*
		 * 在此可以增加参数：img的URL,默认URL为"img/login.png"
		 * super(url);
		 * */
		super("当前显示为原IP, 请按按钮修改");
		
		String oldIP = Config.properties.getProperty("IP");
		String oldPort = Config.properties.getProperty("PORT");
		
		ipFld = new JTextField(15);
		portFld = new JTextField(15);
		changeBtn = new JButton("修改");
		clearBtn = new JButton("清空");
		ipLbl = new JLabel("ip地址:");
		portLbl = new JLabel("端口号:");
		bgPanel = new BgPanel();
		
		ipFld.setText(oldIP);
		portFld.setText(oldPort);
		//Bnt绑定监听
		changeBtn.addMouseListener(new MyChangeListener());
		clearBtn.addMouseListener(new MyClearListener());
		
		bgPanel.setLayout(new GridBagLayout());
		bgPanel.add(ipLbl,new GBC(0, 0)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		bgPanel.add(portLbl,new GBC(1,0)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		bgPanel.add(clearBtn,new GBC(2,0)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		bgPanel.add(ipFld,new GBC(0,1)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		bgPanel.add(portFld,new GBC(1,1)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		bgPanel.add(changeBtn,new GBC(2,1)
				.setFill(GridBagConstraints.BOTH)
				.setAnchor(GridBagConstraints.WEST));
		
		setContentPane(bgPanel);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		new ShowCenter(this);
	}
	
	class MyChangeListener extends MouseAdapter implements Constants{
		public void mouseClicked(MouseEvent evt){
			CheckInput judgeInput = new CheckInput();
			if( judgeInput.setComponent(ipFld).getResult("ip 地址不能为空") 
				&& judgeInput.setComponent(portFld).getResult("端口号不能为空") ) 
			{
				if( JOptionPane.showConfirmDialog(ipFld.getParent(), "修改成功!") 
						== JOptionPane.YES_OPTION ) {
				Config.flushConfig("IP="+ipFld.getText()+"\n"+
						"PORT="+portFld.getText());
				}
			}
		}
	}
	
	class MyClearListener extends MouseAdapter implements Constants{
		public void mouseClicked(MouseEvent evt){
			ipFld.setText("");
			portFld.setText("");
		}
	}
	
	public static void main(String[] args) {
		new ConfigWindow();
	}
}

