<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title></title>
</head>
<body>
<h1>出错啦！</h1>
	<!-- 处理HTTP错误代码 -->
	<c:set value="${requestScope['javax.servlet.error.status_code']}" var="status"></c:set>
	<c:if test="${status != null }">
		<c:set value="${requestScope['javax.servlet.error.message']}" var="statusmessage"></c:set>
	 <h2>错误代码：${status }</h2>
	 <!-- 统一处理错误  -->
	 <c:choose>
	 	<c:when test="${status == 404 }">
	 	<c:set value="找不到页面，${statusmessage }" var="statusmessage"></c:set>
	 	</c:when>
	 </c:choose>
	 <h2>错误信息：${statusmessage }</h2>
	</c:if>
	<!-- 统一处理异常  -->
    <c:if test="${exception != null }">
			<h2>${exception }</h2>
	</c:if>
	<br />
<img alt="" src="resources/images/error.jpg"> <br />


<!-- errorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerror<br />
errorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerror<br />
errorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerror<br />
errorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerror<br /> -->
</body>
</html>