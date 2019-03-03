package Sort;

import java.util.ArrayList;

public class Sort {
	private MySortIO sortIO = new MySortIO();
	private MySortModel sortModel;
	
	public Sort() {
	}
	public Sort(MySortModel sortModel) {
		this.sortModel = sortModel;
	}
	
	public void printProcStack() {
		System.out.println(sortModel.getClass() + ":");
		sortIO.printProcStack(sortModel.getProcStack());
	}
	public void printResult() {
		ArrayList<Integer> result = sortModel.getResult();
		if(result == null) {
			System.out.println("Sort is not complete!");
		}
		System.out.println("there is the sorted array: ");
		sortIO.printArrayList(result);
	}
	
	public void sort() {
		ArrayList<Integer> list = sortIO.readIntNums();
		sortModel.addPorc(list);
		sortModel.sort();
		printProcStack();
	}
}
