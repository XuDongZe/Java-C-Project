package Sort;

import java.util.ArrayList;

public class MergeSortModel extends MySortModel{
	
	private ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right) {
		ArrayList<Integer> res = new ArrayList<>();
		
		int i=0, j=0, m=left.size(), n=right.size();
		while(i<m && j<n) {
			if(left.get(i) < right.get(j)) {
				res.add(left.get(i));
				i++;
			} else {
				res.add(right.get(j));
				j++;
			}
		}
		while(i<m) {
			res.add(left.get(i++));
		}
		if(j<n) {
			res.add(right.get(j++));
		}
		
		addPorc(res);
		return res;
	}
	
	private ArrayList<Integer> mergeSort(ArrayList<Integer> list) {
		if(list.size() < 2) {
			return list;
		}
		int idx = list.size() / 2;
		ArrayList<Integer> left = new ArrayList<Integer>(list.subList(0, idx));
		ArrayList<Integer> right = new ArrayList<Integer>(list.subList(idx, list.size()));
		return merge( mergeSort(left), mergeSort(right) );
	}
	
	@Override
	public void sort() {
		ArrayList<Integer> list = checkInitData();
		mergeSort(list);
	}
}
