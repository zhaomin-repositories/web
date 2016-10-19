<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>注册</title>
	</head>
	<body>
		<c:if test="${param.error != null}">
			<p>账号或密码错误</p>
		</c:if>
		<form action='${pageContext.request.contextPath}/doLogin' method='POST'>
			User:<input type='text' name='username' /><br /> 
			Password:<input type='password' name='password' /><br /> 
			<input name="submit" type="submit" value="Login" />
		</form>
	</body>
</html>