<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	/* Write_ok.jsp */
	
	// ① 주요 속성값을 준비
	//    multipartrequest에 넘길 속성값 준비
	String root = "/";
	root = pageContext.getServletContext().getRealPath(root);
	
	String savePath = root + "pds" + File.separator + "saveFile";
	//                                      "\\"
	
	String encType = "UTF-8";
	int maxFileSize = 5*1024*1024;
	
	// ② 경로상 디렉터리가 존재하지 않으면... 만든다.
	File dir = new File(savePath);
	if (!dir.exists())
		dir.mkdirs();
	
	// ③ MultipartRequest 구성
	MultipartRequest req = null;
	String urlFile = "";
	
	try
	{
		// request, 파일저장경로, 파일최대크기, 인코딩방식, 중복파일명처리정책
		req = new MultipartRequest(request, savePath, maxFileSize
				                  , encType, new DefaultFileRenamePolicy());
		
		// ④ 구성된 MultipartRequest 로 부터 필요한 값 얻어내기 
		out.println("작성자 : " + req.getParameter("userName") + "<br>");
		out.println("제목 : " + req.getParameter("subject") + "<br>");
		out.println("서버에 저장된 파일명 : " + req.getFilesystemName("uploadFile") + "<br>");
		out.println("업로드한 실제 파일명 : " + req.getOriginalFileName("uploadFile") + "<br>");
		out.println("파일 타입 : " + req.getContentType("uploadFile") + "<br>");
		
		File f = req.getFile("uploadFile");
		if (f != null)
		{
			out.println("파일 크기 : " + f.length() + "Bytes. <br>");
		}
		
		// ⑤ 다운로드 기능을 수행하기 위한 속성을 get 방식으로 처리
		urlFile = "Download.jsp?saveFileName=" + req.getFilesystemName("uploadFile");
		urlFile += "&originalFileName=" + req.getOriginalFileName("uploadFile");
	}
	catch(Exception e)
	{
		System.out.println(e.toString());
	}
	
%>
<br><a href="<%=urlFile%>">파일 다운로드</a>