package Sort;

import java.util.ArrayList;

public class QuickSortModel extends MySortModel{
	
	private void swap(ArrayList<Integer> list, int a, int b) {
		if(a == b)
			return;
		int t = list.get(a);
		list.set(a, list.get(b));
		list.set(b, t);
	}
	
	private int partition(ArrayList<Integer> list, int l, int r) {
		int idx = l, base = list.get(l);
		int left = l, right = r;
		while(left < right) {
			while( left<right && list.get(right) >= base )
				right--;
			while( left<right && list.get(left) <= base ) 
				left++;
			if(left < right) {
				swap(list, left, right);
			}
		}
		swap(list, right, idx);
		
		addPorc(list);
		return right;
	}
	
	private void quickSort(ArrayList<Integer> list, int left, int right) {
		if( right <= left )
			return;
		int mid = partition(list, left, right);
		quickSort(list, 0, mid-1);
		quickSort(list, mid+1, right);
	}
	
	@Override
	public void sort() {
		ArrayList<Integer> list = checkInitData();
		quickSort(list, 0, list.size()-1);
	}
	
}
