package viewKit;

import java.awt.Container;
import javax.swing.JOptionPane;
import Constant.Constants;
import netKit.DeCoder;

/*
 * 	根据从server返回的state进行用户提示
 * */
public class CheckState implements Constants{
	private int state;
	private Container father;	//父容器
	
	public CheckState(Container father) {
		this.father = father;
	}
	public CheckState(int state, Container father) {
		this.state = state;
		this.father = father;
	}
	
	public boolean getResult(String title){
		JOptionPane.showMessageDialog(father, DeCoder.parseStateCode(state), title,
				JOptionPane.WARNING_MESSAGE);
		return DeCoder.isSuccessState(state);
	}
	
	public CheckState setState(int state){
		this.state = state;
		return this;
	}
}
