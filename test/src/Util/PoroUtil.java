package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import Constant.Constant;
import Poro.Sprite;

public class PoroUtil implements Constant{
	public static Object readObj(String url) {
		Object obj = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SPRDIR + url));
			obj = ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	public static void saveObj(String url, Object obj) {
		try {
			File file = new File(SPRDIR + url);
			if( !file.exists() ) {
				file.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(obj);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(SPRDIR);
	}
}
