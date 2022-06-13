<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	/* Test_ok.jsp */
	
	//String root = request.getRealPath("/");		//-- 예전 방식
	String root = pageContext.getServletContext().getRealPath("/");
	//String savePath = root + File.separator + "pds" + "\\" + "savaFile";
	String savePath = root + "pds" + "\\" + "savaFile";	// 저장되는 파일의 경로 저장, 임의의 폴더를 만들어서 업로드된 파일을 저장하는 경로로 사용
	File dir = new File(savePath);
	
	// 테스트
	System.out.println(savePath);
	//--==>> C:\SpringMVCStudy\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\fileSystemApp06\pds\savaFile

	if(!dir.exists())
		dir.mkdirs();
	
	String encType = "UTF-8";		//-- 인코딩 방식
	int maxFileSize = 5*1024*1024;	//-- 최대 업로드 크기(5MB)
	
	try
	{
		MultipartRequest multi = null;
		multi = new MultipartRequest(request, savePath, maxFileSize
				                    , encType, new DefaultFileRenamePolicy());
		
		// savaPath : 얻어내는 파일의 경로
		// maxFileSize : 파일 업로드의 최대 용량
		// encType : 인코딩 방식
		// DefaultFileRenamePolicy : 같은 이름으로 업로드 했을 때 숫자만 추가된 형태로 저장시킴(디폴트)
		
		out.println("작성자 : " + multi.getParameter("userName") + "<br>");
		out.println("제목 : " + multi.getParameter("subject") + "<br>");
		out.println("서버에 저장된 파일명 : " + multi.getFilesystemName("uploadFile") + "<br>");
		out.println("업로드한 파일명 : " + multi.getOriginalFileName("uploadFile") + "<br>");
		out.println("파일 타입 : " + multi.getContentType("uploadFile") + "<br>");
		
		File file = multi.getFile("uploadFile");
		if (file != null)
		{
			out.println("파일 크기 : " + file.length() + "<br>");
			
		}
	}
	catch(Exception e)
	{
		System.out.println(e.toString());	// 비워놓지 말기~!!!
	}
	
%>