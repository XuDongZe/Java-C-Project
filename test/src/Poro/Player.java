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
	private int playerY;		//��ǰ����
	private int playerDirection;
	private int playerFootCount;
	private int playerDX;		//Ŀ������
	private int playerDY;
	
	private int HP;
	private int ATK;//������
	private int DFS;//������defense
	
	private int[][] map; //���ﵱǰ���ڵ�ͼ
	
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
	
	//���췽��
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
	* @Description:�����ͼ����Ƶ�ָ��λ��
	* @param g
	* @return void
	 */
	public void drawRole(Graphics g) {
		//ʹ��countƫ����ʵ�����ҽŵ��ƶ�,ͨ��directionƫ����ʵ��ת��
		//��Ҫ�����ض���4*4ͼ������ض����ƶ��㷨���볣��ConstantϢϢ���
		g.drawImage(this.playerImage.getImage(), playerX * CS, playerY * CS, playerX * CS + CS, playerY* CS + CS,
					playerFootCount * CS, playerDirection * CS, CS + playerFootCount * CS, playerDirection * CS + CS, null);
	}

	//set����
	public void setPlayerX (int playerX) { this.playerX = playerX; }
	public void setPlayerY (int playerY) { this.playerY = playerY; }
	public void setPlayerDX (int playerDX) { this.playerDX = playerDX; }
	public void setPlayerDY (int playerDY) { this.playerDY = playerDY; }
	public void setPlayerDerection(int playerDericton) { this.playerDirection = playerDericton; }
	public void setPlayerIsLeftFoot(int playerFootCount) { this.playerFootCount = playerFootCount; }
	//get����
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









