package Dao;

public class MyProblemNotEnoughException extends Exception {
	public MyProblemNotEnoughException (String ExceptionMsg) {
		super(ExceptionMsg);
	}
}