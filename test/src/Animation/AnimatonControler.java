package Animation;

import Poro.Sprite;

/**
 * 
* @ClassName: AnimatonControler 
* @Description:
* @author xudongze
* @date 2019年3月3日 下午10:32:11 
*
 */
public class AnimatonControler {
	Animation animation;
	Sprite sprite;
	
	private static final int DEFAULT_FREQUENCY = 25;
	private int frequency;
	
	public AnimatonControler(Sprite sprite, Animation animation) {
		// TODO Auto-generated constructor stub
		this.sprite = sprite;
		this.animation = animation;
		this.frequency = DEFAULT_FREQUENCY;
	}
	
	public void run() {
		
	}
	
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
}
