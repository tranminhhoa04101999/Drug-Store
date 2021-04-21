<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>

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
		<a class="item" href="admin/logout.htm">
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
								<th style='width:100px'></th>
								<th style='width:100px'></th>
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
										<a href = "admin/chitiethoadon/${p.orderID}.htm" class="ui teal button">Chi Tiết</a>									
									</td>
									<td><a href = "admin/xoahoadon/${p.orderID}.htm" class="ui teal button">Xoá</a></td>
									<td><a href = "admin/suahoadon/${p.orderID}.htm" class="ui teal button">Sửa</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="ui grid stackable padded">
				<div
					class="four wide computer eight wide tablet sixteen wide mobile  center aligned column">
					<form:form class="ui form" action="admin/themhoadon.htm"
						modelAttribute="themhoadon" method="post">

						<br>
						<div class="ui fluid">
							<div class="ui green horizontal label">Ngày Đặt</div>
							<form:input path="date" type="date"  placeholder="Thêm ngày ..." />						
						</div>
						<br>						
						<div class="ui fluid">
							<div class="ui green horizontal label">UserID</div>
							
							<form:select path="users.userID" items="${userscombobox }" itemValue="userID" itemLabel="userID">
								
							</form:select>
							${message2 }
						</div>
						<br>
						<div class="ui fluid">
							<div class="ui green horizontal label">Trạng Thái</div>
							<form:select path="status" class="ui dropdown">
								<option value="1" >Đã Thanh Toán</option>
								<option value="0" >Chưa Thanh Toán</option>
							</form:select>					
						</div>
						<br>
						<div class="ui">
							<button class="ui teal button">Thêm Mới</button>
							${message1 }
						</div>					
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
