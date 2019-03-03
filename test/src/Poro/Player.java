package Poro;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import Constant.Constant;
import Game.MyPanel;
import Util.JudgeUtil;
import Util.FindRoadUtil;
import Util.ImageUtil;

public class Player extends Sprite implements Constant{
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	private MyPanel gamePanel;
	
	//private Image playerImage;
	private int playerX;
	private int playerY;		//当前坐标
	private int playerDirection;
	private int playerFootCount;
	private int playerDX;		//目标坐标
	private int playerDY;
	
	private int HP;
	private int ATK;//攻击力
	private int DFS;//防御力defense
	
	private int[][] map; //人物当前所在地图
	
	public void move() {
		
	}
 	
	public void move(int directionCode) {
		int x = playerX;
		int y = playerY;
		FindRoadUtil findRoadUtil = new FindRoadUtil(map);
		
		switch(directionCode) {
			case LEFT :
				if( findRoadUtil.moveIsAllow(x-1, y) ) {
					this.setPlayerX(x-1);
				}
				this.setPlayerDerection(LEFT);
				break;
			case RIGHT :
				if( findRoadUtil.moveIsAllow(x+1, y) ) {
					this.setPlayerX(x+1);
				}
				this.setPlayerDerection(RIGHT);
				break;
			case UP :
				if( findRoadUtil.moveIsAllow(x, y-1) ) {
					this.setPlayerY(y-1);
				}
				this.setPlayerDerection(UP);
				break;
			case DOWN :
				if( findRoadUtil.moveIsAllow(x, y+1) ) {
					this.setPlayerY(y+1);
				}
				this.setPlayerDerection(DOWN);
				break;
			default:
				break;
			}
		exchangeFoot();
		new JudgeUtil(this, gamePanel).judgeSuccess();
	}
	
	//构造方法
	public Player (int x, int y, int deriction, int footCount, MyPanel panel) {
		playerX = x;
		playerY = y;
		playerDirection = deriction;
		playerFootCount = footCount;
		iconLabel = ImageUtil.playerIcon;
		gamePanel = panel;
	}
	public Player () {
		
	}

	/**
	* @Title: drawRole 
	* @Description:将玩家图像绘制到指定位置
	* @param g
	* @return void
	 */
	public void drawRole(Graphics g) {
		//使用count偏移量实现左右脚的移动,通过direction偏移量实现转向
		//需要基于特定的4*4图像设计特定的移动算法，与常量Constant息息相关
		g.drawImage(this.playerImage.getImage(), playerX * CS, playerY * CS, playerX * CS + CS, playerY* CS + CS,
					playerFootCount * CS, playerDirection * CS, CS + playerFootCount * CS, playerDirection * CS + CS, null);
	}

	//set方法
	public void setPlayerX (int playerX) { this.playerX = playerX; }
	public void setPlayerY (int playerY) { this.playerY = playerY; }
	public void setPlayerDX (int playerDX) { this.playerDX = playerDX; }
	public void setPlayerDY (int playerDY) { this.playerDY = playerDY; }
	public void setPlayerDerection(int playerDericton) { this.playerDirection = playerDericton; }
	public void setPlayerIsLeftFoot(int playerFootCount) { this.playerFootCount = playerFootCount; }
	//get方法
	public int 	getPlayerX()	{ return playerX; }
	public int 	getPlayerY()	{ return playerY; }
	public int 	getPlayerDX()	{ return playerDX; }
	public int 	getPlayerDY()	{ return playerDY; }
	public int  getPlayerDirection () { return playerDirection; }
	public int  getplayerFootCount() { return playerFootCount; }
	
	public int[][] getMap() {
		return map;
	}
	public void setMap(int[][] map) {
		this.map = map;
	}

	public void exchangeFoot() {
		if(playerFootCount == 0){
			playerFootCount = 1;
		}
		else {
			playerFootCount = 0;
		}
	}
	
}









