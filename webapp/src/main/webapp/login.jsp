<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>登录</title>
    </head>
 
    <body>
    	<c:url var="loginUrl" value="/login" />
		<form action="${loginUrl}" method="post">
			<input type="text" name="username" /><br/>
			<input type="password" name="password" /><br/>
			<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
			<input type="submit" value="登录" />
			<input type="reset" value="重置" />
		</form>
	</body>
</html>