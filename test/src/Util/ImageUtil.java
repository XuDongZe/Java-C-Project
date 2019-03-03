package Util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import Constant.Constant;
import Poro.IconLabel;

/**
 * 
* @ClassName: ImagUtil 
* @Description: 管理图片加载 
* @author xdz
* @date 2018年7月16日 下午8:12:25 
*
 */
public class ImageUtil implements Constant {
	
	private static Map<Integer, ImageIcon> imgEncodeMap;
	
	public static ImageIcon decodeImg(int encodeNo) {
		System.out.println("encode =" + encodeNo + ":" + imgEncodeMap.containsKey(encodeNo));
		return imgEncodeMap.get(encodeNo);
	}
	public static int encodeImg(ImageIcon img) {
		return img.toString().hashCode();
	}
	
	static {
		imgEncodeMap = new HashMap<>();
		HashSet<ImageIcon> icons = loadIcon();
		for(ImageIcon icon: icons) {
			System.out.println(encodeImg(icon)+"," +icon);
			imgEncodeMap.put(encodeImg(icon), icon);
		}
	}
	
	public ImageUtil() {
	}
	
	/**
	 * 
	* @Title: loadIcons 
	* @Description: 加载 image目录下的所有图片资源, 并填充相关数据结构: 编码Map和各个类别list
	* @Description: 有求所有图片资源都是 png格式
	* @param 
	* @return void
	 */
	public static HashSet<ImageIcon>  loadIcon() {
		HashSet<ImageIcon> icons = new HashSet<>();
		String[] names = FileManagerUtil.getFileNamesByType(IMGDIR, ".png");
		for(String name : names) {
			icons.add(loadIcon(name));
		}
		return icons;
	}
	
	/**
	 * 
	* @Title: loadIcon 
	* @Description: 根据文件名 加载image 文件加下的单个图片资源, 
	* @param @param fileName 图片文件名, 不需要path, 默认image目录下
	* @param @return
	* @return ImageIcon
	 */
	public static ImageIcon loadIcon(String name) {
		BufferedImage image = null;
		URL url = ImageUtil.class.getClassLoader().getResource(IMGDIR + File.separator + name);
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image == null ? null : new ImageIcon(image);
	}
	
	public static HashSet<IconLabel> processIconLabels(HashSet<String> urls) {
		HashSet<IconLabel> iconLabels = new HashSet<>();
		for( String url : urls ) {
			iconLabels.add(new IconLabel(url));
		}
		return iconLabels;
	}
	
	/**
	 * 
	* @Title: getIconLabelMatrix 
	* @Description: 将Set转变为一个二维数组
	* @param @param col
	* @param @return
	* @return IconLable[][]
	 */
	public static IconLabel[][] getIconLabelMatrix(HashSet<IconLabel> icons, int col) {
		if( icons == null ) 	return null;
		
		int row =  icons.size() / col;
		row = (icons.size() % col == 0) ? row : row + 1;
		int i = 0, j = 0;
		IconLabel[][] res = new IconLabel[row][col];
		for(Iterator<IconLabel> it = icons.iterator(); it.hasNext(); ) {
			if( j == col ) {
				i++; j = 0;
			}
			res[i][j++] = it.next();
		}
		for(; j< col; j++)	
			res[i][j] = null;
		return res;
	}
	
	private static double getGray(BufferedImage bufferedImage, int i, int j) {
		int rgb = bufferedImage.getRGB(i, j);
		int r = 0xff & rgb;
		int g = 0xff & (rgb >> 8);
		int b = 0xff & (rgb >> 16);
		return (r*0.299+g*0.587+b*0.114);
	}
	
	private static double getMeanGray(BufferedImage img) {
		double meanGray = 0.0;
		int width = img.getWidth();
		int height = img.getHeight();
		
		for(int i=0; i<img.getWidth(); i++)
			for(int j=0; j<img.getHeight(); j++)
				meanGray += getGray(img, i, j);
		
		return meanGray / (width * height);
	}
	
	public static BufferedImage alphaProcess(BufferedImage img, int alpha) {
		double meanGray = getMeanGray(img);
		int wdith = img.getWidth();
		int height = img.getHeight();
		BufferedImage resImg = new BufferedImage(wdith, height, BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int i=0; i<wdith; i++) {
			for(int j=0; j<height; j++) {
				int rgb = img.getRGB(i, j);
				int r = 0xff & rgb;
				int g = 0xff & (rgb >> 8);
				int b = 0xff & (rgb >> 16);
				System.out.print( r + " " + g + " " + b + "\n" );
				
				if( (r*0.299+g*0.587+b*0.114) > meanGray) {
					rgb = r + g << 8 + b << 16 + alpha << 24;
				}
				resImg.setRGB(i, j, rgb);
			}
		}
		return resImg;
	}
	
	public static BufferedImage getBufferImg(ImageIcon imageIcon) {
		Image img = imageIcon.getImage();
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		
		BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = res.getGraphics();
		g.drawImage(img, width, height, null);
		g.dispose();
		
		return res;
	}

	public static void main(String[] args) {
		loadIcon();
	}
	
}
