package viewKit;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * 背景面板类
 * 任何继承该类的Jpanel可以具有背景,背景图片url通过：
 * 构造函数或者set方法指定
 * */

public class BgPanel extends JPanel{
	private String url;
	
	public BgPanel() {
		super();
		url = "/imgs/login"+".png";
	}
	public BgPanel(String url) {
		super();
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void changeToBgImage(String url) {
		setUrl(url);
		/*
		 * 	现在还不知道这个repaint()用的对不对
		 * */
		repaint();
	}

	//重写panel类的paintComponent方法，在绘制时会调用
	public void paintComponent(Graphics g){
		try{ 
			//相对路径获取图片资源
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(url));
			//图片绘制填充整个面板
			g.drawImage(imageIcon.getImage(), 0, 0, this.getWidth(),this.getHeight(),this);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,"图片资源不存在!",
					   "信息对话框", JOptionPane.WARNING_MESSAGE);
		}
		//设置调用者(panel)的属性为透明，所以图片和组件不会互相遮挡
		this.setOpaque(false);
	}
	
//	public static void main(String[] args) {
//		new bgPanel();
//	}
}

