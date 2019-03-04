package GameSnake;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Config{

	/**
	 * 1	you shoule konw the array.length is what
	 * 2	you shoule konw the bodyLength and the snakeBody.length is different
	 * 3	you shoule konw %MAX_NUM and %bodyLength and %snakeBody.lenght
	 * 4	you shoule konw where is the point.x point.y and where is fillovea(x,y)
	 */
	private static final long serialVersionUID = 1L;
	
	private Snake snake;
	private Food food;
	private int keyDirection;
	private Thread threadAnime;
	private JButton button;
	
	public GamePanel() {
		super();
		System.out.println(this.getWidth());
		
		setLayout(new BorderLayout());
	
		button = new JButton("Start Game Now!");
		button.setSize(START_BUTTON_HEIGHT, START_BUTTON_WIDTH);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("there is a button clicked");
				threadAnime = new Thread(new AminationThread());
				threadAnime.start();
				button.setVisible(false);
			}
		});
		add(button,BorderLayout.SOUTH);
		setSize(WINDOW_HEIGHT,WINDOW_WIDTH);
		setFocusable(true);
		addKeyListener(new MyKeyListener());
		
		snake = new Snake(this);
		food = new Food(this,snake);
	}
	
	private class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
				System.out.println("yes a keyPressed event occur");
				keyDirection = e.getKeyCode();
			switch(keyDirection){
				case KeyEvent.VK_UP:
					keyDirection = UP;
					break;
				case KeyEvent.VK_DOWN:
					keyDirection = DOWN;
					break;
				case KeyEvent.VK_LEFT:
					keyDirection = LEFT;
					break;
				case KeyEvent.VK_RIGHT:
					keyDirection = RIGHT;
					break;
				default:
					break;
			}//end switch
		}//end keyPressed
	}//end MyKeyListener
	
	class AminationThread extends Thread {
		public void run(){
			while(true) {
				//you need repaint first so you may not lose the first image to draw
				repaint();
				updateData();
				try {
					Thread.sleep(THREAD_SLEEP_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void updateData(){
		//System.out.println("before move() the direction is: "+keyDirection);
		//System.out.println("after move() the direction is: "+keyDirection);
		//you may update snake first, so you can get the newset snake to panduan foodCrash in food.update()
		snake.update();
		food.update();
		if( boardCrash() /*|| snake.selfCrash()*/ ){
			//JDialog deadDialog = new JDialog("you are dead!");
			System.out.println("a boardCrash occur!");
		}
		else{
			
		}
	}
	
	public void paint(Graphics g){
		//get current Screen image object
		Image screenImage = this.createImage(WINDOW_HEIGHT, WINDOW_WIDTH);
		//get screenImage -> Graphics
		Graphics gBack = screenImage.getGraphics();
		//fill the backgroud color use itself color
		gBack.setColor(gBack.getColor());
		//clear
		gBack.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		super.paint(gBack);
		snake.draw(gBack);
		food.draw(gBack);
		
		//use backgroud Graphics gback(now data is already in the gBack) to draw a Image. 
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public int getKeyDirection(){
		System.out.println("keydir:"+keyDirection);
		return keyDirection;
	}
	
//	private boolean selfCrash(){
//	}
	
	private boolean boardCrash(){
		int currentX = snake.getHead().x;
		int currentY = snake.getHead().y;
		
		if( currentX >= 0 && currentX <= this.getWidth() &&
		    currentY >= 0 && currentY <= this.getHeight() ){
			return false;
		}
		return true;
	}	
	
	//for debug
	public static void main(String[] args) {
		JFrame frame = new JFrame("hello");
		Container a = frame.getContentPane();
		
		GamePanel panel = new GamePanel();
		a.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
