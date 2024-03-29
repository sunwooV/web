package sec02.ex01;

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
	
	public List listMembers(MemberVO memberVO) {
		List list = new ArrayList();
		String _name = memberVO.getName();
		try {
			//connDB(); //네 가지 정보로 데이터베이스를 연결한다.
			con = dataFactory.getConnection();
			String query = "select * from t_member";
			if((_name!=null && _name.length() != 0)) {
				query += " where name=?";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, _name);
			} else {
				pstmt = con.prepareStatement(query);
			}
			System.out.println("prepareStatement: " + query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);

				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; //조회한 레코드의 개수만큼 MemberVO 객체를 저장한 ArrayList 를 반환합니다.
	}
	
	public void addMember(MemberVO vo) {
		try {
			Connection con = dataFactory.getConnection();
			String id = vo.getId(); //테이블에 저장할 회원 정보를 받아 옵니다.
			String pwd = vo.getPwd();
			
			String query = "insert into t_member";
			query += " (id,pwd)";
			query += " values(?,?)";
			System.out.println("prepareStatement: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id); //insert 문의 각 '?'에 순서대로 회원 정보를 세팅합니다.
			pstmt.setString(2, pwd);

			pstmt.executeUpdate(); //회원 정보를 테이블에 추가합니다.
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
				   query += " where id=? and pwd=?"; //ID와 비밀번호가 테이블에 존재하면 true, 존재하지 않으면 false
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); //커서를 첫 번째 레코드로 위치시킴.
			result = Boolean.parseBoolean(rs.getString("result"));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
