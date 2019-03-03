package Util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.net.URL;
import java.nio.file.ReadOnlyFileSystemException;

import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;

import Constant.Constant;

public class FileManagerUtil implements Constant{
	
	/**
	 *  历史记录上一次的文件操作目录
	 */
	private static String historyDir; 
	private static String defaultDir;
	private static class MyFileFilter implements FileFilter {
		private String extName;
		public MyFileFilter(String ext) {
			extName = ext;
		}
		@Override
		public boolean accept(File paramFile) {
			return 	paramFile.isFile() &&
					paramFile.getName().substring(paramFile.getName().indexOf("."))
					.equals(extName);
		}
	}
	
	private static class MyFileNameFileter implements FilenameFilter {
		private String extName;
		public MyFileNameFileter(String ext) {
			extName = ext;
		} 
		public boolean accept(File paramFile, String name) {
			return name != null && name.endsWith(extName);
		} 
	}
	
	static {
		historyDir = null;
		defaultDir = CLASSPATH;
	}
	
	/**
	 * 
	* @Title: getFileChooser 
	* @Description: 返回特定目录下的文件选择面板
	* @param @param subDirector 
	* @return JFileChooser
	 */
	public static JFileChooser getFileChooser(String dir) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(dir));
		historyDir = dir;
		
		fc.setDialogTitle("请选择文件位置");
		fc.setApproveButtonText("确定");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		return fc;
	}
	public static JFileChooser getFileChooser() {
		if(historyDir == null) {
			return getFileChooser(defaultDir);
		}
		return getFileChooser(historyDir);
	}

	public static String[] getFilePathsByType(String dir, String type) {
		File[] files = getFilesByType(dir, type);
		if( files != null ) {
			String [] str = new String[files.length];
			for( int i=0; i<str.length; i++ ) {
				str[i] = files[i].getPath();
			}
			return str;
		}
		return null;
	}
	public static String[] getFileNamesByType(String dir, String type) {
		return new File(CLASSPATH + dir).list(new MyFileNameFileter(type));
	}
	public static File[] getFilesByType(String dir, String type) {
		return new File(CLASSPATH + dir).listFiles(new MyFileFilter(type));
	}

	
	public static String getHistoryDir() {
		return historyDir;
	}
	public static void setHistoryDir(String historyDir) {
		FileManagerUtil.historyDir = historyDir;
	}
	
	public static void main(String[] args) {
		String[] str = FileManagerUtil.getFileNamesByType(IMGDIR, ".png");
		for( String s: str )
			System.out.println( s );
	}	
}
