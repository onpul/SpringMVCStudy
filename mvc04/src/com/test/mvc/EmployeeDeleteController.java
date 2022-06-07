/*=================================================================================================================================
	#28. EmployeeDeleteController.java
	- 사용자 정의 컨트롤러 클래스.
	- 직원 데이터 삭제 액션 처리 → 처리 후 emplyeelist.action 을 다시 요청할 수 있도록 안내
	- DAO 객체에 대한 의존성 주입(DI)을 위한 준비
	  → 인터페이스 형태의 자료형을 속성으로 구성
	  → setter 메소드 구성
=================================================================================================================================*/
package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// ※ Spring 의 『Controller』 인터페이스를 구현하는 방법을 통해
//    사용자 정의 컨트롤러 클래스를 구성한다.

public class EmployeeDeleteController implements Controller
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
		
		// 데이터 수신(→ EmployeeList.jsp 로 부터 employeeId 수신)
		String employeeId = request.getParameter("employeeId");
		
		try
		{
			dao.remove(employeeId);
			
			mav.setViewName("redirect:employeelist.action");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
