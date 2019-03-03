package viewKit;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * 	判定用户界面的用户输入是否合法
 * 	目前合法包括下面内容:
 * 	输入框内容非空				容易实现
 * 	输入内容的类型符合正则表达式	暂时没有实现
 * */
public class CheckInput {
	//private String type;
	//private String warning;
	private JComponent component;
	
	public CheckInput(){}
	public CheckInput(JComponent component) {
		this.component = component;
	}
	
	public boolean getResult(String warningMsg) {
		/*
		 * 	注意因为 JPasswordField 继承 JTextFiled
		 * 	所以必须按照这种顺序而且使用else if语句
		 * */
		if(component instanceof JPasswordField) {
			//System.out.println("this is a psdField");
			JPasswordField psw = (JPasswordField)component;
			if( psw.getPassword().length == 0 ) {
				JOptionPane.showMessageDialog(psw.getParent(), warningMsg
						, "信息对话框", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}else if(component instanceof JTextField) {
			//System.out.println("this is a JTextField");
			JTextField tx = (JTextField)component;
			if( tx.getText().equals("") ) {
				JOptionPane.showMessageDialog(tx.getParent(), warningMsg, 
						 "信息对话框", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	public CheckInput setComponent(JComponent component){
		this.component = component;
		return this;
	}
	
}
