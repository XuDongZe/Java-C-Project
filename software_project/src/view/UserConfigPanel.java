package view;

import viewKit.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Constant.Constants;
import Net.ClientNet;
import data.CCommonData;
import data.MsgEntry;

/*
 * 组件命名规则
 * p前缀：problem缩写
 * 后缀：
 * JLabel 		Lbl
 * JTextField	Fld
 * Jbutton		Btn
 * JComboBox 	Box
 * pNums	问题数量
 * pLevs	问题等级
 * */

/*
 * 在pLevsFld中焦点存在时应该给予提示：有哪些等级以及等级顺序
 * */

public class UserConfigPanel extends BgPanel{
	
	public static ClientWindow cWnd;
	public static ClientNet cNet;
	public static CCommonData cData;
	
	private final int WIDTH = 500;
	private final int HEIGHT = 300;
	
	//label
	private JLabel pNumsLbl;
	private JLabel pLevsLbl;
	//id域 	psw域	login按钮 	register按钮
	private JTextField pNumsFld;
	private final int MAXLEVEL = 5;
	private JComboBox<Integer> pLevsBox;
	private JButton startBtn;
	
	public UserConfigPanel() {
		/*
		 * 在此可以增加参数：img的URL,默认URL为"imgs/login.png"
		 * super(url);
		 * */
		super();
		System.out.println("in userconfigPanel:" + cData);
		pNumsFld = new JTextField(15);
		pLevsBox = new JComboBox<>();
		pNumsLbl = new JLabel("题目数量");
		pLevsLbl = new JLabel("题目难度");
		startBtn = new JButton("开始做题");
		for(int i=1; i<=MAXLEVEL; i++) {
			pLevsBox.addItem(i);
		}
		
		startBtn.addMouseListener(new MyStartListener());
		
		this.setLayout(new GridBagLayout());
		this.add(pNumsLbl,new GBC(0, 0)
				.setFill(GridBagConstraints.HORIZONTAL)
				.setAnchor(GridBagConstraints.WEST));
		this.add(pLevsLbl,new GBC(1,0)
				.setFill(GridBagConstraints.HORIZONTAL)
				.setAnchor(GridBagConstraints.WEST));
		this.add(startBtn,new GBC(2,1)
				.setFill(GridBagConstraints.HORIZONTAL)
				);

		this.add(pNumsFld,new GBC(0,1)
				.setFill(GridBagConstraints.HORIZONTAL)
				.setAnchor(GridBagConstraints.EAST));
		this.add(pLevsBox,new GBC(1,1)
				.setFill(GridBagConstraints.HORIZONTAL)
				.setAnchor(GridBagConstraints.EAST));
		
		setSize(WIDTH, HEIGHT);
	}
	
	class MyStartListener extends MouseAdapter implements Constants{
		public void mouseClicked(MouseEvent evt){
			CheckInput judgeInput = new CheckInput();
			CheckState judgeState = new CheckState(pNumsFld.getParent());
			if( judgeInput.setComponent(pNumsFld).getResult("题目数量不能为空") ) 
			{
				//填充msg
				int pNums = Integer.parseInt(pNumsFld.getText());
				int pLevs = (int) pLevsBox.getSelectedItem();
				MsgEntry sendMsg = cData.getMsgEntry();
				sendMsg.setProblemNums(pNums).setProblemLevs(pLevs);
				sendMsg.setHead(REQ);
				cNet.openNet();
				cNet.sendMsg(cNet.getSocket(), sendMsg);
				MsgEntry recvMsg = cNet.recvMsg(cNet.getSocket());
				cNet.closeNet();
				//按照返回的state分析并给出用户回应
				if ( judgeState.setState(recvMsg.getState()).getResult("请求题目") ) {
					cData.setMsgEntry(sendMsg);
					cData.setMsgEntry(recvMsg);
					
					cWnd.switchToPanel(new AnswerPanel());
				}else {
					return;
				}
			}
		}
	}
}
