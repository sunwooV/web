<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.a{
	border:1px solid #D8D8D8;
	width:100%;
	padding: 10px 0px;
}

h1{
	text-align:center;
	color:#444444;
}
p{
	text-align:center;
}
</style>
<body>
<form action="addanswer.do" method="post">
<div class="a">
<h1>비밀번호 찾기</h1>
<p>
	아이디:<br/><input type="text" name="id" value="${param.id}">
	<c:if test="${errors.id}">ID를 입력하세요.</c:if>
</p>
<p>
	가장-좋아하는 동물은?<br/><input type="text" name="answer" value="${param.answer}">
	<c:if test="${errors.answer}">가장 좋아하는 동물을 입력하세여.</c:if>
</p>
<c:if test="${errors.check}">일치하는 정보가 없습니다.</c:if>
<p>
<input type="submit" value="찾기">
</p>
</div>
</body>
</html>