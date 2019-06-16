<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"  
    %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
<a href="${s:mvcUrl('PC#buildPath').arg(1,'123').buildAndExpand() }">hello</a>
        <form method="post" action="/pet/${pet.petId }">
              <input  name="name" value="${pet.name }" /><br/>
              <input  name="age" value="${pet.age }" /><br/>
              <input  name="ownerId" value="${pet.ownerId }" /><br/>
              <input type="submit" value="submit">
        </form>
</body>
</html>