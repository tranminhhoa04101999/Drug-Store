package ptithcm.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.Order;
import ptithcm.entity.OrderDetails;
import ptithcm.entity.Products;
import ptithcm.entity.Types;
import ptithcm.entity.Users;


@Transactional
@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	ServletContext context;
	
	@RequestMapping("sanpham")
	public String getTrangChinh(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Products";
		Query query = session.createQuery(hql);
		List<Products> list = query.list();
		model.addAttribute("sanphams",list);
		return "admin/sanpham";
	}
	@ModelAttribute("typest")
	public List<Types> getTypes(){
		Session session = factory.getCurrentSession();
		String hql = "FROM Types";
		Query query = session.createQuery(hql);
		List<Types> list = query.list();
		return list;
	}
	
	@RequestMapping(value = "themsanpham",method = RequestMethod.GET)
	public String themmoisanpham(ModelMap model) {
		model.addAttribute("themsanpham",new Products());	
		return "admin/themsanpham";
	}
	@RequestMapping(value = "themsanpham",method = RequestMethod.POST)
	public String themmoisanpham(ModelMap model,@ModelAttribute("themsanpham") Products themsanpham,@RequestParam("photo2") MultipartFile photo,BindingResult errors) {
		if(themsanpham.getName().isEmpty() || themsanpham.getName().trim()=="") {
			errors.rejectValue("name", "themsanpham", "Vui L??ng Nh???p t??n s???n ph???m !");
		}
		else if(photo.getOriginalFilename().trim().length()==0) {
			model.addAttribute("message1","Vui l??ng ch???n ???nh !");
		}
		else {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				String photoPath = "";
				photoPath = context.getRealPath("/image/"+photo.getOriginalFilename());
				photo.transferTo(new File(photoPath));
				themsanpham.setPhoto(photo.getOriginalFilename());
				
				session.save(themsanpham);
				t.commit();
				model.addAttribute("message","Th??m m???i th??nh c??ng !!");
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message","Th??m m???i Th???t b???i !!");
			}
			finally {
				session.close();
			}
		}
		return "admin/themsanpham";
	}
	
	@RequestMapping(value ="xoa/{productID}",method = RequestMethod.GET)
	public String delete(ModelMap model,@PathVariable("productID") String productid) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {			
			
			Products pd = (Products)session.get(Products.class,productid);
			session.delete(pd);
			
			t.commit();
			model.addAttribute("message","Xo?? th??nh c??ng !!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message","Xo?? Th???t B???i!");
		}
		finally {
			session.close();
		}
		return "redirect:/admin/sanpham.htm";
	}
	
	@RequestMapping(value="suasanpham/{productID}",method=RequestMethod.GET)
	public String suasanpham(ModelMap model,@PathVariable("productID") String productid) {
		Session session = factory.getCurrentSession();
		Products pd = (Products)session.get(Products.class, productid);
		
		model.addAttribute("products",pd);
		return "admin/suasanpham";
	}
	
	@RequestMapping(value ="suasanpham",method = RequestMethod.POST)
	public String suasanpham(ModelMap model,@ModelAttribute("products") Products products,@RequestParam("photo3") MultipartFile photo1,BindingResult errors) {
		if(products.getName().trim().length()==0) {
			errors.rejectValue("name", "products", "Vui L??ng Nh???p T??n s???n ph???m !");
		}
//		else if(photo1.getOriginalFilename().trim().length()==0) {
//			model.addAttribute("message1","Vui l??ng ch???n ???nh !");
//		}
		else {
			Session session = factory.openSession();
			Session s2 = factory.getCurrentSession();
			Transaction t = session.beginTransaction();
			
			try {
				if(photo1.getOriginalFilename().trim().length()==0) {
					Products productssssss = (Products) s2.get(Products.class, products.getProductID());
					products.setPhoto(productssssss.getPhoto());
				}
				else {
					String photoPath = "";
					photoPath = context.getRealPath("/image/"+photo1.getOriginalFilename());
					photo1.transferTo(new File(photoPath));
					products.setPhoto(photo1.getOriginalFilename());
				}				
				
				session.update(products);
				t.commit();
				model.addAttribute("message","S???a th??nh c??ng!");
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message","S???a th???t b???i!" +e);
			}
			finally {
				session.close();
			}
		}
		return "admin/suasanpham";
	}
	/////////////// tai khoan /////////////////
	//
	//
	
	
	@RequestMapping("taikhoan")
	public String getTaikhoan(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Users";
		Query query = session.createQuery(hql);
		List<Users> list = query.list();
		model.addAttribute("taikhoans",list);
		return "admin/taikhoan";
	}
	
	@RequestMapping(value ="xoataikhoan/{userID}",method = RequestMethod.GET)
	public String xoataikhoan(ModelMap model,@PathVariable("userID") String userid) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {					
			Users us =(Users)session.get(Users.class, userid);
			session.delete(us);
			
			t.commit();
			model.addAttribute("message","Xo?? th??nh c??ng !!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message","Xo?? Th???t B???i!");
		}
		finally {
			session.close();
		}
		return "redirect:/admin/taikhoan.htm";
	}
	
	@ModelAttribute("laytatcataikhoan")
	public List<Users> laytatcataikhoan() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Users";
		Query query = session.createQuery(hql);
		List<Users> list = query.list();
		return list;
	}
	
	public boolean Checktaikhoan(List<Users> list, String userID) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUserID().equals(userID)) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "themtaikhoan",method = RequestMethod.GET)
	public String themtaikhoan(ModelMap model) {
		model.addAttribute("themtaikhoan",new Users());	
		return "admin/themtaikhoan";
	}
	@RequestMapping(value = "themtaikhoan",method = RequestMethod.POST)
	public String themtaikhoan(ModelMap model,@ModelAttribute("themtaikhoan") Users themtaikhoan,BindingResult errors) {
		
		String EMAIL_PATTERN = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
		List<Users> userxx = laytatcataikhoan();
		if (themtaikhoan.getUserID().trim().length() == 0 || themtaikhoan.getUserID() == null || themtaikhoan.getUserID() == "") {
			errors.rejectValue("userID", "dangky", "Vui L??ng Nh???p UserID !");
		} else if (themtaikhoan.getPassword().trim().length() == 0 || themtaikhoan.getPassword() == "") {
			errors.rejectValue("password", "dangky", "Vui L??ng Nh???p ????ng Password!");
		} else if (Pattern.matches(EMAIL_PATTERN, themtaikhoan.getEmail()) == false) {
			errors.rejectValue("email", "dangky", "Vui L??ng Nh???p Email ????ng ?????nh d???ng !");
		} else if (themtaikhoan.getFullname().trim().length() == 0 || themtaikhoan.getFullname() == "") {
			errors.rejectValue("fullname", "dangky", "Vui L??ng Nh???p fullname !");
		} else if (Checktaikhoan(userxx, themtaikhoan.getUserID())) {
			errors.rejectValue("userID", "dangky", "T??n UserID ???? t???n t???i!");
		} else {	
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.save(themtaikhoan);
				t.commit();
				model.addAttribute("message","Th??m m???i th??nh c??ng !!");
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message","Th??m m???i Th???t b???i !!");
			}
			finally {
				session.close();
			}
		}
		return "admin/themtaikhoan";
	}
	
	@RequestMapping(value="suataikhoan/{userID}",method=RequestMethod.GET)
	public String suataikhoan(ModelMap model,@PathVariable("userID") String userid) {
		Session session = factory.getCurrentSession();
		Users us = (Users)session.get(Users.class, userid);
		
		model.addAttribute("users",us);
		return "admin/suataikhoan";
	}
	
	@RequestMapping(value ="suataikhoan",method = RequestMethod.POST)
	public String update(ModelMap model,@ModelAttribute("users") Users users,BindingResult errors) {
		String EMAIL_PATTERN = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
		if(users.getPassword().trim().length()==0) {
			errors.rejectValue("password", "users", "Vui L??ng Nh???p password !");
		}
		else if(Pattern.matches(EMAIL_PATTERN, users.getEmail()) == false) {
			errors.rejectValue("email", "users", "Vui L??ng Nh???p Email ????ng ?????nh d???ng !");
		}
		else if(users.getFullname().trim().length()==0) {
			errors.rejectValue("fullname", "users", "Vui L??ng Nh???p fullname !");
		}
		else {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.update(users);
				t.commit();
				model.addAttribute("message","S???a th??nh c??ng!");
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message","S???a th???t b???i!");
			}
			finally {
				session.close();
			}
		}
		return "admin/suataikhoan";
	}
	
	/// logout
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("admin");
		return "redirect:/user/trangchu.htm";
	}
	// ==============================Hoa don ============================//
	//
	//
	@RequestMapping("hoadon")
	public String gethoadon(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Order";
		Query query = session.createQuery(hql);
		List<Order> list = query.list();
		model.addAttribute("orders",list);
		model.addAttribute("themhoadon",new Order());
		// hien user len combobox khi them hoa don
		String hqlcombobox = "from Users where admin=0";
		Query query2 = session.createQuery(hqlcombobox);
		List<Users> listusers = query2.list();
		model.addAttribute("userscombobox",listusers);
		
		return "admin/hoadon";
	}
	
	@RequestMapping(value = "chitiethoadon/{orderID}",method = RequestMethod.GET)
	public String getchitiethoadon(ModelMap model,@PathVariable("orderID") Integer orderid,HttpSession session2) {		
		Session session = factory.getCurrentSession();
		String hql = "FROM OrderDetails WHERE order.orderID = '"+orderid+"'";
		Query query = session.createQuery(hql);
		List<OrderDetails> list = query.list();
		model.addAttribute("orderdetails",list);
		session2.setAttribute("orderiddd", orderid);
		//du lieu products do vao combox
		String hql1 = "from Products";
		Query query2 = session.createQuery(hql1);
		List<Products> listProductsCombobox = query2.list();
		model.addAttribute("productscombobox",listProductsCombobox);
		
		return "admin/chitiethoadon";
	}
	
	@RequestMapping(value = "xoachitiethoadon/{orderDetailsID}",method = RequestMethod.GET)
	public String xoachitethoadonGET(ModelMap model,@PathVariable("orderDetailsID") Integer orderDetailsID,HttpSession session2,RedirectAttributes rc) {
		
		Integer ss = (Integer)session2.getAttribute("orderiddd");
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {					
			OrderDetails us =(OrderDetails)session.get(OrderDetails.class, orderDetailsID);
			session.delete(us);
			
			t.commit();
			//model.addAttribute("message","Xo?? th??nh c??ng !!");
			rc.addFlashAttribute("message","Xo?? th??nh c??ng !!");
		} catch (Exception e) {
			t.rollback();
			//model.addAttribute("message","Xo?? Th???t B???i!");
			rc.addFlashAttribute("message","Xo?? Th???t B???i!");
		}
		finally {
			session.close();
		}
		return "redirect:/admin/chitiethoadon/"+ss+".htm";
	}
	
	@ModelAttribute("tatcachitiethoadon")
	public List<OrderDetails> tatcachitiethoadon(){		
		Session session = factory.getCurrentSession();
		String hql = "FROM OrderDetails";
		Query query = session.createQuery(hql);
		List<OrderDetails> list = query.list();
		return list;	
	}
	public boolean Checkchitiet(List<OrderDetails> list, Integer chitietID) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getOrder().getOrderID().equals(chitietID)) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "xoahoadon/{orderID}",method = RequestMethod.GET)
	public String xoahoadon(ModelMap model,@PathVariable("orderID") Integer orderidx ,RedirectAttributes rc) {
		List<OrderDetails> odx = tatcachitiethoadon();
		
		if(Checkchitiet(odx, orderidx)) {
			//model.addAttribute("message","Vui l??ng xo?? h???t chi ti???t !");
			rc.addFlashAttribute("message","Vui l??ng xo?? h???t chi ti???t !");
		}
		else {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {					
				Order us =(Order)session.get(Order.class, orderidx);
				session.delete(us);
				
				t.commit();
				//model.addAttribute("message","Xo?? th??nh c??ng !!");
				rc.addFlashAttribute("message","Xo?? th??nh c??ng !!");
			} catch (Exception e) {
				t.rollback();
				//model.addAttribute("message","Xo?? Th???t B???i!");
				rc.addFlashAttribute("message","Th???t B???i !!");
			}
			finally {
				session.close();
			}
		}
		return "redirect:/admin/hoadon.htm";
	}
	
	
	@RequestMapping(value = "themhoadon",method = RequestMethod.POST)
	public String themhoadon(ModelMap model,@ModelAttribute("themhoadon") Order themhoadon1,BindingResult errors,RedirectAttributes rc) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql="FROM Users WHERE userID='"+themhoadon1.getUsers().getUserID()+"'";
		Query query = session.createQuery(hql);
		List<Users> list = query.list();
		
		if(list.isEmpty()) {
			rc.addFlashAttribute("message2","UserID kh??ng t???n t???i !");
			session.close();
		}
		else {		
			try {
				session.save(themhoadon1);
				t.commit();
				rc.addFlashAttribute("message1","th??m m???i ho?? ????n th??nh c??ng !");
			} catch (Exception e) {
				t.rollback();
				rc.addFlashAttribute("message1","Th??m m???i ho?? ????n th???t b???i !");
			} finally {
				session.close();
			}
		}
		
		
		return "redirect:/admin/hoadon.htm";
	}
	
	@RequestMapping(value = "suahoadon/{orderID}",method = RequestMethod.GET)
	public String suahoadonGET(ModelMap model,@PathVariable("orderID") Integer orderid) {
		Session session = factory.getCurrentSession();
		Order sd = (Order)session.get(Order.class, orderid);	
		model.addAttribute("suahoadon",sd);
		return "admin/suahoadon";
	}
	@RequestMapping(value = "suahoadon",method = RequestMethod.POST)
	public String suahoadon(@ModelAttribute("suahoadon") Order suahoadon,RedirectAttributes rc) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {			
			session.update(suahoadon);
			t.commit();
			rc.addFlashAttribute("message6","S???a th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			rc.addFlashAttribute("message6","S???a th???t b???i!");
		}
		finally {
			session.close();
		}
		return "redirect:/admin/hoadon.htm";
	}
	
	@ModelAttribute("themchitiethoadon")
	public OrderDetails themchitiethoadon1() {
		return new OrderDetails();
	}
	
	@RequestMapping(value = "themchitiethoadon",method = RequestMethod.POST)
	public String themchitiethoadon(ModelMap model,@ModelAttribute("themchitiethoadon") OrderDetails tcthd,HttpSession session2,RedirectAttributes rc) {	
		
		Integer ss = (Integer)session2.getAttribute("orderiddd");
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();	
		
		String hql="FROM OrderDetails WHERE order.orderID="+ss+" AND products.productID="+tcthd.getProducts().getProductID()+"";
		Query query = session.createQuery(hql);
		List<OrderDetails> list = query.list();
		if(list.isEmpty()) {
			
			try {		
				session.save(tcthd);
				t.commit();
				rc.addFlashAttribute("message1","Th??m m???i th??nh c??ng !");
			} catch (Exception e) {
				t.rollback();
				rc.addFlashAttribute("message1","Th??m m???i th???t b???i !");
			}
			finally {
				session.close();
			}
		}
		else {
			try {	
				OrderDetails tcthxxx= list.get(0);
				tcthxxx.setProducts(tcthd.getProducts());
				tcthxxx.setQuatity(tcthd.getQuatity());
				session.update(tcthxxx);
				t.commit();
				rc.addFlashAttribute("message1","Thay ?????i th??nh c??ng !");
			} catch (Exception e) {
				t.rollback();
				rc.addFlashAttribute("message1","Thay ?????i th???t b???i !" + e);
			}
			finally {
				session.close();
			}
		}
		
		return "redirect:/admin/chitiethoadon/"+ss+".htm";
	}
	
	//================= doanh thu ====================//
	@RequestMapping(value = "doanhthu" ,method = RequestMethod.GET)
	public String doanhthuGet(ModelMap model) {
		
		return "admin/doanhthu";
	}
	
	@RequestMapping(value = "laydoanhthu",method = RequestMethod.POST)
	public String doanhthu(ModelMap model,@RequestParam("ngaybatdau") String ngaybatdau,@RequestParam("ngayketthuc") String ngayketthuc) {
		int sosanh = ngayketthuc.compareTo(ngaybatdau);
		
		Session session = factory.getCurrentSession();
		String hql = "from Order where date >= '"+ngaybatdau+"' AND date <='"+ngayketthuc+"' AND status=1";
		Query query = session.createQuery(hql);
		List<Order> list = query.list();
		if(sosanh<0) {
			model.addAttribute("message","Ng??y k???t th??c l???n h??n ng??y b???t ?????u !!!!");
			return "admin/doanhthu";
		}
		model.addAttribute("danhsach",list);
		int tong = 0;
		
		for(int i =0;i<list.size();i++) {
			tong += list.get(i).getAmount();
		}
		
		model.addAttribute("tong",tong);
		
		return "admin/doanhthu";
	}
}






















