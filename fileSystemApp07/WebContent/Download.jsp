<%@page import="java.io.File"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	/* Download.jsp */
	
	String root = "/";
	root = pageContext.getServletContext().getRealPath(root);
	String savePath = root + "pds" + File.separator + "saveFile";
	
	String saveFileName = request.getParameter("saveFile");
	String originalFileName = request.getParameter("originalFileName");
	
	// 파일 다운로드
	out.clear();
	boolean flag = FileM
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Download.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

</body>
</html>