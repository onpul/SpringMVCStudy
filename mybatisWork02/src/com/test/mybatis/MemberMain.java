/*=================================================
	MemberMain.java
	- 컨트롤러
=================================================*/

package com.test.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemberMain
{	
	// 주요 속성 구성
	// mybatis 객체 의존성 (자동) 주입~!!!
	@Autowired
	private SqlSession sqlSession;
	// 실제 연결부터 수행하는 sqlSession이 필요
	// 쿼리문을 어떤 브라켓에 딸깍! 해서 쓰는 것
	
	@RequestMapping(value = "/memberlist.action" , method = RequestMethod.GET)
	public String memberList(ModelMap model)
	{
		//IMemberDAO dao = (IMemberDAO)new MemberDAO();
		IMemberDAO dao = sqlSession.getMapper(IMemberDAO.class);
		// sqlSession의 매퍼가 따라갈 인터페이스를 파라미터로 넘기기
		
		//dao.count();
		//dao.list();
		
		model.addAttribute("count", dao.count());
		model.addAttribute("list", dao.list());
		
		return "WEB-INF/view/MemberList.jsp";
	}
	
	@RequestMapping(value = "/memberinsert.action" , method = RequestMethod.POST)
	public String memberInsert(MemberDTO m)
	{
		IMemberDAO dao = sqlSession.getMapper(IMemberDAO.class);
		
		dao.add(m);
		
		return "redirect:memberlist.action";
	}
	
	@RequestMapping(value = "/memberdelete.action")
	public String memberDelete(String mid)
	{
		System.out.println("mid="+mid);
		
		IMemberDAO dao = sqlSession.getMapper(IMemberDAO.class);
		
		dao.delete(mid);
		
		return "redirect:memberlist.action";
	}
}
