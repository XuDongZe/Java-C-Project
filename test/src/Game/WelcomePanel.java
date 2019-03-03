package Game;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import Constant.Constant;
import Util.SoundUtil;

public class WelcomePanel extends BgPanel implements Constant{
	private Game game;
	private JButton newGameBnt;
	private JButton loadGameBnt;
	private JButton helpBnt;
	
	private static final int buttonWidth = 100;
	private static final int buttonHeight = 20;
	
	private static final ImageIcon bnt_newgame;
	private static final ImageIcon bnt_newgame_flash;
	private static final ImageIcon bnt_continue;
	private static final ImageIcon bnt_continue_flash;
	private static final ImageIcon bnt_help;
	private static final ImageIcon bnt_help_flash;
	
	static {
		bnt_newgame = new ImageIcon("image/bnt_newgame.png");
		bnt_newgame_flash = new ImageIcon("image/bnt_newgame_flash.png");
		bnt_continue = new ImageIcon("image/bnt_continue.png");
		bnt_continue_flash = new ImageIcon("image/bnt_continue_flash.png");
		bnt_help = new ImageIcon("image/bnt_help.png");
		bnt_help_flash = new ImageIcon("image/bnt_help_flash.png");
	}
	
	public WelcomePanel() {}
	
	public WelcomePanel(Game game) {
		super("image//bg_paper.png");
		this.game = game;
		//设定初始构造时的面板大小
		setPreferredSize(new Dimension(Constant.WIDTH, Constant.HEIGHT));
		
		newGameBnt = new JButton();
		loadGameBnt = new JButton();
		helpBnt = new JButton();
		newGameBnt.addMouseListener(new MyStartBntListener());
		loadGameBnt.addMouseListener(new MyLoadBntListener());
		helpBnt.addMouseListener(new MyHelpBntListener());
		
		newGameBnt.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		loadGameBnt.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		helpBnt.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		
		newGameBnt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		loadGameBnt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		helpBnt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		newGameBnt.setIcon(bnt_newgame);
		loadGameBnt.setIcon(bnt_continue);
		helpBnt.setIcon(bnt_help);
		
		newGameBnt.setContentAreaFilled(false);
		loadGameBnt.setContentAreaFilled(false);
		helpBnt.setContentAreaFilled(false);
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.SOUTH, newGameBnt, -300, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, newGameBnt, 200, SpringLayout.WEST, this);
		add(newGameBnt);
		layout.putConstraint(SpringLayout.SOUTH, loadGameBnt, -250, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, loadGameBnt, 200, SpringLayout.WEST, this);
		add(loadGameBnt);
		layout.putConstraint(SpringLayout.SOUTH, helpBnt, -200, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, helpBnt, 200, SpringLayout.WEST, this);
		add(helpBnt);
		
	}
	
	private class MyStartBntListener extends MouseAdapter {
		 public void mouseClicked(MouseEvent paramMouseEvent) {
			 game.running = true;
			 game.switchPanel(MAIN_PANEL);
			 game.getMyPanel().startThread();
			 SoundUtil.playSound("sound/TFCLICK.WAV");
		 }
		 public void mouseEntered(MouseEvent paramMouseEvent) {
			 newGameBnt.setIcon(bnt_newgame_flash);
			 SoundUtil.playSound("sound/TOCK.WAV");
		 }
		 public void mouseExited(MouseEvent paramMouseEvent) {
			 newGameBnt.setIcon(bnt_newgame);
		 }
	}
	private class MyLoadBntListener extends MouseAdapter {
		public void mouseClicked(MouseEvent paramMouseEvent) {
			 SoundUtil.playSound("sound/TFCLICK.WAV");
		 }
		 public void mouseEntered(MouseEvent paramMouseEvent) {
			 loadGameBnt.setIcon(bnt_continue_flash);
			 SoundUtil.playSound("sound/TOCK.WAV");
		 }
		 public void mouseExited(MouseEvent paramMouseEvent) {
			 loadGameBnt.setIcon(bnt_continue);
		 }
	}
	private class MyHelpBntListener extends MouseAdapter {
		 public void mouseClicked(MouseEvent paramMouseEvent) {
			 SoundUtil.playSound("sound/TFCLICK.WAV");
		 }
		 public void mouseEntered(MouseEvent paramMouseEvent) {
			 helpBnt.setIcon(bnt_help_flash);
			 SoundUtil.playSound("sound/TOCK.WAV");
		 }
		 public void mouseExited(MouseEvent paramMouseEvent) {
			 helpBnt.setIcon(bnt_help);
		 }
	}
	
	public static void mian (String[] args) {
		new WelcomePanel();
	}
}
