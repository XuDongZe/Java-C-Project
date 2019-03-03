package Util;

import javax.sound.sampled.*;
import java.io.*;

public class SoundUtil {
	//���췽��
	public SoundUtil() {}
	public static AudioInputStream loadSound(String fileName) {//������Ƶ�ļ���Ϣ
		//�������ļ�
		File file = new File(fileName);
		AudioInputStream stream = null;
		//���ļ�ת��Ϊ��Ƶ������
		try{
			stream = AudioSystem.getAudioInputStream(file);
		}catch(UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		return stream;
	}
	
	public static void playSound(String fileName) {
		//��ȡ��Ƶ��ʽ
		AudioInputStream stream = loadSound(fileName);
		AudioFormat format = stream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip clip = null;
		
		try {
			clip = (Clip) AudioSystem.getLine(info);
		}catch(LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			clip.open(stream);
		}catch(IOException | LineUnavailableException e) {
			e.printStackTrace();
		} 
		
		clip.start();
	}

	
}

