package Animation;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class AnimImgManager extends JFrame {
	private List<Image> imgs; //���Ŷ���ͼ
	private Image image;//��������ͼ
	private JButton importBnt;	//���밴ť
	private JButton outPortBnt; //������ť
	
	private JFileChooser fc;
	
	public AnimImgManager() {
		super("����ͼƬ����");
		imgs = new ArrayList<>();
		importBnt = new JButton("����");
		outPortBnt = new JButton("����");
		fc = new JFileChooser();
	}
	
	
}
