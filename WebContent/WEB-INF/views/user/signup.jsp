<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="${pageContext.servletContext.contextPath }/">
<title>Đăng Ký</title>

<link href="static/vendor/icofont/icofont.min.css" rel="stylesheet">
<link rel="stylesheet" href="static/vendor/sematic-ui/semantic.min.css" />

<link rel="stylesheet"
	href="static/vendor/fontawesome-free/css/all.min.css" />

<link href="static/css/style_signup_login.css" rel="stylesheet">

</head>
<body>	
	<div class="main">
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <form:form id="signup-form" class="signup-form" action="user/signup.htm" modelAttribute="dangky">
                        <h2 class="form-title">Tạo Tài Khoản</h2>
                        <div class="form-group">
                            <form:input class="form-input" path="userID" placeholder="Nhập UserID"/>
                            <div style='color:red'><form:errors path="userID" /></div>
                        </div>
                        <div class="form-group">
                            <form:input class="form-input" path="password" type="password" placeholder="Nhập password"/>
                            <div style='color:red'><form:errors path="password" /></div>
                        </div>
                           	<form:input class="form-input" path="admin" type="hidden" value="0"/>
                        <div class="form-group">
                            <form:input class="form-input" path="email" placeholder="Nhập Email"/>
                            <div style='color:red'><form:errors path="email" /></div>
                        </div>
                        <div class="form-group">
                            <form:input class="form-input" path="fullname" placeholder="Nhập fullname"/>
                            <div style='color:red'><form:errors path="fullname" /></div>
                        </div>
                        	<form:input class="form-input" path="available" type="hidden" value="1"/>
                        
                        <div class="form-group">
                            <input type="submit" name="submit" id="submit" class="form-submit" value="Đăng Ký"/>
                        </div>
                        ${message }
                    </form:form>
                    <p class="loginhere">
                        Bạn đã có tài khoản ? <a href="user/login.htm" class="loginhere-link">Đăng nhập ở đây</a>
                    </p>
                </div>
            </div>
        </section>
    </div>
    
    
    
    <script src="static/vendor/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="static/js/main.js"></script>
</body>
</html>