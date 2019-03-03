package Sort;

import java.util.ArrayList;
import java.util.Scanner;

public class MySortIO {
	private Scanner in;
	
	public MySortIO() {
		in = new Scanner(System.in);
	}
	
	public void printProcStack(ArrayList<ArrayList<Integer>> procStack) {
		if(procStack == null) {
			System.out.println("procStack is null!");
			return;
		}
		ArrayList<Integer> list = null;
		for(int i=0; i<procStack.size(); i++) {
			list = procStack.get(i);
			System.out.print("Step " + (i) +":\t");
			for(int j=0;list!=null && j<list.size(); j++) {
				System.out.print(list.get(j) + " ");
			}
			System.out.println();
		}
		System.out.println("Sort is Over!");
	}
	public void printArrayList(ArrayList<Integer> list) {
		if(list != null) {
			for(int i=0; i<list.size(); i++) {
				System.out.print(list.get(i) + " ");
			}
		}
	}
	
	public ArrayList<Integer> readIntNums() {
		System.out.println("please input integer numbers: ");
		ArrayList<Integer> array = new ArrayList<>();
		while(in.hasNextInt()) {
			array.add(in.nextInt());
		}
		System.out.println("read OK!");
		return array;
	}
	public ArrayList<Integer> readIntNums(String filename) {
		ArrayList<Integer> list = new ArrayList<>();
		return list;
	}
}
