package Sort;

import java.util.ArrayList;

/**
 * 
* @ClassName: InsertSortModel 
* @Description: 插入排序：n个数字
* 				将整个数组分为2部分：左边部分有序，右边部分无序。
* 				每次将一个无序部分的数放到左边部分适当的位置，此过程中伴随数组元素的挪动。
* 				类似于复杂化的exchange(a, b)函数。重点搞清楚数组元素空间此刻是否可以被覆盖。
* 				初始化：左：0->0, 右：1->n-1
* 				迭代：
* 					拿到当前处理的数a
* 					从该数的前一位置到位置0遍历，得到第一个小于该数的位置下标j。由于从小到大排序，
* 					我们从右往左遍历时，那么在[j]左边的数（包括[j]）都比该数小，那么理所当然的，
* 					一次排序之后，a应该在j+1的位置上。
* 				所以应该给j+1空出一个位置，采取依次往后挪动的方式。
* 				但是到此刻，我们应该注意其数组内存的可用情况：
* 				[i] -> 将要插入到位置 j+1，
* 				[j+1] -> [n-1], 需要往后挪动一个单元，再次之前需要将最后一个单元缓存。
* 				那么，我们采取这种策略：
* 				t = array[i];
* 				find j;
* 				array[i] = array[array.length-1]; //这样之后没有丢失数据，两个数字仍然都在右边的无序部分
* 				for(k = j+1; k<n-1; k++) array[j+1] = array[j];
* 				array[j+1] = t;
*				
*				或者这种策略：我们只需关注有序部分的后挪即可：
*				t = array[i];
*				find j;
*				for(k = i-1; k>=j+1; k--) array[j+1] = array[j];
*				array[j+1] = t;
* 
* 				时间复杂度：O(n^2)
* 				空间复杂度：O(1)
* 				稳定性：
* @author xudongze
* @date 2019年1月2日 上午12:31:20 
*
 */
public class InsertSortModel extends MySortModel{

	@Override
	public void sort() {
		ArrayList<Integer> list = checkInitData();
		int n = list.size();
		
		for(int i=1; i<n; i++) {
			int t = list.get(i);
			int j = i-1;
			while(j>=0 && list.get(j) >= t)  {
				list.set(j+1, list.get(j));
				j--;
			}
			list.set(j+1, t);
			addPorc(list);
		}
	}
}
