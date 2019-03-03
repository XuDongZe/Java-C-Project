package Sort;

import java.util.ArrayList;

/**
 * 
* @ClassName: SelectSortModel 
* @Description: 选择排序：对于n个数
* 				step 0: 在0 -> n-1位置中选出一个最小的，放到位置0
* 				step 1:	在1 -> n-1位置中选出一个最小的，放到位置1
* 				...
* 				steo n-2: 在n-2 -> n-1位置中选出一个最小的，放到位置n-2
* 				n-1 -> n-1自然有序				
* 				
* 				每一步安排一个数，n个数需要n-1步骤；每个步骤中需要比较 n-i 次，平均n/2次比较每步骤。
* 				找最小值时记录min_idx 而非 min_value的原因：
	* 				找到最小之后需要将其放到前面的位置上，然而我们不能凭空占用人家的位置，也要把前面的被
	*				占位置的数安置好，也就是放置到该最小数的位置上。而要实现数组中两个数的交换，知道值是
	*				不够的，需要知道两个数的下标。
	*				也就是 索引 比 该索引的实际值 传递了更多的信息。
* 				
* 				时间复杂度O(n^2)
* 				空间复杂度O(1)	-> 记录每一步中最小的那个数的下标 & 辅助交换的变量
* 				稳定性 ； 
* 
* @author xudongze
* @date 2019年1月1日 下午10:15:44 
*
 */
public class SelectSortModel extends MySortModel{

	@Override
	public void sort() {
		ArrayList<Integer> list = checkInitData();
		int n = list.size();
		
		for(int i=0; i<n; i++) {
			int k = i;
			for(int j=i+1; j<n; j++) {
				if(list.get(j) < list.get(k))	k = j;
			}
			if(k != i) {
				int t = list.get(k);
				list.set(k, list.get(i));
				list.set(i, t);
			}
			addPorc(list);
		}
	}
	
}
