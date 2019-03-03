package Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpriteEditor extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_width;
	private JTextField textField_height;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException |InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpriteEditor frame = new SpriteEditor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SpriteEditor() {
		setBounds(300, 100, 800, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.9);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1, 64, 64, 0};
		gbl_panel.rowHeights = new int[]{1, 94, 21, 23, 23, 26, 26, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 2;
		panel.add(lblName, gbc_lblName);
		
		textField_name = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		panel.add(textField_name, gbc_textField);
		textField_name.setColumns(10);
		
		JButton btnType = new JButton("Type");
		btnType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnType = new GridBagConstraints();
		gbc_btnType.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnType.anchor = GridBagConstraints.NORTH;
		gbc_btnType.insets = new Insets(0, 0, 5, 5);
		gbc_btnType.gridx = 1;
		gbc_btnType.gridy = 3;
		panel.add(btnType, gbc_btnType);
		
		JButton btnImage = new JButton("Image");
		GridBagConstraints gbc_btnImage = new GridBagConstraints();
		gbc_btnImage.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnImage.insets = new Insets(0, 0, 5, 5);
		gbc_btnImage.gridx = 1;
		gbc_btnImage.gridy = 4;
		panel.add(btnImage, gbc_btnImage);
		
		JLabel lblWidth = new JLabel("Width");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 1;
		gbc_lblWidth.gridy = 5;
		panel.add(lblWidth, gbc_lblWidth);
		
		textField_width = new JTextField();
		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 5;
		panel.add(textField_width, gbc_textField1);
		textField_width.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 1;
		gbc_lblHeight.gridy = 6;
		panel.add(lblHeight, gbc_lblHeight);
		
		JTextArea textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridwidth = 2;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 7;
		panel.add(textArea, gbc_textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);
		
		JPanel panel_1 = new JPanel();
		scrollPane_1.setViewportView(panel_1);
		
		ShowCenter.showCenter(this);
	}

}
