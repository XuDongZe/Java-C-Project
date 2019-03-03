package Game;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.geom.FlatteningPathIterator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Constant.Constant;
import Util.ShowCenter;

public class Game extends JFrame implements Constant{

	private static final long serialVersionUID = 1L;
	public boolean running;
	private MyPanel myPanel;
	private WelcomePanel welcomePanel;
	private CardLayout card;
	private Map<String, JPanel> panelMap;
	
	//private static final LoadingPanel loadingPanel;
	
	@SuppressWarnings("deprecation")
	public Game() {
		super("RPG走迷宫游戏");
		running = false;
		
		
		//自定义面板类,得到自定义地图面板的实例panel
		myPanel = new MyPanel(this);
		welcomePanel = new WelcomePanel(this);
		panelMap = new HashMap<>();
		panelMap.put(WELCOME_PANEL, welcomePanel);
		panelMap.put(MAIN_PANEL, myPanel);
		
		JPanel contentPanel = new JPanel();
		setContentPane(contentPanel);
		card = new CardLayout();
		contentPanel.setLayout(card);
		contentPanel.add(WELCOME_PANEL, welcomePanel);
		contentPanel.add(MAIN_PANEL, myPanel);
		card.show(contentPanel, WELCOME_PANEL);
		
		//执行并构建窗体设定
		pack();
		setResizable(false);
		ShowCenter.showCenter(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void showLoading() {
		
	}

	public void switchPanel(String panelName) {
		card.show(this.getContentPane(), panelName);
		panelMap.get(panelName).requestFocus();
	}
	
	public MyPanel getMyPanel() {
		return myPanel;
	}
	public WelcomePanel getWelcomePanel() {
		return welcomePanel;
	}
	
	public static void main (String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (ClassNotFoundException |InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new Game();
	}
}









