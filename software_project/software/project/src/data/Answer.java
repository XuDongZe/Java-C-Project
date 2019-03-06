package data;

import Constant.Constants;

/*
 *  由Problem类 和 用户的输入构造出来的类
 *  用于用户做题质量的统计
 * */
public class Answer implements Constants{
	private Problem problem;	//里面封装了正确答案
	private String userAns;		//用户答案
	
	public boolean isCorrect() {
		return userAns.equals(problem.getAnswer());
	}
	
	@Override 
	public String toString(){
		return problem + userAns;
	}
	
	public Answer() {
		// TODO Auto-generated constructor stub
		
	}
	public Answer(Problem p, String ans) {
		this.problem = p;
		this.userAns = ans;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public String getUserAns() {
		return userAns;
	}
	public void setUserAns(String userAns) {
		this.userAns = userAns;
	}
	
}
