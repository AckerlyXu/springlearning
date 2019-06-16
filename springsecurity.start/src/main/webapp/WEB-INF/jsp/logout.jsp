<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
     <form  method="post">
      <input name="${_csrf.parameterName }" value="${_csrf.token }">
        <input type="submit" value="提交"> 
       
     </form>
</body>
</html>