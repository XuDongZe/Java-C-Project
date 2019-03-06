package Config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Logger;
import org.xml.sax.InputSource;

public class Config {
	//读取系统外(即Jar包外的配置文件)
	//外部工程在引用该jar包时需在工程目录下建立Config目录存放配置文件
	public static Properties properties;
	static {
		properties = new Properties();
		try {
			String filePath = System.getProperty("user.dir") + "/Config/init.properties";
			System.out.println(filePath);
			InputStream is = new BufferedInputStream(new FileInputStream(filePath));
			properties.load(is);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void flushConfig(String comments) {
		String filePath = System.getProperty("user.dir") + "/Config/init.properties";
		OutputStream os;
		try {
			os = new BufferedOutputStream(new FileOutputStream(filePath));
			properties.store(os, comments);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String ip = Config.properties.getProperty("NetManager.IP");
		String port = Config.properties.getProperty("Constants.PORT");
	}
}
