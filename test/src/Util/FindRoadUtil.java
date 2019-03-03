package Util;

import java.util.LinkedList;
import java.util.Queue;
import Constant.Constant;

public class FindRoadUtil implements Constant {
	public int[][] map;
	//Find Road Auto data
	private static int[] dx = {-1,1,0,0};
	private static int[] dy = {0,0,-1,1};
	private boolean[][] visited = new boolean[ROW][COL];
	private int pathLength = 0;
	private int [] directionPath = new int [ROW * COL];
	
	/**
	 * 判断当前人物是否可以移动到该位置
	 */
	public FindRoadUtil(){}
	public FindRoadUtil(int [][]map) {
		this.map = map;
	}
	
	public boolean moveIsAllow(int x, int y) {
		return ( y>=0&&y<ROW && x>=0&&x<COL && 
				(ImageUtil.isBg(map[y][x]) || map[y][x]==UNEDIT) );
	}
	
	//自动寻路，需要知道本身所在位置和目的地所在位置
	//left0 right1 up2 down3
	private void initVisited() 
	{
		for(int j=0; j<COL; j++) 
			for(int i=0; i<ROW; i++)
				visited[i][j] = false;
	}
	
	public boolean findRoad(int startX, int startY, int endX, int endY)
	{	
		Queue<Integer> queue = new LinkedList<Integer>();
		
		//current = currentY * COL + currentX,currentX = current%COL,currentY = current/COL:
		int currentX = startX,currentY = startY,currentPos = currentY * COL + currentX;
		int nextX,nextY;
		
		int [][] fatherDirection = new int [ROW][COL];
		int [][] fatherPoint = new int [ROW][COL];
		
		boolean result = false;
		
		initVisited();
		visited[currentX][currentY] = true;
		queue.offer(currentPos);
	
		while( queue.peek() != null  )
		{
			currentPos = queue.remove();//若队列为空那么抛出异常
			currentX = currentPos % COL;
			currentY = currentPos / COL;
			
			if(currentX == endX && currentY == endY)
			{
				result = true;
				break;
			}

			for(int i=0; i<4; i++ ){//按照左右上下 的顺序寻找
				nextX = currentX + dx[i];
				nextY = currentY + dy[i];
				//必须在经过边界检查的前提下使用二维数组
				if( moveIsAllow(nextX, nextY) && !visited[nextX][nextY] ){
					queue.offer(nextY * COL + nextX);//若队列满则抛出异常
					//record farther node information
					fatherPoint[nextX][nextY] = currentPos;
					fatherDirection[nextX][nextY] = i;
					visited[nextX][nextY] = true;
				}
			}
		}
		
    	//get directionPath through fatherNode and fatherDirection
    	pathLength = 0;
		currentX = endX;
		currentY = endY;
		int fatherX = 0,fatherY = 0;
		
    	if(result){
    		//必须在能够知道路径的时候才可以进行回溯处理，
    		//否则将会由于永远也回溯不到起点而造成 whlie 死循环从而产生数组溢出
    		while( !(currentX == startX && currentY == startY) )
    		{
    			directionPath[pathLength] = fatherDirection[currentX][currentY];
    			fatherX = fatherPoint[currentX][currentY] % COL;
    			fatherY = fatherPoint[currentX][currentY] / COL;
    			currentX = fatherX;
    			currentY = fatherY;
    			pathLength++;
    		}
        	for(int i=0 ; i<pathLength/2; i++)
        	{
        		int temp = 0;
        		temp = directionPath[pathLength-1-i];
        		directionPath[pathLength-1-i] = directionPath[i];
        		directionPath[i] = temp;
        	}
    	}
    	
    	return result;
	}
	
	public int getPathLength() {
		return pathLength;
	}
	public int[] getDirectionPath() {
		return directionPath;
	}
}
