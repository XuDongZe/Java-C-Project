package Constant;

import java.io.File;

import Util.FileManagerUtil;

public interface Constant {
		//����Ĭ�����ش�С��һ��Ϊ32*32,һ�������С���߼���λ32*32��,����1�����߼���λ32����
		static final int CS = 32;
		
		//���ñ�������Ĭ������������λΪС�˵�һ��
		static final int ROW = 15;
		static final int COL = 15;
		
		//���ñ�������ĸ����,��λΪ����
		static final int WIDTH = CS * COL;//480
		static final int HEIGHT = CS * ROW;//480
		
		//�趨��������ӳ�䣬��Ϊ����Ч�ʸߣ���������
		//�����ͼ�������ֱ�ӱ�����ϵ
		static final int LEFT = 0;
		static final int RIGHT = 1;
		static final int UP = 2;
		static final int DOWN = 3;

		//�趨ͼ��ı궨ֵ��Ҫ��map����һ��
		static final int FLOOR 	= 0;
		static final int WALL 	= 1;
		static final int MONSTER = 2;

		//�趨�ػ���
		static final int sleepTime = 350;
		
		//��ͼtxt�ļ��ָ���
		static final String SPACE = ",";
		
		//δ�༭״̬
		static final int UNEDIT = -1;
		
		//����ǰ��
		static final String BGSTR = "background:";
		static final String FGSTR = "foreground:";
		
		//���������
		static final String ENTERPOINT = "enterpoint:";
		static final String EXITPOINT = "exitpoint";
		
		//historyMapջ�������С
		static final int MAXHISTORY = 8;
		
		//panelName
		static final String WELCOME_PANEL = "WelcomePanel";
		static final String MAIN_PANEL = "MainPanel";
		static final String HELP_PANEL = "Help";
		
		//Map�ļ���չ��
		static final String MAPEXT = ".map";
		//Map�ļ�Ĭ�ϱ�����Ŀ¼
		static final String MAPDIR = "map";
		//Img�ļ�Ĭ�ϱ�����Ŀ¼
		static final String CLASSPATH 
		= Constant.class.getClassLoader().getResource("").getPath();
		static final String IMGDIR = "image";
		static final String SNDDIR = "sound";
		static final String SPRDIR = CLASSPATH + "sprite" + File.separator;
		//Img�ļ����ǰ׺
		static final String BG_PREFIX = "bg";
		static final String SCENE_PREFIX = "scene";
		static final String MONSTER_PREFIX = "rw";
		static final String WALL_PREFIX = "wall";
		static final String TOOL_PREFIX = "tool";
		static final String NPC_PREFIX = "npc";
		static final String BNT_PREFIX = "bnt";
		
		//Ĭ��FPS
		static final int FPS = 24;
		static final long fpsNsTime = (long) ((Double.valueOf(1000) / Double.valueOf(FPS)) * 1000000);
}
