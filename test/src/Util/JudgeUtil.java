package Util;

import javax.swing.JOptionPane;

import Constant.Constant;
import Game.MyPanel;
import Poro.Player;
import Util.SoundUtil;

/**
 * 
* @ClassName: JudgeUtil 
* @Description: 判断游戏是否结束，游戏规则
* @author xudongze
* @date 2018年7月17日 下午10:27:23 
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
	
	//判断是否通过
	public void judgeSuccess() {
		if(player.getPlayerX() == COL-2 && player.getPlayerY() == ROW-2) {
			System.out.println("chenggong");
			//短时间的声音音频加载类实例化
			SoundUtil.playSound("sound/win_wav.wav");
			//通关提示框
			JOptionPane.showMessageDialog(null, "当前关卡:\t" +
					MapUtil.getcurrentMapNo() + "通过!");
			
			if( MapUtil.getcurrentMapNo() == MapUtil.getMapCounts() ) {
				//通关提示框
				JOptionPane.showMessageDialog(null, "恭喜您已经通关!");
			}else {
				//加载下一关地图
				MapUtil.loadNextMap();
				//显示下一关地图
				gamePanel.repaint();
			}
		}
	}

}
