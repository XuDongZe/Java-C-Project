package test;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Constant.Constants;
import Dao.MySQLDao;
import Net.*;
import data.Problem;
import data.SCommonData;
import data.User;

public class TestServer extends JFrame implements Constants{
	
	private JTextArea msgArea;
	private JScrollPane scrollPane;
	
	public TestServer() {
		super();
		//serverNet = new ServerNet(this);
		msgArea = new JTextArea();
		scrollPane = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		setLayout(new BorderLayout());
		add(scrollPane);
		setSize(500, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new TestServer();
		User user1 = new User().setID("xudongze");
		User user2 = new User().setID("xudongze");
		SCommonData sData = new SCommonData();
		MySQLDao DAO = new MySQLDao();
		for(int i=DAO.queryLastSeq(); i<=DAO.queryLastSeq()+10; i++)
			DAO.regProblem(new Problem(i));
		System.out.println(user1.hashCode()  + ", " + user2.hashCode());
	}
}
