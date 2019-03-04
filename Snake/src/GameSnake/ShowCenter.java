package GameSnake;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ShowCenter {
	
	public ShowCenter(JFrame jFrame){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int screenHeight =  screenSize.height;
		int screenWidth = screenSize.width;
		int frameHeight = jFrame.getHeight();
		int frameWidth = jFrame.getWidth();
		
		jFrame.setLocation(screenWidth/2-frameWidth/2, screenHeight/2-frameHeight/2);
	}
}
