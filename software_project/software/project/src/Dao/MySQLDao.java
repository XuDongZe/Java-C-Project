package Dao;

import java.awt.RadialGradientPaint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import data.Answer;
import data.Problem;
import data.User;


/*
 * 	涓�涓澶勭悊瀵硅薄鍙互澶勭悊涓�娆ql璇锋眰
 * */
public class MySQLDao {
	/*
	 * 姣忎竴涓猰ySql杩炴帴 浣跨敤涓�涓嫭绔嬬殑绠＄悊鍣�,
	 * 濡傛灉涓�涓▼搴忎腑浣跨敤涓�涓叡鍚岀殑static鐨凜onnection鏃讹紝杩欑闂灏卞緢瀹规槗鍑虹幇
	 * MySQL exception : No operations allowed after connection closed
	 * 绗竴娆′娇鐢╟onnection涓嶄細鍑虹幇闂锛屼互鍚庡啀娆″氨浼氬洜涓虹涓�娆′腑宸茬粡灏嗗叾鍏抽棴
	 * 瀵艰嚧杩炴帴涓嶅彲鐢�
	 * */
	private static ConnManager connMng = new ConnManager();	
	private Connection conn;
	private PreparedStatement stmt;	//澹版槑棰勫鐞嗗璞�
	private ResultSet rs;			//缁撴灉闆�
	
	public MySQLDao() {
		
	}
	
	/*	浣跨敤鐢ㄦ埛ID鏌ヨ
	 *	User 鍑哄彛鍙傛暟
	 *	res 杩斿洖鐘舵�佸弬鏁�
	 * */
	public boolean queryUser(String ID, User user) {
		String sql = "select * from tb_user" + " " + "where id = ?";
		System.out.println(sql);
		boolean res = false;
		try {
			conn = connMng.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ID);
			rs = stmt.executeQuery();
			if( rs.next() ) {
				user.setID( rs.getString("ID") );
				user.setPsw( rs.getString("psw") );
				user.setSumNums( rs.getInt("sumNums") );
				user.setCorrectNums( rs.getInt("correctNums") );
				user.setLevel( rs.getInt("level") );
				res = true;
			}
			System.out.println("get user from tb_user: " + user);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connMng.close(conn, stmt, rs);
		}
		return res;
	}
	
	//鎸夌収棰樼洰绛夌骇鏌ヨ涓�涓鐩苟杩斿洖瀵硅薄寮曠敤
	public boolean queryProblems(int pNums, int pLevs, ArrayList<Answer> answers) {
		/*
		 * 	杩欓噷涓嶅鍟�, 杩欓噷浼氳繑鍥炲緢澶歱roblem
		 * 	鑰屾垜浠粎浠呴渶瑕佷竴涓嵆鍙�
		 * 	涔熷彲浠ユ敼鎴愯繑鍥炰竴涓槦鍒�
		 * */
		String sql = "select * from tb_problem" + " " + "where level = ?";
		boolean res = false;
		int n = pNums;
		try {
			conn = connMng.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pLevs);
			rs = stmt.executeQuery();
			while( rs.next() && n>0 ) {/*浣跨敤if姣忔鍙煡璇竴涓�*/
				Problem p = new Problem();
				p.setSeq( rs.getInt("seq") );
				p.setContent( rs.getString("content") );
				p.setAnswer( rs.getString("answer") );
				p.setScore( rs.getInt("score") );
				p.setLevel( rs.getInt("level") );
				answers.add(new Answer(p, null));
				n--;
			}
			res = rs.next();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connMng.close(conn, stmt, rs);
		}
		return res;
	}
	
	/*
	 * 	娉ㄥ唽涓�涓敤鎴�
	 * */
	public boolean regUser(String ID, String psw) {
		String sql = "insert into tb_user " + 
					"values " +
					"(?, ?, ?, ?, ?)";
		boolean res = true;
		try {
			conn = connMng.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ID);
			stmt.setString(2, psw);
			stmt.setInt(3, 0);	//sumNums
			stmt.setInt(4, 0);	//correctNums
			stmt.setInt(5, 0);	//level
			stmt.executeUpdate();
		}catch (Exception e) {
			res = false;
			e.printStackTrace();
		}finally {
			connMng.close(conn, stmt, rs);
		}
		return res;
	}
	
//new added below
	public boolean regProblem(Problem p) {
		String sql = "insert into tb_problem " + 
					"values " +
					"(?, ?, ?, ?, ?)";
		boolean res = true;
		try {
			conn = connMng.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, p.getSeq());
			stmt.setString(2, p.getContent());
			stmt.setString(3, p.getAnswer());
			stmt.setInt(4, p.getScore());
			stmt.setInt(5, p.getLevel());
			stmt.executeUpdate();
		}catch (Exception e) {
			res = false;
			e.printStackTrace();
		}finally {
			connMng.close(conn, stmt, rs);
		}
		return res;
	}
	
	/*
	 * 	查询数据库中最后一个题目的编号
	 * 	所以在生成题目的时候回有序的向数据库插入
	 * 	失败返回-1	
	 * 	成功返回记录在数据库中的最后一题的序列号, 最小为0
	 * */
	public int queryLastSeq() {
		int res = -1;
		String sql = "select * from tb_problem order by seq desc limit 1";
		try {
			conn = connMng.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if( rs.next() ) {
				res = rs.getInt("seq");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connMng.close(conn, stmt, rs);
		}
		return res;
	}
	/*
	 * 	失败返回-1, 成功返回数据库中第一道题目的序列号(按序列号自然序排列)
	 * */
	public int queryFirstSeq() {
		//现在默认为0
		return 0;
	}
	
	//输入参数是本次测试的更新值, 更新结果是数据库总值
	//数据库中sumNums: 该用户所做过的所有的题目数量		correctNums:所做过的所有正确题目的数量
	public boolean updUser(String ID, int sumNums, int correctNums) {
		User user = new User();
		if ( queryUser(ID, user) == false )	return false;
		String sql = "update tb_user " + 
					 "set sumNums = ?, correctNums = ? " +
					 "where id = ?";
		boolean res = true;
		try {
			conn = connMng.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, sumNums + user.getSumNums());
			stmt.setInt(2, correctNums + user.getCorrectNums());
			stmt.setString(3, ID);
			stmt.executeUpdate();
		}catch (Exception e) {
			res = false;
			e.printStackTrace();
		}finally {
			connMng.close(conn, stmt, rs);
		}
		return res;
	}
}


