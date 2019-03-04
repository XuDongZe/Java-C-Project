package GameSnake;

/*
 * define config data
 */
public interface Config {
	
	public static final int WINDOW_HEIGHT = 250;
	public static final int WINDOW_WIDTH = 250;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	public static final int START_BUTTON_HEIGHT = WINDOW_HEIGHT / 2;
	public static final int START_BUTTON_WIDTH = WINDOW_WIDTH / 2;
	
	public static final int MAX_BODY_NUM = 50;
	
	public static final int SNAKE_DIMATER = 10;
	public static final int FOOD_SIZE = SNAKE_DIMATER;
	public static final int SNAKE_SPEED = SNAKE_DIMATER; 
	
	public static final int GAME_LEVEL = 2;
	public static final int THREAD_SLEEP_TIME = 400/GAME_LEVEL;
	
}
