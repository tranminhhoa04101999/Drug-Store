<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="${pageContext.servletContext.contextPath }/">
<title>Đăng Nhập</title>

<link href="static/vendor/icofont/icofont.min.css" rel="stylesheet">
<link href="static/css/style_signup_login.css" rel="stylesheet">

</head>
<body>
	<div class="main">
		<section class="signup">
			<div class="container">
				<div class="signup-content">
					<form id="signup-form" class="signup-form" action="user/login.htm" method="post">
						<h2 class="form-title">Đăng Nhập</h2>
						<div class="form-group">						
							<input type="text" class="form-input" name="userID" 
								id="userID" placeholder="userID của bạn" required/>
						</div>
						<div class="form-group">
							<input type="password" class="form-input" name="password"
								id="password" placeholder="Mật khẩu" required/> 
						</div>
						<div class="form-group">
							<input type="submit" name="submit" id="submit"
								class="form-submit" value="Đăng Nhập" />
							${message }
						</div>
					</form>
					<p class="loginhere">
                        <a href="user/signup.htm" class="loginhere-link">Đăng ký ở đây</a>
                        <br>
                        <br>
                        <a href="user/trangchu.htm" class="loginhere-link">Trang Chủ</a>
                        <br>
                        <br>
                        <a href="user/laytaikhoan.htm" class="loginhere-link">Quên Mật Khẩu</a>
                    </p>
				</div>
			</div>
		</section>
	</div>



	<script src="static/vendor/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="static/js/main.js"></script>

</body>
</html>