package GameSnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Food implements Config{
	Point foodLocation;
	private int foodSize;
	private Snake snake;
	private GamePanel gamePanel;
	Random random;
	
	public Food(GamePanel gPanel,Snake snk){
		snake = snk;
		gamePanel = gPanel;
		foodSize = FOOD_SIZE;
		random = new Random();

		foodLocation = new Point(Math.abs(random.nextInt(gamePanel.getWidth()-FOOD_SIZE)),
								 Math.abs(random.nextInt(gamePanel.getHeight()-FOOD_SIZE)) );
	}
	public Food(int x, int y){
		foodSize = FOOD_SIZE;
		foodLocation = new Point(x, y);
	}
	public Food(Point p){
		foodSize = FOOD_SIZE;
		foodLocation = p;
	}
	
	public void setFoodLocation(Point location){
		foodLocation = location;
	}
	public Point getFoodLocation(){
		return foodLocation;
	}
	public void setFoodSize(int size){
		foodSize = size;
		
	}
	public int getFoodSize(){
		return foodSize;
	}
	
	public void update(){
		int sx = snake.getHead().x;
		int sy = snake.getHead().y;
		int fx = foodLocation.x;
		int fy = foodLocation.y;
		int dimater = snake.getDimater();
		int length;
		
//		System.out.println("shx,shy="+snake.snakeHead.x+","+snake.snakeHead.y);
//		System.out.println("head,tail="+snake.bodyHead+","+snake.bodyTail);
//		System.out.println("slength="+snake.getBodyLength());
//		System.out.println("food="+foodLocation);
		if( Math.abs((sx + dimater/2) - (fx + foodSize/2)) <= dimater &&
				Math.abs((sy + dimater/2) - (fy + foodSize/2)) <= dimater
					/*Math.abs(sx-fx) < dimater && Math.abs(sy-fy) < dimater*/ ) 
		{
			System.out.println("a foodCrash occur");
			foodLocation = new Point(Math.abs(random.nextInt(gamePanel.getWidth()-FOOD_SIZE)),
					Math.abs(random.nextInt(gamePanel.getHeight()-FOOD_SIZE)) );
			if((length = snake.getBodyLength()) < MAX_BODY_NUM) {
				snake.setBodyLength(length + 1);
			}
		}
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(foodLocation.x, foodLocation.y, foodSize, foodSize);
		//System.out.println("draw a food: x, y="+foodLocation+"  color="+g.getColor());
	}
}
