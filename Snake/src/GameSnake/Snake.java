package GameSnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Snake implements Config{
	
	//private int direction;
	private int dimater;
	private int speed;
	
	public Point snakeHead;
	//private List<Point> snakeBody;
	private Point[] snakeBody;
	public int bodyLength;
	public int bodyHead;
	public int bodyTail;
	
	private GamePanel gamePanel;
	
	public Snake(GamePanel gamePanel){
		
		dimater = SNAKE_DIMATER;
		speed = SNAKE_SPEED;
		//init no body
		snakeHead = new Point(100, 100);
		snakeBody = new Point[MAX_BODY_NUM];
		bodyHead = -1;
		bodyTail = -1;
		bodyLength = 1;
		
		this.gamePanel = gamePanel;
	}
	public Point getHead(){
		return snakeHead;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public int getSpeed(){
		return speed;
	}
	public void setDimater(int d){
		dimater = d;
	}
	public int getDimater(){
		return dimater;
	}
	public int getBodyLength(){
		return bodyLength;
	}
	public void setBodyLength(int length){
		bodyLength = length;
	}

	public void update(){
		int keyDirection = gamePanel.getKeyDirection();	
		//now you are aload to move to your oppsite direction immeditly
		switch(keyDirection){
		case UP:
			snakeHead.y -= speed;
			break;
		case DOWN:
			snakeHead.y += speed;
			break;
		case LEFT:
			snakeHead.x -= speed;
			break;
		case RIGHT:
			snakeHead.x += speed;
			break;
		default:
			break;
		}		
		bodyHead = (bodyHead + 1) % snakeBody.length;
		bodyTail = (bodyHead + snakeBody.length - bodyLength + 1) % snakeBody.length;
		snakeBody[bodyHead] = new Point(snakeHead.x, snakeHead.y);
		
		if(selfCrash()){
			System.out.println("a selfCrash Occur");
		}
	}
	
	/*
	 * do not konw the relationship between the head and the body
	 */
	private boolean selfCrash(){
		int length = snakeBody.length;
		for(int i = bodyTail; i != bodyHead; i = (i + 1) % length){
			if( snakeBody[i].equals(snakeHead) ){
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g){
		Point currentBody;
		int length = snakeBody.length;
		if(bodyLength > 1) {
			for(int i = bodyTail; i != bodyHead; i = (i + 1) % length ){
				currentBody = snakeBody[i];
				//System.out.println("bodyTail: "+bodyTail+"\nbodyHead: "+bodyHead+"\ni: "+i);
				g.setColor(Color.BLACK);
				g.fillOval(currentBody.x, currentBody.y, dimater, dimater);
			}
		}
		//for head use different color 
		g.setColor(Color.YELLOW);
		g.fillOval(snakeHead.x, snakeHead.y, dimater, dimater);
	}
	
}

