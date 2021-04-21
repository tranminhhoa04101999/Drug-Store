<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="${pageContext.servletContext.contextPath }/">
<title>Lấy Tài Khoản</title>

<link href="static/vendor/icofont/icofont.min.css" rel="stylesheet">
<link href="static/css/style_signup_login.css" rel="stylesheet">

</head>
<body>
	<div class="main">
		<section class="signup">
			<div class="container">
				<div class="signup-content">
					<form id="signup-form" class="signup-form" action="user/laytaikhoan.htm" method="post">
						<h2 class="form-title">Lấy Lại Tài Khoản</h2>
						<div class="form-group">						
							<input type="text" class="form-input" name="email" 
								id="userID" placeholder="Nhập vào EMAIL để lấy lại" required/>
						</div>
						<div class="form-group">
							<input type="submit" name="submit" id="submit"
								class="form-submit" value="Gửi Tài Khoản về email" />
							${message }
						</div>
					</form>
					<p class="loginhere">
						<a href="user/login.htm" class="loginhere-link">Đăng Nhập</a>
						<br>
						<br>
                        <a href="user/trangchu.htm" class="loginhere-link">Trang Chủ</a>
                    </p>
				</div>
			</div>
		</section>
	</div>



	<script src="static/vendor/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="static/js/main.js"></script>

</body>
</html>