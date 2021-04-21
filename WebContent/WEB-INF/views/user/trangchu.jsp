<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<base href="${pageContext.servletContext.contextPath }/">
<title>&reg;Quầy Thuốc 146: Uy tín,chất lượng.</title>


<!-- Google Fonts -->

<!-- Vendor CSS Files -->
<link href="static/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="static/vendor/icofont/icofont.min.css" rel="stylesheet">
<link href="static/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="static/vendor/aos/aos.css" rel="stylesheet">
<link rel="stylesheet" href="static/vendor/sematic-ui/semantic.min.css" />
<!-- Template Main CSS File -->
<link href="static/css/style.css" rel="stylesheet">

</head>

<body>

	<!-- ======= Header ======= -->
	<header id="header" class="fixed-top d-flex align-items-center">
		<div class="container-fluid">
			<div class="header-container d-flex align-items-center">
				<div class="logo mr-auto">
					<h1 class="text-light">
						<a href="user/trangchu.htm"><span>QUẦY THUỐC 146</span></a>
					</h1>
				</div>

				<nav class="nav-menu d-none d-lg-block">

					<ul>
							
						<li class="drop-down"><a>SẢN PHẨM</a>
							<ul>
								<c:forEach var="p" items="${types}">
									<li><a href="user/trangchu.htm?typeID=${p.typeID}">${p.typeName}</a></li>
								</c:forEach>
							</ul></li>
						<li><a href="user/giohang.htm"><i class="shopping cart icon"></i>Giỏ Hàng</a></li>
						<c:choose>
							<c:when test="${users==null}">
								<li><a href="user/signup.htm">Đăng Ký</a></li>
								<li class="get-started"><a href="user/login.htm">Đăng
										Nhập</a></li>
							</c:when>
							<c:when test="${users!=null }">
								<li><a href="user/thongtinuser.htm"><i class="user circle icon"></i>Thông Tin</a></li>
								<li><a href="user/lichsudathang.htm"><i class="calendar outline icon"></i>Lịch Sử Đặt Hàng</a></li>
								<li class="get-started"><a href="user/logoutuser.htm"><i class="angle double right icon"></i>Đăng
										Xuất</a></li>
							</c:when>
						</c:choose>
					</ul>
				</nav>
				<!-- .nav-menu -->
			</div>
			<!-- End Header Container -->
		</div>
	</header>
	<!-- End Header -->

	<!-- Main -->


	<!-- ============Hero Section============ -->
	<section id="hero" class="d-flex align-items-center">
		<div class="container text-center position-relative"
			data-aos="dau-trang" data-aos-delay="200">
			<h1>Sức Khoẻ Của Các Bạn</h1>
			<h2>Là Sứ Mệnh Của Chúng Tôi</h2>
			<a class="btn-dathang scrollto">Đặt Hàng</a>
		</div>
	</section>
	<!-- End hero section -->
	<!-- ============ tat ca san pham ============ -->
	<div id="tatca" class="ui five column grid">
		<div class="hienthi">
			<hr>
			<h3>Tất cả sản phẩm</h3>
			<hr>
		</div>
		<!-- hien thi card -->
		<c:forEach var="p" items="${sanphams}">
			<c:if test="${p.quatity!=0 && p.available}">
			<div class="column">
				<div class="ui cards">
					<div class="card">
						<div class="image">
							<img src="image/${p.photo }" width="200px" height="200px">
						</div>
						<div class="content">
							<div class="header">${p.name}</div>
							<div class="meta">${p.types.typeName }</div>
							<div class="description">
								<f:formatNumber type="currency" maxFractionDigits="0" currencySymbol="" value="${p.price}"/> đ
							</div>
						</div>
						<div class="extra content">
							<div class="ui two buttons">
							<form action="user/dathang/${p.productID }.htm" method="post">
								<button class="ui green button">Thêm Vào Giỏ</button>
							</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			</c:if>
		</c:forEach>
	</div>
	<!-- End Main -->
	<!-- ============ FOOTER ============ -->
	<footer id="footer">
		<div class="footer-top">
			<div class="container">
				<div class=row>
					<div class=" col-lg-3 col-md-6 footer-contact">
						<h3>Quầy Thuốc 146</h3>
						<p>
							Thôn 3,Thống Nhất <br> Bù Đăng,Bình Phước <br> VIỆT NAM
							<br> <strong>SĐT: </strong> (+84)374269758 <br> <strong>Email:
							</strong> tranminhhoa04101999@gmail.com <br>
						</p>

						<hr>
						<h6>Thời Gian Hoạt Động</h6>
						<p>
							Thứ 2 - Thứ 6 : 7:00AM - 8:00PM <br> Thứ 7 - CN : 7:00AM -
							5:00PM.
						</p>
					</div>
					<div class="col-lg-2 col-md-6 footer-links">
						<h4>Liên Hệ</h4>
						<ul>
							<li><i class="bx bx-chevron-right"></i><a
								href="https://www.facebook.com/hoatran0410/">Facebook</a></li>
						</ul>
					</div>
					<div class="col-lg-3 col-md-6 footer-content">
						<h5>Từ Khoá Sản Phẩm</h5>
					</div>
					<div class="col-lg-4 col-md-6 footer-links"></div>
				</div>
			</div>
		</div>

		<div id="bot-footer" class="container d-md-flex py-4">

			<div class="mr-md-auto text-center text-md-left">
				<div>
					&reg;<strong><span>Quầy Thuốc 146</span></strong>. Hân hạnh được
					phục vụ quý khách.
				</div>
				<div class="credits">
					Designed by <a href="https://www.facebook.com/hoatran0410">Trần
						Minh Hoà</a>
				</div>
			</div>
			<div class="social-links text-center text-md-right pt-3 pt-md-0">
				<a href="https://www.facebook.com/hoatran0410" class="facebook"><i
					class="bx bxl-facebook"></i></a> <a
					href="https://www.instagram.com/t_minhhoa_/" class="instagram"><i
					class="bx bxl-instagram"></i></a>
			</div>
		</div>
	</footer>
	<!-- end footer -->
	<a href="#" class="back-to-top"><i class="icofont-simple-up"></i></a>

	<!-- File main js -->
</body>

</html>