<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="header.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<style>.error{color:red;}</style>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/layout-default-latest.css">
		<title>login</title>
		<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../static/js/loginPage.js"></script>
		<script type="text/javascript">
			$(function(){
				onLoad();
			});
		</script>
	</head>
	<body>
		<form>
			用户名：<input id="username" name="username" type="text"/><br/>
			密码：<input id="pwd" name="pwd" type="password" /><br/>
			<input id="redirectUrl" name="redirectUrl" type="hidden" value="${redirectUrl }"/>
			验证码：<input id="jcaptchaCode" name="jcaptchaCode" type="text" />
        	<img class="jcaptcha-btn jcaptcha-img" src="jcaptcha.jpg" title="点击更换验证码" />
        	<a class="jcaptcha-btn" href="javascript:;">换一张</a>
        	<input id ="submit" type="button" value="提交"/>
        <br/>
		</form>
	</body>
</html>