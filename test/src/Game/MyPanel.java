package Game;
import javax.swing.*;
import javax.xml.crypto.Data;

import Constant.Constant;
import Util.JudgeUtil;
import Poro.Player;
import Util.FindRoadUtil;
import Util.MapUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Clock;
import java.util.Date;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyPanel extends JPanel implements KeyListener, MouseListener, Constant {
	
	//游戏系列序号
	private static final long serialVersionUID = 1L;
	private Game game;
	public int[][] map;
	
	private Player player;
	private int currentPath;
	private FindRoadUtil findRoadUtil;
	//私有数据成员:线程处理
	private Thread threadAnime;
	
	private JLabel FPSLabel;
	private String FPSStr;
	
	public MyPanel (Game game) {
		super();
		this.game = game;
		
		//设定初始构造时的面板大小
		setPreferredSize(new Dimension(Constant.WIDTH, Constant.HEIGHT));
		
		//地图初始化
		map = MapUtil.getCurrentFgMap(MapUtil.getCurrentFn());
		
		//角色初始化:坐标为1,1;方向为DOWN,初始迈左脚,设置自动寻路的阻挡地图
		player = new Player(2, 13, UP, 0, this);
		player.setMap(map);
		
		//自动寻路组件初始化
		currentPath = 0;
		findRoadUtil = new FindRoadUtil(map);
		
		new JudgeUtil(player, this);
		
		FPSLabel = new JLabel();
		FPSLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
		setLayout(new BorderLayout());
		add(FPSLabel, BorderLayout.NORTH);
		
		//设定焦点在本窗体并交给监听对象
		setFocusable(true);
		//添加监视器
		addKeyListener(this);
		addMouseListener(this);

		//实例化内部线程 AnimationThread
		threadAnime = new Thread(new AnimationThread());

		//在初始化面板时就启动线程
		//threadAnime.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		MapUtil.drawMap(g);
		player.drawRole(g);
	}
	
	public void startThread() {
		threadAnime.start();
	}
	
	private class AnimationThread extends Thread {
		//内部类实现计步器
		public void run() {
			while( true ) {
				if( game.running ) {
					long before = System.nanoTime();
					
					//自动寻路每次移动一下
					if(currentPath < findRoadUtil.getPathLength() ) {
						player.move(findRoadUtil.getDirectionPath()[currentPath++]);
					}
					//重绘画面
					repaint();
					
					//根据FPS动态计算是否需要休眠仍需要休眠的时间
					long intervalTime = System.nanoTime() - before;
					if( intervalTime > fpsNsTime ) {
						continue;	//运行时间已经达到
					}
					try {
						Thread.sleep( (fpsNsTime - (System.nanoTime() - before)) / 1000000 );
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					do {
						intervalTime = System.nanoTime() - before;
					}while( intervalTime < fpsNsTime );
					FPSLabel.setText(String.valueOf( ( 1000000000 / (System.nanoTime() - before) ) ));
				}
			}
		}
	}//内部类结束
	
	public void keyPressed(KeyEvent e) {
		if( game.running ) {
	 		//得到键盘输入
			int keyCode = e.getKeyCode();
			//根据键盘输入移动，这里的“移动”是对地图数据而言的，并不是对显示而言
			switch(keyCode) {
			case KeyEvent.VK_LEFT:
				player.move(LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				player.move(RIGHT);
				break;
			case KeyEvent.VK_UP:
				player.move(UP);
				break;
			case KeyEvent.VK_DOWN:
				player.move(DOWN);
				break;
			}
			//重绘画面,这是才根据刚刚更改的数据将画面刷新，才会看到任务的移动
			repaint();
		}
	}
	public void keyReleased(KeyEvent e) {	}
	public void keyTyped(KeyEvent e) {	}

	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e)  { }
	public void mouseReleased(MouseEvent e) { }
	@Override
	public void mousePressed(MouseEvent e) {
		//单位转换：像素->游戏中人物逻辑的一步
		player.setPlayerDX( e.getX()/CS );
		player.setPlayerDY( e.getY()/CS );	
		if( game.running) {
			findRoadUtil.findRoad( 	player.getPlayerX(), player.getPlayerY(), 
								player.getPlayerDX(), player.getPlayerDY() );
			currentPath = 0;
		}
	}
}







