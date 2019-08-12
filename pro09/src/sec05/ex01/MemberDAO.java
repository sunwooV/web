package sec05.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List listMembers() {
		List list = new ArrayList();
		try {
			//connDB(); //�� ���� ������ �����ͺ��̽��� �����Ѵ�.
			con = dataFactory.getConnection();
			String query = "select * from t_member";
			System.out.println("prepareStatement: " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);

				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; //��ȸ�� ���ڵ��� ������ŭ MemberVO ��ü�� ������ ArrayList �� ��ȯ�մϴ�.
	}
	
	public void addMember(MemberVO vo) {
		try {
			Connection con = dataFactory.getConnection();
			String id = vo.getId(); //���̺��� ������ ȸ�� ������ �޾� �ɴϴ�.
			String pwd = vo.getPwd();
			
			String query = "insert into t_member";
			query += " (id,pwd)";
			query += " values(?,?)";
			System.out.println("prepareStatement: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id); //insert ���� �� '?'�� ������� ȸ�� ������ �����մϴ�.
			pstmt.setString(2, pwd);

			pstmt.executeUpdate(); //ȸ�� ������ ���̺��� �߰��մϴ�.
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delMember(String id) {
		try {
			Connection con = dataFactory.getConnection();
			
			String query = "delete from t_member where id=?";
			System.out.println("prepareStatement: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isExisted(MemberVO membervo) {
		boolean result = false;
		String id = membervo.getId();
		String pwd = membervo.getPwd();
		
		try {
			con=dataFactory.getConnection();
			String query = "select decode(count(*), 1, 'true', 'false') as result from t_member";
				   query += " where id=? and pwd=?"; //ID�� ��й�ȣ�� ���̺��� �����ϸ� true, �������� ������ false
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); //Ŀ���� ù ��° ���ڵ�� ��ġ��Ŵ.
			result = Boolean.parseBoolean(rs.getString("result"));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}