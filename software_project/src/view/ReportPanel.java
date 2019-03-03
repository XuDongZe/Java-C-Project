package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Net.ClientNet;
import data.Answer;
import data.CCommonData;
import data.MsgEntry;
import data.Problem;

/*
 * 	由AnswerPanel -> ReportPanel面板接口
 * 	错题队列, 每一个元素是一个错题(用户未做或者做错的题目)
 * 	题目id + 题目内容 + 正确答案  + 用户答案 
 * */

/*	组件命名规则
 * 	前缀：
 * 	err		错题具体展示
 * 	cnt		错题统计情况展示
 * 	fns		完成测试
 * */
public class ReportPanel extends JPanel{
	
	public static ClientWindow cWnd;
	public static ClientNet	cNet;
	public static CCommonData cData;
	
	private final int WIDTH = 700;
	private final int HEIGHT = 700;
	
	//组件
	private JLabel errLbl;
	private JLabel cntLbl;
	private JTextArea errArea;	//错误显示区域
	private JScrollPane errPanel;
	private JPanel ctrPanel;
	
	public ReportPanel() {
		super();
		MsgEntry cMsg = cData.getMsgEntry();
		errLbl = new JLabel("以下为您本次测试错误的题目:");
		cntLbl = new JLabel( "用户名: "+cMsg.getID() + "\t" +
						    "总题目数: "+cMsg.getProblemNums() + "\t" +
							"正确题数: "+cMsg.getCorrectNums() + "\t" +
							"正确率: "+cMsg.getCorrectNums()*1.0/cMsg.getProblemNums());
		
		errArea = new JTextArea( (int)(HEIGHT*0.75/this.getFont().getSize()), 
				(int)(WIDTH*0.75/this.getFont().getSize()) );
		errPanel = new JScrollPane(errArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		ctrPanel = new JPanel();
		ctrPanel.add(cntLbl);
		
		this.setLayout(new BorderLayout());
		this.add(errLbl, BorderLayout.NORTH);
		this.add(errPanel, BorderLayout.CENTER);
		this.add(ctrPanel, BorderLayout.SOUTH);
		
		this.setSize(WIDTH, HEIGHT);
		
		showErrArea();
	}
	
	private void showErrArea() {
		ArrayList<Answer> answers = cData.getMsgEntry().getAnsList();
		errArea.setText("\n\n您本次测试结果如下表: \n\n");
		errArea.append("\t\t题号\t\t题目内容\t\t用户答案\t\t正确答案\n\n");
		for(Answer ans: answers) {
			Problem p = ans.getProblem();
			errArea.append(p.getSeq()+"\t\t"+p.getContent()+
					"\t\t"+ans.getUserAns()+"\t\t"+p.getAnswer()
					+"\n\n");
		}
	}
	
}
