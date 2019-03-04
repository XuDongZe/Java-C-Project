
package GameSnake;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * BUGs:
 * 1	when you hadn't click the button to start the game,the paint thread has already run.
 * 
 * 2	you can move to your anti-direction now.
 * 
 * 3	the FoodCrash Size is to small -> now it is snake.darimer/2 and it should be not div by 2,so 
 * 		your snake will not move through the food.And I chante as it, and make it,now the snake can't 
 * 		through a food.
 * 
 * 4	you should konw the differences and the link among ( Point.x Point.y fillOval(x,y)), so you 
 * 		can use math to slove the crash event.
 * 
 * 5 	sometimes your snake will eat the same food more than one time, obviously it still the FoodCrash
 * 		and the eatFood() don't work well.And what is more, if you eat tiwce,the bodyLength and the 
 * 		snakeHead will get updated tiwce,but there is just one body in fact 
 * 
 * 6	sometimes you can't see the food in the Panel,maybe the random math is wrong.
 * 
 * 7 	you can't use move() and eatFood() at the same time,if you do that,you will find that 
 * 		the snakeHead will increase tiwce or more: it move and eatFood at the same time.So your array
 * 		will exists null pointer
 * 
 * 8	your snake.draw and food.draw use the same Graphics so you can use render tech,
 * 		but remind that ,emmm, for example,if you use g.setColor() in your snake.draw() and
 * 		you use g.setColor() in your food.draw() at the same time, the color may be what you don't want.
 * 		But since we setColor each when we want to darw somthing,so the color is stable? 
 * 
 * 9 	now when a not first init food apperance and the snake will Crash with it imemeditely.So the Snake
 * 		Body will be seperated not seq.and the snack will eat food forever so don't move
 * 		and will occur error with time going by.
 * 
 * 10   now for a while the snakeBody will get invisible : because we use for(i=tail;i<head;i=(i+1)%body.length)
 * 		in the function : snake.draw(); but tail may be bigger than head,now the runtime will not draw body
 * 		but just draw a head.
 * 		So we should use i != head as the control expression.
 */ 

public class GameFrame extends JFrame implements Config{
	
	private static final long serialVersionUID = 1L;
	
	private BorderLayout layout;
	private GamePanel gamePanel;
	
	public GameFrame(){
		
		super("SnakeGame");
		
		layout = new BorderLayout();
		gamePanel = new GamePanel();
		
		this.setContentPane(gamePanel);
		this.setLayout(layout);
		gamePanel.setFocusable(true);
			
		setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		new ShowCenter(this);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	
//	public static void main(String[] args) {
//		GameFrame game = new GameFrame();
//	}
}