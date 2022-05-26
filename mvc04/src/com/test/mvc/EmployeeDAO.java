/*====================================================
   #9. EmployeeDAO.java
   - 데이터베이스 액션 처리 클래스.
   - 직원 데이터 입력 / 출력 / 수정 / 삭제 액션.
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

public class EmployeeDAO implements IEmployeeDAO
{
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public ArrayList<Employee> list() throws SQLException
	{
		ArrayList<Employee> result = new ArrayList<Employee>();
		
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT EMPLOYEEID, NAME, SSN, BIRTHDAY"
				+ ", LUNAR, LUNARNAME, TELEPHONE, DEPARTMENTID"
				+ ", DEPARTMENTNAME, POSITIONID, POSITIONAME"
				+ ", REGIONID, REGIONNAME, BASICPAY, EXTRAPAY, PAY, GRADE"
				+ " FROM EMPLOYEEVIEW"
				+ " ORDER BY EMPLOYEEID";
		
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Employee emp = new Employee();
			emp.setEmployeeId(rs.getString("EMPLOYEEID"));
			emp.setName(rs.getString("NAME"));
			emp.setBirthday(rs.getString("BIRTHDAY"));
			emp.setLunar(rs.getInt("LUNAR"));
			emp.setLunarName(rs.getString("LUNARNAME"));
			emp.setTelephone(rs.getString("TELEPHONE"));
			emp.setDepartmentId(rs.getString("DEPARTMENTID"));
			emp.setDepartmentName(rs.getString("DEPARTMENTNAME"));
			emp.setPositionId(rs.getString("POSITIONID"));
			emp.setPositionName(rs.getString("POSITIONNAME"));
			emp.setRegionId(rs.getString("REGIONID"));
			emp.setRegionName(rs.getString("REGIONNAME"));
			emp.setBasicPay(rs.getInt("BASICPAY"));
			emp.setExtraPay(rs.getInt("EXTRAPAY"));
			emp.setPay(rs.getInt("PAY"));
			emp.setGrade(rs.getInt("GRADE"));
			
			result.add(emp);
			
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public ArrayList<Region> regionList() throws SQLException
	{
		ArrayList<Region> result = new ArrayList<Region>();
		
		String sql = "SELECT REGIONID, REGIONNAME"
				+ ", DELCHECK"
				+ " FROM REGIONVIEW"
				+ " ORDER BY REGIONID";
		
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Region reg = new Region();
			
			reg.setRegionId(rs.getString("REGIONID"));
			reg.setRegionName(rs.getString("REGIONNAME"));
			reg.setDelCheck(rs.getInt("DELCHECK"));
			
			result.add(reg);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public ArrayList<Department> departmentList() throws SQLException
	{
		ArrayList<Department> result = new ArrayList<Department>();
		
		String sql = "SELECT DEPARTMENTID, DEPARTMENTNAME"
				+ ", DELCHECK"
				+ " FROM DEPARTMENTVIEW"
				+ " ORDER BY DEPARTMENTID";
		
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Department dep = new Department();
			
			dep.setDepartmentId(rs.getString("DEPARTMENTID"));
			dep.setDepartmentName(rs.getString("DEPARTMENTNAME"));
			dep.setDelCheck(rs.getInt("DELCHECK"));
			
			result.add(dep);
		}
		
		rs.close();
		pstmt.close();
		conn.close();		
		
		return result;
	}

	@Override
	public ArrayList<Position> positionList() throws SQLException
	{
		ArrayList<Position> result = new ArrayList<Position>();
		
		String sql = "SELECT POSITIONID, POSITIONNAME"
				+ ", MINBASICPAY, DELCHECK"
				+ " FROM POSITIONVIEW"
				+ " ORDER BY POSITIONID";
		
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Position pos = new Position();
			
			pos.setPositionId(rs.getString("POSITIONID"));
			pos.setPositionName(rs.getString("POSITIONNAME"));
			pos.setMinBasicPay(rs.getInt("MINBASICPAY"));
			pos.setDelCheck(rs.getInt("DELCHECK"));
			
			result.add(pos);
		}
		
		rs.close();
		pstmt.close();
		conn.close();		
		
		return result;
	}

	@Override
	public int getMinBasicPay(String positionId) throws SQLException
	{
		int result = 0;
		
		String sql = "SELECT MINBASICPAY"
				+ " FROM POSITION"
				+ " WHERE POSITIONID=?";
		
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(positionId));
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
			result = rs.getInt("MINBASICPAY");
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int employeeAdd(Employee emp) throws SQLException
	{
		int result = 0;
		
		String sql = "INSERT INTO EMPLOYEE("
				+ " EMPLOYEEID, NAME, SSN1, SSN2"
				+ ", BIRTHDAY, LUNAR, TELEPHONE, DEPARTMENTID"
				+ ", POSITIONID, REGIONID, BASICPAY, EXTRAPAY )"
				+ " VALUES( EMPLOYEESEQ.NEXTVAL, ?"
				+ ", ?, CRYPTPACK.ENCRYPT(?, ?)"
				+ ", TO_DATE(?, 'YYYY-MM-DD'), ?"
				+ ", ?, ?, ?, ?, ?, ?)";
		
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, emp.getName());
		pstmt.setString(2, emp.getSsn1());
		pstmt.setString(3, emp.getSsn2());
		pstmt.setString(4, emp.getSsn2());
		pstmt.setString(5, emp.getBirthday());
		pstmt.setInt(6, emp.getLunar());
		pstmt.setString(7, emp.getTelephone());
		pstmt.setInt(8, Integer.parseInt(emp.getDepartmentId()));
		pstmt.setInt(9, Integer.parseInt(emp.getPositionId()));
		pstmt.setInt(10, Integer.parseInt(emp.getRegionId()));
		pstmt.setInt(11, emp.getBasicPay());
		pstmt.setInt(12, emp.getExtraPay());		
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 직원 데이터 삭제
	@Override
	public int remove(String employeeId) throws SQLException
	{
		int result = 0;
		
		String sql = "DELETE"
				+ " FROM EMPLOYEE"
				+ " WHERE EMPLOYEEID=?";
		
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(employeeId));	
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 직원 데이터 수정
	@Override
	public int modify(Employee emp) throws SQLException
	{
		int result = 0;
		
		String sql = "UPDATE EMPLOYEE"
				+ " SET NAME=?, BIRTHDAY=TO_DATE(?, 'YYYY-MM-DD')"
				+ ", LUNAR=?, TELEPHONE=?"
				+ ", DEPARTMENTID=?, POSITIONID=?"
				+ ", REGIONID=?, BASICPAY=?, EXTRAPAY=?"
				+ ", SSN1=?, SSN2=CRYPTPACK.ENCRYPT(?, ?)"
				+ " WHERE EMPLOYEEID=?";
		
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, emp.getName());
		pstmt.setString(2, emp.getBirthday());
		pstmt.setInt(3, emp.getLunar());
		pstmt.setString(4, emp.getTelephone());
		pstmt.setInt(5, Integer.parseInt(emp.getDepartmentId()));
		pstmt.setInt(6, Integer.parseInt(emp.getPositionId()));
		pstmt.setInt(7, Integer.parseInt(emp.getRegionId()));
		pstmt.setInt(8, emp.getBasicPay());
		pstmt.setInt(9, emp.getExtraPay());
		pstmt.setString(10, emp.getSsn1());
		pstmt.setString(11, emp.getSsn2());
		pstmt.setString(12, emp.getSsn2());
		pstmt.setInt(14, Integer.parseInt(emp.getEmployeeId()));

		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 아이디로 직원 검색
	@Override
	public Employee searchId(String employeeId) throws SQLException
	{
		Employee result = new Employee();
		
		String sql = "SELECT EMPLOYEEID, NAME, SSN1"
				+ ", TO_CHAR(BIRTHDAY, 'YYYY-MM-DD') AS BIRTHDAY"
				+ ", LUNAR, TELEPHONE, DEPARTMENTID, POSITIONID"
				+ ", REGIONID, BASICPAY, EXTRAPAY"
				+ " FROM EMPLOYEE"
				+ " WHERE EMPLOYEEID=?";
		
		
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(employeeId));
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result.setEmployeeId(rs.getString("EMPLOYEEID"));
			result.setName(rs.getString("NAME"));
			result.setBirthday(rs.getString("BIRTHDAY"));
			result.setLunar(rs.getInt("LUNAR"));
			result.setLunarName(rs.getString("LUNARNAME"));
			result.setTelephone(rs.getString("TELEPHONE"));
			result.setDepartmentId(rs.getString("DEPARTMENTID"));
			result.setPositionId(rs.getString("POSITIONID"));
			result.setRegionId(rs.getString("REGIONID"));
			result.setBasicPay(rs.getInt("BASICPAY"));
			result.setExtraPay(rs.getInt("EXTRAPAY"));
			result.setSsn1(rs.getString("SSN1"));
		}
		
		rs.close();
		pstmt.close();
		conn.close();		
		
		return result;
	}
	
	// 일반 직원 로그인
	@Override
	public String login(String id, String pw) throws SQLException
	{
		String result = "";
		
		String sql = "SELECT NAME"
				+ " FROM EMPLOYEE"
				+ " WHERE EMPLOYEEID=?"
				+ " AND SSN2 = CRYPTPACK.ENCRYPT(?, ?)";
				
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(id));
		pstmt.setString(2, pw);
		pstmt.setString(3, pw);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result= rs.getString("NAME");
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 관리자 로그인
	@Override
	public String loginAdmin(String id, String pw) throws SQLException
	{
		String result = "";
		
		String sql = "SELECT NAME"
				+ " FROM EMPLOYEE"
				+ " WHERE EMPLOYEEID=?"
				+ " AND SSN2=CRYPTPACK.ENCRYPT(?, ?)"
				+ " AND GRADE=0";
				
		Connection conn = dataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(id));
		pstmt.setString(2, pw);
		pstmt.setString(3, pw);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result= rs.getString("NAME");
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return result;
	}	
}
