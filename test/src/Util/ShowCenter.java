package Util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * 
 * @author xudongze
 *	because used frame.getHeight() and frame.getWidth(),
 *	so must init frame.size then can use this function.
 */
public class ShowCenter { 
	
	public static void showCenter(JFrame frame){
		Toolkit kit = Toolkit.getDefaultToolkit();    // 定义工具�?
    	Dimension screenSize = kit.getScreenSize();   // 获取屏幕的尺�?
    	int screenWidth = screenSize.width;         // 获取屏幕的宽
    	int screenHeight = screenSize.height;       // 获取屏幕的高
    	int height = frame.getHeight();
    	int width = frame.getWidth();
    	
    	frame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
    }
}
