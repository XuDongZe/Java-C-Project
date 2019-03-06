package data;

import java.security.Principal;

import javax.naming.spi.DirStateFactory.Result;

import Constant.Constants;


/*
 * 	重写equals()、 toString()、 hashCode()方法
 * 	equals()、 hashCode() 分别在一般集合或者哈希组织集合中用于比较
 * 	toString()	用于显示
 * */
public class User implements Constants{
	private String ID;
	private String psw;
	private int sumNums;
	private int correctNums;
	private int level;
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		return PRIME * ((ID == null)? 0: ID.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)	return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass())	return false;
		
		final User other = (User)(obj);
		return 		ID.equals(other.ID);
	}
	
	@Override
	//注意在每个对象的最后面加了一个spliter,这样在数组中传递的话,decoder就不会出问题
	public String toString() {
		return ID + SPLITER + psw + SPLITER + sumNums
				+ SPLITER + correctNums + SPLITER + level + SPLITER;
	}

	public String getID() {
		return ID;
	}

	public User setID(String iD) {
		ID = iD;
		return this;
	}

	public String getPsw() {
		return psw;
	}

	public User setPsw(String psw) {
		this.psw = psw;
		return this;
	}

	public int getSumNums() {
		return sumNums;
	}

	public User setSumNums(int sumNums) {
		this.sumNums = sumNums;
		return this;
	}

	public int getCorrectNums() {
		return correctNums;
	}

	public User setCorrectNums(int correctNums) {
		this.correctNums = correctNums;
		return this;
	}

	public int getLevel() {
		return level;
	}

	public User setLevel(int level) {
		this.level = level;
		return this;
	}
	
}
