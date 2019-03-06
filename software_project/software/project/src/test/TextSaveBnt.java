package test;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class TextSaveBnt extends JFrame{
	
	private JTextField inputFld;
	private JButton saveBnt;
	private JButton showBnt;
	private String myAns;
	
	public TextSaveBnt(){
		
		super("TextSaveBnt");
		myAns = "";
		saveBnt = new JButton("保存");
		showBnt = new JButton("显示");
		inputFld = new JTextField();

		saveBnt.addMouseListener(new MySaveListener());
		showBnt.addMouseListener(new MyShowListener());
		saveBnt.setMnemonic(java.awt.event.KeyEvent.VK_S);
		showBnt.setMnemonic(java.awt.event.KeyEvent.VK_P);
		
		setLayout(new BorderLayout());
		add(inputFld, BorderLayout.CENTER);
		add(saveBnt, BorderLayout.NORTH);
		add(showBnt, BorderLayout.SOUTH);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class MySaveListener extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			System.out.println("您点击了按钮, 将保存数据!");
			myAns = inputFld.getText();
			inputFld.setText("");
		}
	}
	
	class MyShowListener extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			System.out.println("您之前输入的内容如下!");
			System.out.println(myAns);
		}
	}
	
	public static void main(String[] args) {
		new TextSaveBnt();
	}
}
