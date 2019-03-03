package Util;

import javax.swing.JOptionPane;

import Constant.Constant;
import Game.MyPanel;
import Poro.Player;
import Util.SoundUtil;

/**
 * 
* @ClassName: JudgeUtil 
* @Description: �ж���Ϸ�Ƿ��������Ϸ����
* @author xudongze
* @date 2018��7��17�� ����10:27:23 
*
 */
public class JudgeUtil implements Constant{
	
	private Player player;
	private MyPanel gamePanel;
	
	public JudgeUtil(Player player, MyPanel panel) {
		this.player = player;
		this.gamePanel = panel;
	}
	public JudgeUtil() {
		
	}
	
	//�ж��Ƿ�ͨ��
	public void judgeSuccess() {
		if(player.getPlayerX() == COL-2 && player.getPlayerY() == ROW-2) {
			System.out.println("chenggong");
			//��ʱ���������Ƶ������ʵ����
			SoundUtil.playSound("sound/win_wav.wav");
			//ͨ����ʾ��
			JOptionPane.showMessageDialog(null, "��ǰ�ؿ�:\t" +
					MapUtil.getcurrentMapNo() + "ͨ��!");
			
			if( MapUtil.getcurrentMapNo() == MapUtil.getMapCounts() ) {
				//ͨ����ʾ��
				JOptionPane.showMessageDialog(null, "��ϲ���Ѿ�ͨ��!");
			}else {
				//������һ�ص�ͼ
				MapUtil.loadNextMap();
				//��ʾ��һ�ص�ͼ
				gamePanel.repaint();
			}
		}
	}

}
