package Sort;

import java.util.ArrayList;

/**
 * 
* @ClassName: BubbleSortModel 
* @Description: 冒泡排序: n个数
* 				每次从0 -> 当前最后一个位置。 两两比较交换，最终一轮比较的结果是，
* 				最大的数交换到最后一个位置。
* 				与SelectSort比较起来，虽然具有相同的时间复杂度，但是交换的频率更高，
* 				所以具有更大的常数时间。
* 				
* 				时间复杂度：O(n^2) 	-> (n-1) * (n/2)
* 				空间复杂度：O(1)		-> 辅助交换的变量。
* 				稳定性： 
* 				
* @author xudongze
* @date 2019年1月1日 下午10:26:17 
*
 */
public class BubbleSortModel extends MySortModel {
	@Override
	public void sort() {
		ArrayList<Integer> list = checkInitData();
		int n = list.size();
		
		for(int i=1; i<n; i++) {
			for(int j=0; j<n-i; j++) {
				if(list.get(j) > list.get(j+1)) {
					int t = list.get(j);
					list.set(j, list.get(j+1));
					list.set(j+1, t);
				}
			}
			/**
			 * 注意这里的list是一个数组的管理者，也就是指针。
			 * 而procStack每次放进去的应该是一个新的内存空间，这样才能构成一个二维数组
			 * 也就是必须重新申请内存。
			 * 否则，若使用addProc(list),那么在双重for循环之后的
			 * 对list的引用，会导致之前存入procStack的内容遭到更改。最终的结果是procStack里面
			 * 的每一项都变为最后一次加入时的list内容。这一步骤在addProc内部去做。那么用户在传递时
			 * 无论是指针还是副本，都可行。
			 */
			addPorc(list);
		}
	}
}
