package test;

import java.io.Closeable;
import java.net.Socket;

import javax.swing.JFrame;

import data.MsgEntry;
import view.*;
import viewKit.*;

public class TestPanels extends JFrame{
	private LoginPanel loginPanel;
	private UserConfigPanel userConfigPanel;
	private AnswerPanel answerPanel;
	private ReportPanel reportPanel;
	private Socket socket;
	private MsgEntry msg;
	
	public TestPanels() {
		super();
		socket = new Socket();
		msg = new MsgEntry();
		
		//loginPanel = new LoginPanel(this, cNet, msg);
		userConfigPanel = new UserConfigPanel();
		answerPanel = new AnswerPanel();
		reportPanel = new ReportPanel();
		
		this.setContentPane(loginPanel);
		//this.setContentPane(userConfigPanel);
		//this.setContentPane(answerPanel);
		//this.setContentPane(reportPanel);
		setSize(this.getContentPane().getSize());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		new ShowCenter(this);
	}
	
	public static void main(String[] args) {
		new TestPanels();
	}
		
	public void switchToNextPanel(){
		if(getContentPane() == loginPanel)	setContentPane(userConfigPanel);
		else if(getContentPane() == userConfigPanel)	setContentPane(answerPanel);
		else if(getContentPane() == answerPanel)	setContentPane(reportPanel);
		else dispose();		//关闭当前窗口
	}
}
