/* =================================
	#23.AjaxController.java
	- 사용자 정의 컨트롤러 클래스
================================= */
package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// ※ Spring 의 『Controller』 인터페이스를 구현하는 방법을 통해
//    사용자 정의 컨트롤러 클래스를 구성한다.

public class AjaxController implements Controller
{
	
	// 인터페이스 형태의 속성 구성
	private IEmployeeDAO dao;
	
	// setter 구성
	public void setDao(IEmployeeDAO dao)
	{
		this.dao = dao;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 컨트롤러가 수행하는 액션 코드
		
		ModelAndView mav = new ModelAndView();
		
		// 데이터 수신 (→ EmployeeInsertForm.jsp 로 부터... positionId 수신)
		String positionId = request.getParameter("positionId");
		
		int result = 0;
		
		try
		{
			result = dao.getMinBasicPay(positionId);
			
			mav.addObject("result", result);
			
			mav.setViewName("/WEB-INF/view/Ajax.jsp");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return mav;
	}


	
}
