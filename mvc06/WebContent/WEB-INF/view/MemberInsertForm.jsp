<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MemberInsertForm.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function()
{
	$("#submitBtn").click(function()
	{
		//alert("확인");
		
		if ($("#id").val()=="" || $("#pw").val()=="" || $("#name").val()=="" || $("#tel").val()=="" || $("#email").val()=="")
		{
			alert("모든 항목을 입력해 주세요");
			return false;
		}
		
		$("#memberInsertForm").submit();
	});
});
</script>
</head>
<body>
<div>
	<h1>회원 정보 입력</h1>
	<form action="memberinsert.action" method="post" id="memberInsertForm">
		아이디* <input type="text" id="id" name="id"/><br />
		비밀번호* <input type="password" id="pw" name="pw"/><br />
		이름* <input type="text" id="name" name="name"/><br />
		전화번호* <input type="text" id="tel" name="tel"/><br />
		이메일* <input type="text" id="email" name="email"/><br />
		<input type="button" value="회원 등록" id="submitBtn"/>
		<input type="button" value="회원 목록" onclick="location.href='memberlist.action'"/>
	</form>
</div>
</body>
</html>