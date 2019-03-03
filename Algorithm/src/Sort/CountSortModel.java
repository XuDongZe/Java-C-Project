package Sort;

import java.util.ArrayList;

public class CountSortModel extends MySortModel{
	
	private int[] counter;
	
	@Override
	public void sort() {
		ArrayList<Integer> list = checkInitData();
		
		int max = list.stream().reduce(0, Math::max);
		counter = new int[max + 1];
		for(int i=0; i<list.size(); i++) {
			counter[list.get(i)]++;
		}
		
		list.clear();
		for(int i=0; i<counter.length; i++) {
			while( counter[i] != 0 ) {
				list.add(i);
				counter[i]--;
			}
		}
		
		addPorc(list);
	}
}
