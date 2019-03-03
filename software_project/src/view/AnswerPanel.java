package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.KeyStore;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Constant.Constants;
import Net.ClientNet;
import data.Answer;
import data.CCommonData;
import data.MsgEntry;
import data.Problem;
import viewKit.CheckState;

/*
 * 组件命名规则
 * 后缀：
 * JTextArea		Area
 * JTextField	 	Fld
 * JButton			Btn
 * JScrollPane		Panel
 * JLabel			Lbl
 * JComboBox		Box
 * 组件：
 * id:	当前用户名
 * proc 		题目进度 a/b形式
 * solvedNums	已完成题目数
 * sumNums		本次测试总题目数
 * show			题目显示区域
 * input		答题区域
 * search		搜索区域
 * prev			上一题按钮
 * next			下一题按钮
 * submit		提交按钮
 * */

/*
 * UserConfigPanel 到 AnswerPanel 中间的接口是  题目队列
 * 由服务器返回，客户端解析得到
 * */

public class AnswerPanel extends JPanel{

	public static ClientWindow cWnd;
	public static ClientNet cNet;
	public static CCommonData cData;
	
	private final int WIDTH = 700;
	private final int HEIGHT = 600;
	
	//组件
	private JLabel idLbl;
	private JLabel procLbl;
	private JLabel inputLbl;
	private JLabel showLbl;
	private JScrollPane showPanel;
	private JTextArea showArea;
	private JComboBox<Integer> searchBox;	//使用int索引，第一题为 1，一直到sunNums
	private JTextArea inputArea;
	private JScrollPane inputPanel;
	
	private JPanel ctrPanel;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton saveBnt;
	private JButton submitBtn;
	
	public AnswerPanel() {
		super();
		MsgEntry cMsg = cData.getMsgEntry();
		inputLbl = new JLabel("请输入您的答案:");
		showLbl = new JLabel("题目描述:");
		idLbl = new JLabel("当前用户: " + cMsg.getID() + "\t");
		procLbl = new JLabel("当前进度: " + cData.getSolvedNums() + "/" + cMsg.getProblemNums());
		//rows cols
		showArea = new JTextArea( (int)(HEIGHT*0.5/this.getFont().getSize()), 
				(int)(WIDTH*0.75/this.getFont().getSize()) );
		inputArea = new JTextArea( (int)(HEIGHT*0.2/this.getFont().getSize()), 
				(int)(WIDTH*0.75/this.getFont().getSize()) );
		
		searchBox = new JComboBox<Integer>();
		for(int i=1; i<=cMsg.getProblemNums(); i++)	searchBox.addItem(i);
		
		ctrPanel = new JPanel();
		prevBtn = new JButton("上一题");
		nextBtn = new JButton("下一题");
		saveBnt = new JButton("保存");
		submitBtn = new JButton("提交");
		
		//绑定按钮监听器
		prevBtn.addMouseListener(new MyPrevBntListener());
		nextBtn.addMouseListener(new MyNextBntListener());
		saveBnt.addMouseListener(new MySaveBntListener());
		searchBox.addItemListener(new MySearchBoxListener());
		submitBtn.addMouseListener(new MySubmitBntListener());
		
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);
		
		//标题信息栏 ： 用户id + 做题进度
		JPanel titlePanel = new JPanel();
		titlePanel.add(idLbl);
		titlePanel.add(procLbl);
		this.add(titlePanel);
		springLayout.putConstraint(SpringLayout.WEST, titlePanel, 5, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, titlePanel, 5, SpringLayout.NORTH, this);
		
		//题目描述去label
		this.add(showLbl);
		springLayout.putConstraint(SpringLayout.WEST, showLbl, 5, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, showLbl, 10, SpringLayout.SOUTH, titlePanel);
		
		//题目描述区：只读
		showArea.setEditable(false);//题目描述自然不可写
		showArea.setLineWrap(true);//换行不断字
		showArea.setLineWrap(true);	//激活自动换行
		
