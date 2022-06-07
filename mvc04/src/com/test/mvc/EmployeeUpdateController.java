/*=================================================================================================
	#27. EmployeeUpdateController.java
	- 사용자 정의 컨트롤러 클래스
	- 직원 데이터 수정 액션 수행 → employeelist.action 을 다시 요청할 수 있도록 안내 (리다이렉트)
	- DAO 객체에 대한 의존성 주입(DI)을 위한 준비
	  → 인터페이스 형태의 자료형을 속성으로 구성
	  → setter 메소드 준비
=================================================================================================*/
package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// ※ Spring 의 『Controller』 인터페이스를 구현하는 방법을 통해
//    사용자 정의 컨트롤러 클래스를 구성한다.

public class EmployeeUpdateController implements Controller
{
	private IEmployeeDAO dao;
	
	public void setDao(IEmployeeDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 컨트롤러가 수행하는 액션 코드
		ModelAndView mav = new ModelAndView();
		
		// 로그인 여부만 확인 → 관리자인지 확인할 필요 없음.
		// 세션 처리과정 추가 -----------------------------------------------------------------------
		HttpSession session = request.getSession();
		
		if (session.getAttribute("name")==null)	//-- 로그인이 되어 있지 않은 상황
		{
			mav.setViewName("redirect:loginform.action");
			return mav;
		}
		//-----------------------------------------------------------------------  세션 처리과정 추가 
		
		// 데이터 수신 (EmployeeUpdateForm.jsp(수정폼) 로부터 넘겨 받은 데이터)
		String employeeId = request.getParameter("employeeId");
		String name = request.getParameter("name");
		String ssn1 = request.getParameter("ssn1");
		String ssn2 = request.getParameter("ssn1");
		String birthday = request.getParameter("birthday");
		String lunar = request.getParameter("lunar");
		String telephone = request.getParameter("telephone");
		String regionId = request.getParameter("regionId");
		String departmentId = request.getParameter("departmentId");
		String positionId = request.getParameter("positionId");
		String basicPay = request.getParameter("basicPay");
		String extraPay = request.getParameter("extraPay");
		
		try
		{
			Employee employee = new Employee();
			
			employee.setEmployeeId(employeeId);
			employee.setName(name);
			employee.setSsn1(ssn1);
			employee.setSsn2(ssn2);
			employee.setBirthday(birthday);
			employee.setLunar(Integer.parseInt(lunar));
			employee.setTelephone(telephone);
			employee.setRegionId(regionId);
			employee.setDepartmentId(departmentId);
			employee.setPositionId(positionId);
			employee.setBasicPay(Integer.parseInt(basicPay));
			employee.setExtraPay(Integer.parseInt(extraPay));
			
			dao.modify(employee);
			
			//mav.setViewName("/WEB-INF/view/EmployeeList.jsp");
			mav.setViewName("redirect:employeelist.action");
			//               --------- 안 붙여도 상관 없음
			//               리다이렉트로 전달하라는 개념을 전달.
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
