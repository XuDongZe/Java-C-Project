package data;

import Constant.Constants;

/*
 * 	数据库tabel对象	tb_problem
 * */
public class Problem implements Constants{
	private int seq;	//非负数, 对应数组下标
	private String content;
	private String answer;
	private int score;
	private int level;
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)	return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass())	return false;
		
		final Problem other = (Problem)(obj);
		return 		(seq == other.seq);
	}
	
	@Override
	//注意在每个对象的最后面加了一个spliter,这样在数组中传递的话,decoder就不会出问题
	public String toString() {
		return seq + INNER_SPLITER + content + INNER_SPLITER + answer
				+ INNER_SPLITER + score + INNER_SPLITER + level + SPLITER;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public Problem() {
		// TODO Auto-generated constructor stub
	}
	
	public Problem(int seq) {
		produceAOB();
		this.seq = seq;
		level = JudgeLevel();
		score = JudgeScore();
	}
	
	/*
	 * 	生成 A op B	类型的 Problem
	 * 	op			range
	 * 	ADD SUB		[0-100]
	 * 	MUL DIV		[0-10] && 除法B非0	
	 * */
	private void produceAOB() {
		final int ADD=0,SUB=2,DIV=1,MUL=3;
		int oprandx,oprandy;
		int opcode=(int)(Math.random()*3);//generate random number from 0 to 3
		//add
		if(opcode==ADD) {
			oprandx=(int)(Math.random()*101);
			oprandy=(int)(Math.random()*101);
			answer= Integer.toString(oprandx+oprandy);
			content=oprandx + "+" + oprandy + "=";
		}
		//divide
		if(opcode==DIV) {
			oprandx=(int)(Math.random()*11);
			oprandy=(int)(Math.random()*10)+1;
			//chu de jin de hua
			if(oprandx%oprandy==0) {
				answer = Integer.toString(oprandx/oprandy);				
			}
			else {
				answer =Integer.toString(oprandx/oprandy)+"..."+Integer.toString(oprandx%oprandy);
			}
			content=oprandx + "/" + oprandy + "=";			
		}
		//sub
		if(opcode==SUB) {
			oprandx=(int)(Math.random()*101);
			oprandy=(int)(Math.random()*101);
			answer= Integer.toString(Math.max(oprandx, oprandy)- Math.min(oprandx, oprandy));
			content=oprandx + "-" + oprandy + "=";
		}
		//multiple
		if(opcode==MUL) {
			oprandx=(int)(Math.random()*11);
			oprandy=(int)(Math.random()*11);
			answer= Integer.toString(oprandx*oprandy);
			content=oprandx + "*" + oprandy + "=";
		}
	}
	
	/*
	 * 	评估 随机生成的题目的等级 和 分数
	 * */
	private int JudgeLevel() {
		//content -> level
		return 1;
	}
	private int JudgeScore() {
		//level -> score
		return 1;
	}
}