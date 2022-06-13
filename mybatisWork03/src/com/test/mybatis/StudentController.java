/*==============================
	StudentController.java
==============================*/

package com.test.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 컨트롤러 등록
@Controller
public class StudentController
{
	// SqlSession 을 활용하여 마이바티스 객체 의존성 (자동) 주입
	@Autowired
	private SqlSession sqlSession;
	
	// 매개변수를 등록하는 과정에서 매개변수 목록에 적혀있는
	// 클래스의 객체 정보는 스프링이 제공한다.
	
	// 사용자의 요청 주소와 메소드를 매핑하는 과정 필요
	// 『@RequestMapping(value = "요청주소", method = 전송방식)』
	// 이 때, 전송 방식은 submit 액션인 경우만 POST 로 설정하고
	// 나머지 모든 전송 방식은 GET 으로 처리한다.
	@RequestMapping(value = "/studentlist.action", method = RequestMethod.GET)
	public String studentList(Model model)
	{
		String result = null;
		
		IStudentDAO dao = sqlSession.getMapper(IStudentDAO.class);
		
		model.addAttribute("count", dao.count());
		model.addAttribute("list", dao.list());
		
		result = "/WEB-INF/view/StudentList.jsp";
		
		return result;
	}
	
	@RequestMapping(value = "/studentinsertform.action", method = RequestMethod.GET)
	public String studentInsertForm()
	{
		String result = null;
		
		result = "/WEB-INF/view/StudentInserForm.jsp";
		
		return result;
	}
	
	// dto랑 jsp name 속성이랑 같으면 바로 dto 받을 수 있음
	// 폼으로 넘어오는 건 메소드 post 써라
	@RequestMapping(value = "/studentinsert.action", method = RequestMethod.POST)
	public String studentInsert(StudentDTO student)
	{
		String result = null;
		
		IStudentDAO dao = sqlSession.getMapper(IStudentDAO.class);
		
		dao.add(student);
		
		result = "redirect:studentlist.action";
		
		return result;
	}
}
