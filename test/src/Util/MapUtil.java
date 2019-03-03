package Util;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Constant.Constant;

/**
 * 
* @ClassName: MapUtil 
* @Description: Map file IO drawMap
* @author xudongze
* @date 2018年7月17日 下午9:15:41 
*
 */
public class MapUtil implements Constant {
	private static int[][] currentBgMap;  //当前背景图
	private static int[][] currentFgMap;  //当前前景图
	private static String currentFn;
	
	static {
		currentBgMap = new int[ROW][COL];
		currentFgMap = new int[ROW][COL];
		currentFn = ".//map//map01" + MAPEXT;
	}
	
	public MapUtil() {
	}
	
	/**
	 * 
	* @Title: readMap 
	* @Description: 内部调用，读入文件到当前地图中
	* @param 要读入的文件名
	* @return void
	 */
	private static void readMap(String readFileName) {
		File readFile = new File(readFileName);
		String line;
		int bgRow = 0, fgRow = 0;
		int state = 0; boolean jump = false;
		try {
			BufferedReader in = new BufferedReader(new FileReader(readFile));
			try {
				while (true) {
					jump = false;
					if((line = in.readLine()) != null) {
						String[] temp = line.split(",");
						if( temp != null && temp.length > 0 ) {
							if( temp[0].equals(BGSTR) ) { state = 1; jump = true; }
							else if( temp[0].equals(FGSTR) ) { state = 2; jump = true; }
						}
						if( state == 1 && !jump) {
							for(int j=0; j<temp.length; j++) {
								currentBgMap[bgRow][j] = Integer.parseInt(temp[j]);
							}
							bgRow++;
						}else if( state == 2 && !jump ){
							for(int j=0; j<temp.length; j++) {
								currentFgMap[fgRow][j] = Integer.parseInt(temp[j]);
							}
							fgRow++;
						}
					}else {
						break;
					}
				}
				setCurrentFn(readFileName);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 *@Title: saveMap 
	* @Description: 将编辑的地图保存到目标文件
	* @return void
	 */
	public static void saveMap(String writeFileName, int[][] bgMap, int[][] fgMap) {
		File wirteFile = new File(writeFileName);
		int row = 0;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(wirteFile));
			out.write(BGSTR); out.newLine();
			while(bgMap!=null && row < bgMap.length) {
				StringBuilder temp = new StringBuilder();
				for(int j=0; j < bgMap[row].length; j++) {
					temp.append( bgMap[row][j] );
					if( j != bgMap[row].length - 1) {
						temp.append(",");
					}
				}
				out.write(temp.toString()); out.newLine();
				row++;
			}
			
			row = 0; out.write(FGSTR); out.newLine();
			while( fgMap!=null && row < fgMap.length) {
				StringBuilder temp = new StringBuilder();
				for(int j=0; j < fgMap[row].length; j++) {
					temp.append( fgMap[row][j] );
					if( j != fgMap[row].length - 1) {
						temp.append(",");
					}
				}
				out.write(temp.toString()); out.newLine();
				row++;
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: loadMap 
	* @Description: 加载任意关卡的地图
	* @param @param mapNo, 从1开始，到mapCounts为止
	* @return void
	 */
	public static void loadMap(String mapFn) {
		readMap(mapFn);
	}
	public static void loadMap(int mapNo) {
		if( mapNo < 10 )
			readMap(".//map//map" + "0" + (mapNo) + MAPEXT);
		else 
			readMap(".//map//map" + (mapNo) + MAPEXT);
	}
	public static void loadNextMap() {
		loadMap( getcurrentMapNo() + 1 );
	}
	public static void loadPrevMap() {
		loadMap(getcurrentMapNo() - 1 );
	}
	
	public static int getcurrentMapNo() {
		int lastDotIndex = MapUtil.currentFn.lastIndexOf('.');
		String mapNoStr = MapUtil.currentFn.substring(lastDotIndex-2, lastDotIndex);
		System.out.println(mapNoStr);
		
		return Integer.parseInt(mapNoStr);
	}
	
	public static void drawMap(Graphics g) {
		readMap(currentFn); 
		for(int x = 0; x < ROW; x++) {
			for(int y = 0; y < COL; y++) {
				g.drawImage(ImageUtil.decodeImg(currentBgMap[x][y]).getImage(), y*CS, x*CS, null);
				g.drawImage(ImageUtil.decodeImg(currentFgMap[x][y]).getImage(), y*CS, x*CS, null);
			}
		}
	}
	
	public static void printMap() {
		System.out.println(BGSTR);
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				System.out.print(currentBgMap[i][j]);
				if( j != COL-1 )	
					System.out.print(SPACE);
			}
			System.out.println();
		}
		System.out.println(FGSTR);
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				System.out.print(currentFgMap[i][j]);
				if( j != COL-1 )	
					System.out.print(SPACE);
			}
			System.out.println();
		}
	}
	
	public static int[][] getCurrentBgMap(String fileName) {
		readMap(fileName);
		return currentBgMap;	
	}
	public static int[][] getCurrentFgMap(String fileName) {
		readMap(fileName);
		return currentFgMap;	
	}
	
	public static String getCurrentFn() {
		return currentFn;
	}
	public static void setCurrentFn(String fn) {
		currentFn = fn;
	}
	
	public static int getMapCounts() {
		return FileManagerUtil.getFilesByType(MAPDIR, MAPEXT).length;
	}

	/**
	 * 
	* @Title: px2Step 
	* @Description: 将像素单位转换为以人物的 步长，也就是砖块大小为单位
	* @param 像素长度值
	* @return 砖块长度值
	 */
	public static int pxToStep(int px) {
		return px / CS;
	}
	
	/**
	 * 
	* @Title: stepToPx 
	* @Description: 将以步长为单位转换以像素为单位
	* @param @param step
	* @param 砖块值大小
	* @return 像素值大小
	 */
	public static int stepToPx(int step) {
		return step * CS;
	}

	public static void main(String[] args) {
		String readFile = ".//map//map01" + MAPEXT;
		String writeFile = "/C:/Users/xudongze/workspace/test/map/map02" + MAPEXT;
		
		MapUtil.saveMap(writeFile,
						MapUtil.getCurrentBgMap(readFile),
						MapUtil.getCurrentFgMap(readFile));
		MapUtil.printMap();
	}

}
