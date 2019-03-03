package Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.MalformedInputException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Constant.Constant;

public class SpriteEditor1 extends JFrame implements Constant{
	private JButton fcBnt;
	private JTextField nameFiled;
	private JTextField iconFiled;
	private String selectName;
	private boolean inIconFiled;
	private JButton typeBnt;
	private JButton descBnt;
	private JButton submitBnt;
	
	private JLabel iconLabel;
	
	public SpriteEditor1() {
		
		super();
        
		setLayout(new BorderLayout());
		selectName = null;
		inIconFiled = false;
		
		iconLabel = new JLabel();
		iconLabel.setPreferredSize(new Dimension(100, 25));
		iconLabel.setOpaque(true);
		
		nameFiled = new JTextField();
		iconFiled = new JTextField();
		nameFiled.setPreferredSize(new Dimension(100, 25));
		iconFiled.setPreferredSize(new Dimension(100, 25));
		iconFiled.setEnabled(false);
		
		descBnt = new JButton("Descriptor");
		descBnt.setPreferredSize(new Dimension(100, 25));
		descBnt.setHorizontalTextPosition(JButton.CENTER);
		
		typeBnt = new JButton("TypeChooser");
		typeBnt.setPreferredSize(new Dimension(100, 25));
		typeBnt.setHorizontalTextPosition(JButton.CENTER);
		
		fcBnt = new JButton("FileChooser");
		fcBnt.setPreferredSize(new Dimension(100, 25));
		fcBnt.setHorizontalTextPosition(JButton.CENTER);
		fcBnt.addActionListener(new MyFcBntListener());
		
		submitBnt = new JButton("Submit");
		submitBnt.setPreferredSize(new Dimension(100, 25));
		submitBnt.setHorizontalTextPosition(JButton.CENTER);
		submitBnt.addActionListener(new MySubmiteBntListener());
		
		setLayout(new GridLayout(5, 2));
		add(new JLabel("name: ")); 	add(nameFiled);
		add(fcBnt); 	add(new JLabel());
		add(iconFiled); add(iconLabel);	
		add(typeBnt);	add(descBnt);
		add(submitBnt);	
		
		pack();
		setResizable(false);
		ShowCenter.showCenter(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class MyFcBntListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent me) {
			JFileChooser fc = FileManagerUtil.getFileChooser("image");
			if( fc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION ) {
				String name = fc.getSelectedFile().getName();
				iconFiled.setText( name );
				ImageIcon icon = new ImageIcon("image/" + name);
				iconLabel.setIcon( new ImageIcon( icon.getImage()
						.getScaledInstance(iconLabel.getWidth(), iconLabel.getHeight(), 
								Image.SCALE_DEFAULT)) );
			}
		}
	}
	private class MyTypeBntListener implements ActionListener{
		public void actionPerformed(ActionEvent paramActionEvent) {
			System.out.println("typeBnt click");

		}
	}
	private class MySubmiteBntListener implements ActionListener{
		public void actionPerformed(ActionEvent paramActionEvent) {
			System.out.println("submitBnt click");

		}
	}

}
