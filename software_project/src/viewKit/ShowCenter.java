package viewKit;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author xudongze
 *	because used frame.getHeight() and frame.getWidth(),
 *	so must init frame.size then can use this function.
 */
public class ShowCenter { 
	
	public ShowCenter(JFrame frame){
		Toolkit kit = Toolkit.getDefaultToolkit();    // 定义工具包
    	Dimension screenSize = kit.getScreenSize();   // 获取屏幕的尺寸
    	int screenWidth = screenSize.width;         // 获取屏幕的宽
    	int screenHeight = screenSize.height;       // 获取屏幕的高
    	int height = frame.getHeight();
    	int width = frame.getWidth();
    	//System.out.println(height+","+width);
    	frame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
    }
}
