package Test;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import Constant.Constant;
import Util.FileManagerUtil;
import Util.ShowCenter;
import Util.SpriteEditor;

public class TestFrame extends JFrame implements Constant{
	private JButton fcBnt; 
	
	public TestFrame() {
		super("TestFrame");
		
		/**
		 * test FileManagerUtil
		 */
		fcBnt = new JButton("TestFcUtil");
		fcBnt.addActionListener(new MyFcBntListener());
		add(fcBnt);
		
		setSize(Constant.WIDTH, Constant.HEIGHT);
		setResizable(false);
		ShowCenter.showCenter(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class MyFcBntListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent me) {
			JFileChooser fc = FileManagerUtil.getFileChooser("map");
			if( fc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION ) {
				String selectPath = fc.getSelectedFile().getPath();
				String selectName = fc.getSelectedFile().getName();
				System.out.println(selectPath);
				System.out.println(selectName);
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException |InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new SpriteEditor();
	}
	
}
