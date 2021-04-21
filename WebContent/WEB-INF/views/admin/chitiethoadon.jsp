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
		</a> <a class="item" href="admin/logout.htm">
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
								<th>ProductID</th>
								<th>Tên Sản Phẩm</th>
								<th>Gía Sản Phẩm</th>
								<th>Số Lượng Đặt</th>
								<th>Tổng Tiền</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${orderdetails}">
								<tr>
									<td>${p.products.productID }</td>
									<td>${p.products.name }</td>
									<td>${p.products.price }</td>
									<td>${p.quatity}</td>
									<td>${p.quatity * p.products.price}</td>
									<td><a href = "admin/xoachitiethoadon/${p.orderDetailsID}.htm" class="ui teal button">Xoá</a><td>
								</tr>
							</c:forEach>							
						</tbody>
					</table>
					<a href="admin/hoadon.htm" class="ui teal button"><i
						class="angle left icon"></i>Quay Lại</a>
				</div>
			</div>
			<div class="ui grid stackable padded">			
				<form:form class="ui form" action="admin/themchitiethoadon.htm"
						modelAttribute="themchitiethoadon" method="post">
							
						<div class="ui">
							<div class="ui green horizontal label">Product ID</div>
								
							<form:select path="products.productID" items="${productscombobox }" itemValue="productID" itemLabel="name">
								
							</form:select>				
						</div>					
						<div class="ui">
							<div class="ui green horizontal label">Số Lượng Đặt</div>
							<form:input path="quatity" type="number" value="1" min="1"/>
						</div>
							<form:input path="order.orderID" value="${orderiddd}" type="hidden"/>	
						
					<div class="ui">
							<button class="ui teal button">Thêm hoặc Sửa</button>
							${message1 }
						</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
