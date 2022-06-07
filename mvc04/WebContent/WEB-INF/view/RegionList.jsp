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
<title>RegionList.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<div>

<!-- 콘텐츠 영역 -->
	<div id="content">
		<h1>[ 지역 관리 (관리자 전용) ]</h1>
		<hr />
		
		<div>
			<form>
				<input type="button" value="지역 추가" class="btn"
				onclick="location.href='regioninsertform.action'"/>
			</form>
		</div>
		<br /><br />
		 <table id="regions" class="table">
		 <!-- REGIONID, REGIONNAME, DELCHECK -->
		 	<tr>
		 		<th>번호</th>
		 		<th>지역명</th>
		 		<!-- <th>인원수</th> -->
		 	</tr>
		
		 	<c:forEach var="region" items="${regionList }">
		 	<tr>
		 		<td>${region.regionId }</td>
		 		<td>${region.regionName }</td>
		 		<%-- <td>${region.delCheck}</td> --%>
	
		 	</tr>
		 	</c:forEach>
		 	
		 </table>
	</div>
	
	<!-- 회사 소개 및 어플리케이션 소개 영역 -->
	<div id="footer">
	</div>

</div>

</body>
</html>