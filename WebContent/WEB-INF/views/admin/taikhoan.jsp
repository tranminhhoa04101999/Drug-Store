<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.servletContext.contextPath }/">
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Admin Controller</title>

<link rel="stylesheet" href="static/vendor/sematic-ui/semantic.min.css" />

<link rel="stylesheet"
	href="static/vendor/fontawesome-free/css/all.min.css" />
<style>
:root { -
	-tablet: 768px; -
	-smallMonitor: 992px; -
	-largeMonitor: 1200px; -
	-font-family: 'Open Sans', sans-serif;
}

body {
	font-family: var(- -font-family) !important;
}

body ::-webkit-scrollbar {
	width: 6px;
}

.ui.vertical.menu.sidebar-menu {
	margin-top: 40px !important;
	max-height: calc(100% - 40px) !important;
	height: calc(100% - 40px) !important;
}

.ui.vertical.menu.sidebar-menu .item i.icon {
	float: left;
	margin: 0em 0.5em 0em 0em;
}

.main-content {
	margin-top: 40px;
}

@media ( min-width : 768px) {
	.ui.vertical.menu.sidebar-menu {
		visibility: visible;
		-webkit-transform: translate3d(0, 0, 0);
		transform: translate3d(0, 0, 0);
		width: 15rem !important;
	}
	.main-content {
		margin-left: 15rem;
	}
	.sidebar-menu-toggler {
		display: none !important;
	}
}
</style>
</head>

<body>
	<!-- sidebar -->
	<div class="ui teal sidebar inverted vertical menu sidebar-menu"
		id="sidebar">
		<div class="item">
			<div class="header">Quản Lý</div>
			<div class="menu">
				<a class="item" href="admin/doanhthu.htm">
					<div>
						<i class="money bill alternate outline icon"></i> Doanh Thu
					</div>
				</a>
			</div>
		</div>
		<div class="item">
			<div class="header">Sản Phẩm</div>
			<div class="menu">
				<a href="admin/sanpham.htm" class="item">
					<div>
						<i class="eye icon"></i> Hiển Thi
					</div>
				</a> <a href="admin/themsanpham.htm" class="item">
					<div>
						<i class="circle icon"></i> Thêm Mới
					</div>
				</a>
			</div>
		</div>
		<div class="item">
			<div class="header">User</div>
			<div class="menu">
				<a href="admin/taikhoan.htm" class="item">
					<div>
						<i class="eye icon"></i> Hiển Thị
					</div>
				</a> <a href="admin/themtaikhoan.htm" class="item">
					<div>
						<i class="circle icon"></i> Thêm
					</div>
				</a>
			</div>
		</div>
		<a class="item" href="admin/hoadon.htm">
			<div>
				<i class="money bill alternate icon"></i> Hoá Đơn
			</div>
		</a>
		<a class="item" href="user/trangchu.htm">
			<div>
					<i class="money bill alternate icon"></i> Đăng Xuất
			</div>
		</a>
	</div>

	<!-- sidebar -->
	<!-- top nav -->

	<nav class="ui teal top fixed inverted menu">
		<div class="left menu">
			<a href="#" class="sidebar-menu-toggler item" data-target="#sidebar">
				<i class="sidebar icon"></i>
			</a> <a class="header item"> &reg;Quầy Thuốc 146 </a>
		</div>

		<div class="right menu"></div>
	</nav>

	<!-- top nav -->

	<div class="pusher">
		<div class="main-content">
			<div class="ui grid stackable padded">
				<div class="column">
				${message }
					<table class="ui celled table">
						<thead>
							<tr>
								<th>UserID</th>
								<th>PassWord</th>
								<th>Admin</th>
								<th>Email</th>
								<th>FullName</th>
								<th>Available</th>
								<th style='width:120px'></th>
								<th style='width:120px'></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${taikhoans}">
								<tr>
									<td>${p.userID }</td>
									<td>${p.password }</td>
									<td>${p.admin?'admin':'user' }</td>
									<td>${p.email }</td>
									<td>${p.fullname}</td>
									<td>${p.available?'Hoạt Động':'Khoá' }</td>
									<c:if test="${p.admin!=true}">
									<td><a href="admin/xoataikhoan/${p.userID}.htm" class="ui teal button">Xoá</a></td>
									</c:if>
									<td><a href="admin/suataikhoan/${p.userID}.htm" class="ui teal button">Sửa</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="ui grid stackable padded">
				<div
					class="four wide computer eight wide tablet sixteen wide mobile  center aligned column">
					<div class="ui teal statistic">
						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
