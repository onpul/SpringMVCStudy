/*==================================================================
	#22. EmployeeInsertController.java
	- 사용자 정의 컨트롤러 클래스
	- 직원 데이터 입력 액션 수행 → DAO 필요
	- 이후 employeelist.action 을 다시 요청할 수 있도록 안내
	- DAO 객체에 대한 의존성 주입(DI)을 위한 ㅈ준비
	  → 인터페이스 형태의 자료형을 속성으로 구성
	  → setter 메소드 구성
==================================================================*/
package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// ※ Spring 의 『Controller』 인터페이스를 구현하는 방법을 통해
//    사용자 정의 컨트롤러 클래스를 구성한다.

public class EmployeeInsertController implements Controller
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
		
		// 데이터 수신 → EmployeeInsertForm.jsp 로 부터...
		String name = request.getParameter("name");
		String ssn1 = request.getParameter("ssn1");
		String ssn2 = request.getParameter("ssn2");
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
			//System.out.println(extraPay);
			employee.setExtraPay(Integer.parseInt(extraPay));
			
			dao.employeeAdd(employee);
			// check~!!!
			//mav.setViewName("EmployeeList.jsp");
			//컨트롤러가 갱신했어 클라이언트 너 다시 요청해 ~ 
			mav.setViewName("redirect:employeelist.action");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return mav;
	}
}
