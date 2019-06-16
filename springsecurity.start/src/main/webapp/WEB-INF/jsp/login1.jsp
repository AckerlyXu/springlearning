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
 <h1>index</h1>
      <c:url value="/login" var="url"></c:url>
      <form method="post" >
      ${param.error}
      <c:if test="${param.error!=null }">
       <p>登陆失败，请重新登陆</p>
      </c:if>
       <c:if test="${param.logout!=null }">
       <p>您已经成功退出登录</p>
      </c:if>
      用户名:<input name="username" ><br />
      密码:<input name="username" ><br />
      <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
      <input type="submit" value="登陆">
      </form>
</body>
</html>