<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/static/js/jquery-3.2.1.js"></script>
</head>
<body>
hi, ${name}! this is home.jsp

<jsp:include page="common/include.jsp"></jsp:include>
</body>
<script type="text/javascript">
$(function(){
	alert("jquery was loaded");
})
</script>
</html>