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
		<p>
			<form action='${pageContext.request.contextPath}/doRegister' method='POST'>
				账号:<input type='text' name='username' /><br /> 
				密码:<input type='password' name='password' /><br />
				<input name="submit" type="submit" value="注册" />
			</form>
		</p>
	</body>
</html>