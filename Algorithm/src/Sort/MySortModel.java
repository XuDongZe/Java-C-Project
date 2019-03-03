package Sort;

import java.util.ArrayList;

/**
* @ClassName: Sort 
* @Description: MVC -> Model，
* 				其中getInit，getResult都返回对应的list副本，addProc加入对应的list副本
* 				所以引出了一个原则，若遇到元素类型为引用类型的容器，考虑get，add时是
* 				应用本身还是先获取该引用的副本，在使用该副本的引用。
* @author xudongze
* @date 2019年1月1日 下午8:08:18 
 */
public abstract class MySortModel {
	protected ArrayList<ArrayList<Integer>> procStack;
	
	public MySortModel() {
		procStack = new ArrayList<>();
	}
	
	private ArrayList<Integer> getIdxList(int idx) {
		if( procStack==null || procStack.isEmpty() ) 
			return null;
		if( idx < 0 || idx >= procStack.size() )
			return null;
		return new ArrayList<>(procStack.get(idx));
	}

	
	public ArrayList<Integer> checkInitData() {
		ArrayList<Integer> data = getInitData();
		if(data==null || data.isEmpty()) {
			System.err.println("none of init data!");
			System.exit(1);;
		}
		return data;
	}
	public ArrayList<Integer> getInitData() {
		return getIdxList(0);
	}
	public ArrayList<Integer> getResult() {
		return getIdxList(procStack.size() - 1);
	}
	public ArrayList<ArrayList<Integer>> getProcStack() {
		return procStack;
	}  
	
	/**
	 * 
	* @Title: addPorc 
	* @Description: 接受一个ArrayList<Integer>类型的容器指针，将其加入到procStack中。
	* 				注意要生成list的副本在加入，如此在函数外对list的更改百年不会影响到procStack内容。
	* @param @param list
	* @return void
	 */
	public void addPorc(ArrayList<Integer> list) {
		procStack.add(new ArrayList<>(list));
	}
	
	/**
	* @Title: sort 
	* @Description: 抽象方法，前提是procStack里面有初始化的内容。
	* @param 
	* @return void
	 */
	public abstract void sort();

	public void sort(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		
	}
   
}
