<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
		<form:form action="authenticate" method="post" modelAttribute="user">
			<tr>
				<td>Email:</td>
				<td><form:input path="email" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><form:password path="password" /></td>
			</tr>
			<tr>
				<td><input type="checkbox" value="Remember Me"  name="remember"/> Remember Me</td>
			</tr>
			<tr>
				<td><input type="submit" value="Login"></td>
			</tr>
		</form:form>
	</table>${msg}
</body>
</html>