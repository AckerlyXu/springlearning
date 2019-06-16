<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    
</body>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
>
 
</script>
<script type="text/javascript">
$.ajax({
	  url:"/pet/postJson",
	  method :"POST",
	  contentType:"application/json",
	  dataType:"json",
	  data:JSON.stringify({name:"peiqi",age:5,petId:5}),
	  error:function(e){
		  console.log(e);
	  },
	  success:function(e){
		  alert(JSON.stringify(e));
	  }
})
</script>
</html>