		//竖直方向按需滚动，水平方向不滚动
		showPanel = new JScrollPane(showArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(showPanel);
		springLayout.putConstraint(SpringLayout.WEST, showPanel, 5, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, showPanel, 5, SpringLayout.SOUTH, showLbl);
		
		//题目搜索区
		this.add(searchBox);
		springLayout.putConstraint(SpringLayout.WEST, searchBox, 5, SpringLayout.EAST, showPanel);
		springLayout.putConstraint(SpringLayout.NORTH, searchBox, 5, SpringLayout.SOUTH, titlePanel);
		springLayout.putConstraint(SpringLayout.EAST, searchBox, -5, SpringLayout.EAST, this);
		
		//作答label
		this.add(inputLbl);
		springLayout.putConstraint(SpringLayout.NORTH, inputLbl, 10, SpringLayout.SOUTH, showPanel);
		springLayout.putConstraint(SpringLayout.WEST, inputLbl, 5, SpringLayout.WEST, this);
		
		//作答区域
		inputArea.setLineWrap(true);//换行不断字
		inputArea.setLineWrap(true);	//激活自动换行
		//竖直方向按需滚动，水平方向不滚动
		inputPanel = new JScrollPane(inputArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(inputPanel);
		springLayout.putConstraint(SpringLayout.WEST, inputPanel, 5, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, inputPanel, 5, SpringLayout.SOUTH, inputLbl);
		springLayout.putConstraint(SpringLayout.SOUTH, inputPanel, -5, SpringLayout.SOUTH, this);
		
		//控制面板, 集成 上一题、下一题、提交按钮
		//布局采用
		
		ctrPanel.setLayout(new BorderLayout());
		ctrPanel.add(prevBtn, BorderLayout.NORTH);
		ctrPanel.add(saveBnt, BorderLayout.CENTER);
		ctrPanel.add(nextBtn, BorderLayout.SOUTH);
		ctrPanel.add(submitBtn, BorderLayout.EAST);
		this.add(ctrPanel);
		springLayout.putConstraint(SpringLayout.WEST, ctrPanel, 5, SpringLayout.EAST, inputPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, ctrPanel, -5, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, ctrPanel, -5, SpringLayout.EAST, this);
		
		this.setSize(WIDTH, HEIGHT);
		
		showProblemAt(cData.getCurrentPNo());
		
	}
	
	/*
	 * 	对组件showArea的操作	组件对题目编号从1开始, 题目从
	 * 	数据结构cData.getMsgEntry().getAnsList()
	 * */
	/*
	 * 	序列号seq是数据库编号		从0开始
	 * 	pNo是客户端题目队列编号	从0开始
	 * */
	private void showProblemAt(int pNo) {
		Answer ansEntry = null;
		//超出数组界限: 第一题找上一题, 最后一题点下一题: 不做操作
		if( (ansEntry = cData.getUserAnswerEntry(pNo)) != null ) {
			switchToProblem(ansEntry, pNo);
		}else {
			System.out.println("in showProblemAt: get a null p " + pNo);
		}
	}
	
	/*
	 * 	由于切换题目对视图的更改
	 * 	1.	inputfield的内容		切换为当前题目内容
	 * 	2.	inputfiels			自动获得焦点
	 * 	3.	searchBox			切换为当前题目编号
	 * */
	private void switchToProblem(Answer ans, int pNo) {
		//当前题目队列指针
		cData.setCurrentPNo(pNo);
		//题目显示区域
		showArea.setText("\n\n\n" + "第 【" + (pNo+1) + "】 题:\n\n\t\t" +
							ans.getProblem().getContent() + 
							"\n\n你的答案:\n\n" +
							ans.getUserAns());
		//用户输入答案区域
		inputArea.requestFocus();	//得到焦点
		if( ans.getUserAns() == null ) {
			inputArea.setText("");
		}else {
			inputArea.setText(ans.getUserAns());
		}
		
		//procLabel
		procLbl.setText("当前进度: " + cData.getSolvedNums() + "/" + cData.getMsgEntry().getProblemNums());
		/*	searchBox设置选定项
		 * 	由于会调用 public void itemStateChanged(ItemEvent evt)方法
		 * 	但是这个我们在这里改写了该方法
		 */
		//searchBox.setSelectedItem(pNo);
	}
	
	private class MyNextBntListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			int currentPNo = cData.getCurrentPNo();
			showProblemAt(currentPNo + 1);
		}
	}
	
	private class MyPrevBntListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			int currentPNo = cData.getCurrentPNo();
			showProblemAt(currentPNo - 1);
		}
	}
	
	private class MySearchBoxListener implements ItemListener  {
		@Override
		public void itemStateChanged(ItemEvent evt) {
			// TODO Auto-generated method stub
			if(evt.getStateChange() == ItemEvent.SELECTED) {
				int cPno = (int)evt.getItem() - 1;
				showProblemAt(cPno);
			}
		}
	}
	
	private class MySaveBntListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			
			String inputStr = inputArea.getText();
			String userAns = inputStr.substring(0, inputStr.length());
			int pNo = cData.getCurrentPNo();
			if( cData.getMsgEntry().getAnsList().get(pNo).getUserAns() == null ) {
				System.out.println("您回答了一个新的题目");
				cData.setSolvedNums(cData.getSolvedNums() + 1);
			}
			cData.getMsgEntry().getAnsList().get(pNo).setUserAns(userAns);
			//判断对错
			if( cData.getMsgEntry().getAnsList().get(pNo).isCorrect() ) {
				cData.getMsgEntry().setCorrectNums(cData.getMsgEntry().getCorrectNums() + 1);
			}
			
			procLbl.repaint();
		}
	}
	
	/*
	 * 	提交按钮, 首先生成用户提示框
	 * 	提示框选择确定: 发送Upd MsgEntry包到服务器
	 * 	服务器更新用户信息: sumPNums, correnctNums 到数据库
	 * */
	private class MySubmitBntListener extends MouseAdapter implements Constants{
		@Override
		public void mouseClicked(MouseEvent evt) {
			CheckState judgeState = new CheckState(showArea.getParent());
			int choice = JOptionPane.showConfirmDialog(cWnd, 
					"提交后您的数据将被上传到服务器端, 确定继续吗?", "提示",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if ( choice == JOptionPane.YES_OPTION ) {	//用户选择确定
				MsgEntry sendMsg = cData.getMsgEntry();
				sendMsg.setCorrectNums(cData.getMsgEntry().getCorrectNums());//填充msg
				sendMsg.setHead(UPD);
				cNet.openNet();
				cNet.sendMsg(cNet.getSocket(), sendMsg);
				MsgEntry recvMsg = cNet.recvMsg(cNet.getSocket());
				cNet.closeNet();
				//按照返回的state分析并给出用户回应
				if ( judgeState.setState(recvMsg.getState()).getResult("更新用户数据库") ) {
					 cData.setMsgEntry(sendMsg);
					 cWnd.switchToPanel(new ReportPanel());
				}else {
					return;
				}
			}
		}
	}
	
}
