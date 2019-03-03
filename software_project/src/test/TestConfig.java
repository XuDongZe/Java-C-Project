package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import Config.Config;

import org.xml.sax.InputSource;

public class TestConfig {
	
	public static void main(String[] args) {
		String ip = Config.properties.getProperty("IP");
		String port = Config.properties.getProperty("PORT");
		System.out.println("ip="+ip+"\nport="+port);
	}
}
