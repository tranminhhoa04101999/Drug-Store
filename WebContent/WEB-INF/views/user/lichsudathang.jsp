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
	
	<div class="pusher">
		<div class="main-content">
			<div class="ui grid stackable padded">
				<div class="column">
				${message }
				${message6 }
					<table class="ui celled table">				
						<thead>
							<tr>							
								<th>OrderID</th>
								<th>Ngày Đặt</th>
								<th>Tổng Tiền</th>
								<th>UserID</th>
								<th>Trạng Thái</th>
								<th style='width:120px'></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${orders}">
								<tr>
									<td>${p.orderID }</td>
									<td><f:formatDate value="${p.date }" pattern="dd-MM-yyyy" /></td>
									
									<td><f:formatNumber type="currency" maxFractionDigits="0" currencySymbol="" value="${p.amount }"/> đ</td>
									<td>${p.users.userID }</td>
									<td>${p.status?'Đã Thanh Toán':'Chưa Thanh Toán' }</td>
									<td>
										<a href = "user/chitietlichsudathang/${p.orderID }.htm" class="ui teal button">Chi Tiết</a>									
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${orderdetails.size()>0 }">
					<table class="ui celled table">
						<thead>
							<tr>
								
								<th>Tên Sản Phẩm</th>
								<th>Gía Sản Phẩm</th>
								<th>Số Lượng Đặt</th>
								<th>Tổng Tiền</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${orderdetails}">
								<tr>
									
									<td>${p.products.name }</td>
									<td><f:formatNumber type="currency" maxFractionDigits="0" currencySymbol="" value="${p.products.price }"/> đ</td>
									<td>${p.quatity}</td>
									<td>${p.quatity * p.products.price}</td>									
								</tr>
							</c:forEach>							
						</tbody>
					</table>
					
						<a href = "user/lichsudathang.htm" class="ui teal button">Quay Lại</a>
					</c:if>
				</div>
			</div>
		</div>
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