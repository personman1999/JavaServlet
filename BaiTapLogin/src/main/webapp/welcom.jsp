<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h1>Xin chào, <%= request.getAttribute("username") %>!</h1>
    <p>Mật khẩu của bạn là: <%= request.getAttribute("password") %></p>
</body>
</html>