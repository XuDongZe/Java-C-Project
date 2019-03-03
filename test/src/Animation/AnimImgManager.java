package Animation;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class AnimImgManager extends JFrame {
	private List<Image> imgs; //多张动画图
	private Image image;//动画序列图
	private JButton importBnt;	//导入按钮
	private JButton outPortBnt; //导出按钮
	
	private JFileChooser fc;
	
	public AnimImgManager() {
		super("动画图片管理");
		imgs = new ArrayList<>();
		importBnt = new JButton("导出");
		outPortBnt = new JButton("导出");
		fc = new JFileChooser();
	}
	
	
}
