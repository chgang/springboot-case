<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>登录</title>
  <link href="<%=request.getContextPath()%>/reset.css" rel="stylesheet">
  <link href="<%=request.getContextPath()%>/login.css" rel="stylesheet">
  <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/login.js"></script>
</head>
<body>
  <div class="login__container">
    <h4>登录 | Login</h4>
    <input type="text" placeholder="请输入用户名" id="user" name="user" class="input" onfocus="hideMsg('user')"/>
    <div class="error__msg" id="user_msg"></div>
    <input type="password" placeholder="请输入密码" id="pass" name="pass" class="input" onfocus="hideMsg('pass')"/>
    <div class="error__msg" id="pass_msg"></div>
    <input type="button" value="登录" id="login" class="login"/>
  </div>
</body>
</html>