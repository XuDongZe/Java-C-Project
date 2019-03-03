package Constant;

import java.io.File;

import Util.FileManagerUtil;

public interface Constant {
		//设置默认像素大小，一般为32*32,一个人物大小的逻辑单位32*32像,人物1步的逻辑单位32像素
		static final int CS = 32;
		
		//设置背景方格默认行列数，单位为小人的一步
		static final int ROW = 15;
		static final int COL = 15;
		
		//设置背景方格的高与宽,单位为像素
		static final int WIDTH = CS * COL;//480
		static final int HEIGHT = CS * ROW;//480
		
		//设定按键数字映射，因为数字效率高，不能随便改
		//这个跟图像绘制有直接编码联系
		static final int LEFT = 0;
		static final int RIGHT = 1;
		static final int UP = 2;
		static final int DOWN = 3;

		//设定图像的标定值，要与map保持一致
		static final int FLOOR 	= 0;
		static final int WALL 	= 1;
		static final int MONSTER = 2;

		//设定重绘间隔
		static final int sleepTime = 350;
		
		//地图txt文件分隔符
		static final String SPACE = ",";
		
		//未编辑状态
		static final int UNEDIT = -1;
		
		//背景前景
		static final String BGSTR = "background:";
		static final String FGSTR = "foreground:";
		
		//人物出生点
		static final String ENTERPOINT = "enterpoint:";
		static final String EXITPOINT = "exitpoint";
		
		//historyMap栈的最深大小
		static final int MAXHISTORY = 8;
		
		//panelName
		static final String WELCOME_PANEL = "WelcomePanel";
		static final String MAIN_PANEL = "MainPanel";
		static final String HELP_PANEL = "Help";
		
		//Map文件拓展名
		static final String MAPEXT = ".map";
		//Map文件默认保存子目录
		static final String MAPDIR = "map";
		//Img文件默认保存子目录
		static final String CLASSPATH 
		= Constant.class.getClassLoader().getResource("").getPath();
		static final String IMGDIR = "image";
		static final String SNDDIR = "sound";
		static final String SPRDIR = CLASSPATH + "sprite" + File.separator;
		//Img文件类别前缀
		static final String BG_PREFIX = "bg";
		static final String SCENE_PREFIX = "scene";
		static final String MONSTER_PREFIX = "rw";
		static final String WALL_PREFIX = "wall";
		static final String TOOL_PREFIX = "tool";
		static final String NPC_PREFIX = "npc";
		static final String BNT_PREFIX = "bnt";
		
		//默认FPS
		static final int FPS = 24;
		static final long fpsNsTime = (long) ((Double.valueOf(1000) / Double.valueOf(FPS)) * 1000000);
}
