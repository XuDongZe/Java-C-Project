package Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RadixSortModel extends MySortModel{

	private Map<Integer, ArrayList<Integer>> counter = new LinkedHashMap<>();
	
	@Override
	public void sort() {
		// TODO Auto-generated method stub
		ArrayList<Integer> list = checkInitData();
		int n = list.size();
		

	}
	
	private void radixSort(ArrayList<Integer> list, int maxDigit) {
		for(int i=0, mod=10, div=1; i<maxDigit; mod*=10, div*=10) {
			for(int idx=0; idx<list.size(); idx++) {
				int bucket = (list.get(i) / div) % mod;
				if(counter.get(bucket) == null)
					counter.put(bucket, new ArrayList<>());
				counter.get(bucket).add(list.get(i));
			}
		}
	}
	
}
