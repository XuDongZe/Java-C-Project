package Util;

import javax.sound.sampled.*;
import java.io.*;

public class SoundUtil {
	//构造方法
	public SoundUtil() {}
	public static AudioInputStream loadSound(String fileName) {//加载音频文件信息
		//打开声音文件
		File file = new File(fileName);
		AudioInputStream stream = null;
		//将文件转化为音频输入流
		try{
			stream = AudioSystem.getAudioInputStream(file);
		}catch(UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		return stream;
	}
	
	public static void playSound(String fileName) {
		//获取音频格式
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

