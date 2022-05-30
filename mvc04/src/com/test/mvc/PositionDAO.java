/*====================================================
   #11. PositionDAO.java
   - 데이터베이스 액션 처리 클래스.
   - 직위 데이터 입력 / 출력 / 수정 / 삭제 액션.
   - Connection 객체에 대한 의존성 주입을 위한 준비
     → 인터페이스 형태의 속성 구성(DataSource)
     → setter 메소드 정의.
====================================================*/
package com.test.mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class PositionDAO implements IPositionDAO
{
	// 인터페이스 자료형을 속성으로 구성
		private DataSource datasource;

	// setter 구성
	public void setDatasource(DataSource datasource)
	{
		this.datasource = datasource;
	}

	@Override
	public ArrayList<Position> list() throws SQLException
	{
		ArrayList<Position> result = new ArrayList<Position>();
		
		Connection conn = datasource.getConnection();
		
		String sql = "SELECT POSITIONID, POSITIONNAME, MINBASICPAY, DELCHECK FROM POSITIONVIEW ORDER BY POSITIONID";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Position position = new Position();
			
			position.setPositionId(rs.getString("POSITIONID"));
			position.setPositionName(rs.getString("POSITIONNAME"));
			position.setMinBasicPay(rs.getInt("MINBASICPAY"));
			position.setDelCheck(rs.getInt("DELCHECK"));
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
		
	}

	@Override
	public int add(Position position) throws SQLException
	{
		int result = 0;
		
		Connection conn = datasource.getConnection();
		
		String sql = "INSERT INTO POSITION(POSITIONID, POSITIONNAME, MINBASICPAY) VALUES(POSITIONSEQ.NEXTVAL, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, position.getPositionName());
		pstmt.setInt(2, position.getMinBasicPay());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int remove(Position position) throws SQLException
	{
		int result = 0;
		
		Connection conn = datasource.getConnection();
		
		String sql = "DELETE FROM POSITION WHERE POSITIONID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(position.getPositionId()));
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int modify(Position position) throws SQLException
	{
		int result = 0;
		
		Connection conn = datasource.getConnection();
		
		String sql = "UPDATE POSITION SET POSITIONNAME=?, MINBASICPAY=? WHERE POSITIONID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, position.getPositionName());
		
		pstmt.setInt(2, Integer.parseInt(position.getPositionId()));
		pstmt.setInt(3, position.getMinBasicPay());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	

}
