package ptithcm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.jdi.Value;

import ptithcm.entity.Order;
import ptithcm.entity.OrderDetails;
import ptithcm.entity.Products;
import ptithcm.entity.Types;
import ptithcm.entity.Users;

@Transactional
@Controller
@RequestMapping("/user/")
public class HomeController {
	@Autowired
	SessionFactory factory;

	@Autowired
	JavaMailSender mailer;

	Users getuser = null;

	@RequestMapping("trangchu")
	public String getTrangChinh(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Products";
		Query query = session.createQuery(hql);
		List<Products> list = query.list();
		model.addAttribute("sanphams", list);
		return "user/trangchu";
	}

	// --/---------------dang ky tai khoan--------------------
	//
	//
	@ModelAttribute("taikhoan")
	public List<Users> getTaiKhoan() {
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

	@RequestMapping(value = "signup", method = RequestMethod.GET)
	public String themtaikhoan(ModelMap model) {
		model.addAttribute("dangky", new Users());
		return "user/signup";
	}

	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String themtaikhoan(ModelMap model, @ModelAttribute("dangky") Users dangky, BindingResult errors) {
		String EMAIL_PATTERN = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
		List<Users> userxx = getTaiKhoan();
		if (dangky.getUserID().trim().length() == 0 || dangky.getUserID() == null || dangky.getUserID() == "") {
			errors.rejectValue("userID", "dangky", "Vui Lòng Nhập UserID !");
		} else if (dangky.getPassword().trim().length() == 0 || dangky.getPassword() == "") {
			errors.rejectValue("password", "dangky", "Vui Lòng Nhập Đúng Password!");
		} else if (Pattern.matches(EMAIL_PATTERN, dangky.getEmail()) == false) {
			errors.rejectValue("email", "dangky", "Vui Lòng Nhập Email đúng định dạng !");
		} else if (dangky.getFullname().trim().length() == 0 || dangky.getFullname() == "") {
			errors.rejectValue("fullname", "dangky", "Vui Lòng Nhập fullname !");
		} else if (Checktaikhoan(userxx, dangky.getUserID())) {
			errors.rejectValue("userID", "dangky", "Tên UserID đã tồn tại!");
		} else {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				dangky.setUserID(dangky.getUserID().trim());
				session.save(dangky);
				t.commit();
				model.addAttribute("message", "Đăng ký thành công !!");
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Đăng ký Thất bại !!");
			} finally {
				session.close();
			}
		}
		return "user/signup";
	}

	// ---------------- dang nhap ---------------//
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(ModelMap model, @RequestParam("userID") String userid,
			@RequestParam("password") String password, HttpSession session2) {

		userid = userid.trim();
		Session session = factory.getCurrentSession();
		String hql = "FROM Users WHERE UserID='" + userid + "' AND Password='" + password + "'";
		Query query = session.createQuery(hql);
		List<Users> list = query.list();
		if (list.isEmpty() == false) {
			getuser = list.get(0);

			if (!getuser.isAvailable()) {
				model.addAttribute("message", "Tài Khoản đã bị khoá!");
				return "user/login";
			} else if (getuser.isAdmin()) {
				session2.setAttribute("admin", getuser);
				return "redirect:/admin/sanpham.htm";

			}
			session2.setAttribute("users", getuser);
			return "redirect:/user/trangchu.htm";
		}
		model.addAttribute("message", "Đăng nhập thất bại!");
		return "user/login";
	}

	@RequestMapping("logoutuser")
	public String logoutuser(HttpSession session) {
		session.removeValue("users");
		return "redirect:/user/trangchu.htm";
	}
	// ====================lay tai khoan =======================//

	@RequestMapping(value = "laytaikhoan", method = RequestMethod.GET)
	public String quenmatkhau() {
		return "user/laytaikhoan";
	}

	@RequestMapping(value = "laytaikhoan", method = RequestMethod.POST)
	public String quenmatkhau(ModelMap model, @RequestParam("email") String email) {
		try {
			Session session = factory.getCurrentSession();
			String hql = "FROM Users WHERE Email = '" + email + "'";
			Query query = session.createQuery(hql);
			List<Users> list = query.list();
			String dulieuguidi = "UserID : " + list.get(0).getUserID() + "\n\n Password : " + list.get(0).getPassword()
					+ "";

			// tao mail
			MimeMessage mail = mailer.createMimeMessage();
			// su dung tro giup
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(email);
			helper.setText(dulieuguidi, true);

			mailer.send(mail);
			model.addAttribute("message", "Gửi mail thành công!");
		} catch (Exception e) {
			model.addAttribute("message", "Gửi mail thất bại!");
		}

		return "user/laytaikhoan";
	}

	// ===================danh sach the loai =====================//
	@ModelAttribute("types")
	public List<Types> getTypes() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Types";
		Query query = session.createQuery(hql);
		List<Types> list = query.list();
		return list;
	}

	@RequestMapping(value = "trangchu", params = "typeID", method = RequestMethod.GET)
	public String phantheoloai(ModelMap model, @PathParam("typeID") Integer typeID) {
		Session session = factory.getCurrentSession();
		String hql = "from Products where types.typeID = " + typeID + "";
		Query query = session.createQuery(hql);
		System.out.println(hql);
		List<Products> list = query.list();
		model.addAttribute("sanphams", list);
		return "user/trangchu";
	}
	// ===================gio hang =====================//

//	@RequestMapping("giohang")
//	public String giohang() {
//		return "user/giohang";
//	}

	// ===================Thong tin user =====================//
	@RequestMapping(value = "thongtinuser", method = RequestMethod.GET)
	public String thongtinuser(ModelMap model, HttpSession session2) {
		String userid = getuser.getUserID();
		System.out.println(userid + " alo");

		Session session = factory.getCurrentSession();
		Users users = (Users) session.get(Users.class, userid);
		model.addAttribute("usersss", users);
		return "user/thongtinuser";
	}

	@RequestMapping(value = "thongtinuser", method = RequestMethod.POST)
	public String thongtinuser(ModelMap model, @ModelAttribute("usersss") Users users) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(users);
			t.commit();
			model.addAttribute("message", "Sửa thành công!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Sửa thất bại!");
		} finally {
			session.close();
		}

		return "redirect:/user/thongtinuser.htm";
	}

	// ============================== gio hang =============================//

	@RequestMapping(value = "giohang", method = RequestMethod.GET)
	public String giohangGET(HttpSession session2, ModelMap model) {
		if (session2.getAttribute("users") == null) {
			return "user/login";
		}	
		
		Users usersxx = (Users) session2.getAttribute("users");
		Session session = factory.getCurrentSession();
		String hql = "from Order where status=0 and userID='" + usersxx.getUserID() + "'";
		Query query = session.createQuery(hql);
		List<Order> listOrders = query.list();
		if (listOrders.isEmpty()) {
			model.addAttribute("check", 0);
		} else {
			// lay orderID
			Integer orderid = listOrders.get(0).getOrderID();
			// lay danh sach chi tiet hoa don
			String hqlorderdetails = "from OrderDetails where order.orderID=" + orderid + "";
			Query query2 = session.createQuery(hqlorderdetails);
			List<OrderDetails> listOrderDetails = query2.list();
			model.addAttribute("chitietgio", listOrderDetails);
			model.addAttribute("amountss",listOrders.get(0));
			model.addAttribute("orderidd",listOrders.get(0).getOrderID());
			// gui 1 cai orderID cho thanh toan
			
		}
		return "user/giohang";
	}
	
	@RequestMapping(value = "capnhatgio",method = RequestMethod.POST)
	public String capnhatgio(ModelMap model,@ModelAttribute("capnhatgio") OrderDetails orderDetails,
			@RequestParam(value = "ordID") List<Integer> ordids,@RequestParam(value = "quatity") List<Integer> qualities) {
		
		int dem =0;
		for( var orid : qualities) {
			dem++;
		}
			
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		for(int i=0;i<dem;i++) {
			String hql = "from OrderDetails where orderDetailsID="+ordids.get(i)+"";
			Query query = session.createQuery(hql);
			List<OrderDetails> list = query.list();
			OrderDetails or = list.get(0);
			or.setQuatity(qualities.get(i));
			
			try {
				if(qualities.get(i)==0) {
					session.delete(or);
				}
				else {
					session.update(or);
				}
				if(i==dem-1) {
					t.commit();
				}
				
				System.out.println("capnhat tiet hoa don thanh cong");
			} catch (Exception e) {
				t.rollback();
				System.out.println("cap nhat tiet hoa hoa don that bai");
			} finally {
				if(i==dem-1) {
					session.close();
				}
			}
		}
		
		return "redirect:/user/giohang.htm";
	}
	
	@RequestMapping(value = "thanhtoanhoadon",method = RequestMethod.POST)
	public String thanhtoangiohang(@RequestParam("orderid") Integer orderid,RedirectAttributes rc,ModelMap model) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		String hql = "from Order where orderID="+orderid+"";
		Query query = session.createQuery(hql);
		List<Order> list = query.list();
	// lam ngay 11
		String hqlsanpham ="from OrderDetails where order.orderID="+orderid+"";
		Query query2 = session.createQuery(hqlsanpham);
		List<OrderDetails> listorderDetails = query2.list();
		
		List<String> listid = new ArrayList<>();
		List<Integer> listsl = new ArrayList<>();
		//lay het so luong con lai cua 1 product
		for(int i =0;i<listorderDetails.size();i++) {
			// product trong kho
			String hqlsl = "from Products where productID="+listorderDetails.get(i).getProducts().getProductID()+"";
			Query query3 = session.createQuery(hqlsl);
			List<Products> listProduct = query3.list();
			// product trong gio 
			String hqlslmoi = "from OrderDetails where products.productID="+listorderDetails.get(i).getProducts().getProductID()+" AND order.orderID="+orderid+"";	
			Query query4 = session.createQuery(hqlslmoi);
			List<OrderDetails> listslmoi = query4.list();
			
			
			Products pd = listProduct.get(0);
			int soluong = listProduct.get(0).getQuatity(); // so luong cu
			int soluongM = listslmoi.get(0).getQuatity(); // so luong moi
			int slcuoi = soluong-soluongM;		
			pd.setQuatity(slcuoi);
			
			if(slcuoi<0) {
				rc.addFlashAttribute("messagehethang",listorderDetails.get(i).getProducts().getName() + "Số Lượng Trong kho không đủ !!!");
				System.out.println(listorderDetails.get(i).getProducts().getName() + "SO luong trong kho khong du");
				return "redirect:/user/giohang.htm";
			}
			else 
			{
				listid.add(listorderDetails.get(i).getProducts().getProductID());
				listsl.add(slcuoi);
			}	
		}
		
		for(int i = 0;i<listorderDetails.size();i++) {
			String hqlsl5 = "from Products where productID="+listid.get(i)+"";
			Query query5 = session.createQuery(hqlsl5);
			List<Products> listProduct5 = query5.list();
			
			Products pd5 = listProduct5.get(0);
			pd5.setQuatity(listsl.get(i));
			try {
				
				session.update(pd5);
				System.out.println("update so luong san pham thanh cong");
			} catch (Exception e) {
				t.rollback();
				System.out.println("update so luong san pham that bai");
			} 
		}
	//
		
		Order or = list.get(0);
		or.setStatus(true);
		or.setDate(new Date());
		try {
			session.update(or);
			t.commit();
			System.out.println("thanh toan thanh cong");
		} catch (Exception e) {
			t.rollback();
			System.out.println("thanh toan that bai");
		} finally {
			session.close();
		}
		
		return "redirect:/user/giohang.htm";
	}
	
	@RequestMapping(value = "remove/{orderDetailsID}",method = RequestMethod.GET)
	public String remove(@PathVariable("orderDetailsID") Integer orderDetailsID) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {					
			OrderDetails us =(OrderDetails)session.get(OrderDetails.class, orderDetailsID);
			session.delete(us);
			
			t.commit();
			System.out.println("remove thanh cong");
		} catch (Exception e) {
			t.rollback();
			System.out.println("remove that bai");
		}
		finally {
			session.close();
		}
		
		return "redirect:/user/giohang.htm";
	}
	
	// ============================== them vao gio hang =============================//
	@RequestMapping(value = "dathang/{productID}", method = RequestMethod.POST)
	public String dathangGET(ModelMap model, HttpSession session2, @PathVariable("productID") String productid) {

		if (session2.getAttribute("users") == null) {
			return "user/login";
		} else {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			Users usersxx = (Users) session2.getAttribute("users");
			// check 1 hoa don
			// String hqlcheck = "from Order where date is null AND userID =
			// '"+usersxx.getUserID()+"'";
			String hqlcheck = "from Order where status = 0 AND userID = '" + usersxx.getUserID() + "'";
			Query query = session.createQuery(hqlcheck);
			List<Order> listOrders = query.list();
			// lay ra 1 product
			String hqlproduct = "from Products where productID=" + productid + "";
			Query query2 = session.createQuery(hqlproduct);
			List<Products> listProducts = query2.list();

			if (listOrders.isEmpty()) {
				// tao 1 hoa don moi
				Order or = new Order();
				or.setDate(null);
//				or.setAmount(0);
				or.setUsers(usersxx);
				listOrders.add(or);
				try {
					session.save(or);
					// t.commit();
					System.out.println("tao hoa don thanh cong");
				} catch (Exception e) {
					t.rollback();
					System.out.println("tao hoa don that bai");
				}
			}

			// dua gia tri vao orderss
			Order orderss = new Order();
			orderss = (Order) listOrders.get(0);
			// dua gia tri vao productss
			Products productss = new Products();
			productss = (Products) listProducts.get(0);
			// lay so luong 1 product ra
			String hqlQuatity = "from OrderDetails where OrderID=" + orderss.getOrderID() + " AND ProductID="+ productid + "";
			Query query3 = session.createQuery(hqlQuatity);
			List<OrderDetails> list3 = query3.list();


			if (list3.isEmpty()) {
				// them product vao hoa don
				OrderDetails addDetail = new OrderDetails();
				addDetail.setOrder(orderss);
				addDetail.setProducts(productss);
				addDetail.setQuatity(1);
				try {
					session.save(addDetail);
					t.commit();
					System.out.println("them chi tiet hoa don thanh cong");
				} catch (Exception e) {
					t.rollback();
					System.out.println("them chi tiet hoa hoa don that bai");
				} finally {
					session.close();
				}
			}
			else {
							
				OrderDetails addDetail = list3.get(0);
				addDetail.setQuatity(list3.get(0).getQuatity() + 1);

				try {
					session.update(addDetail);
					t.commit();
					System.out.println("update chi tiet hoa don thanh cong");
				} catch (Exception e) {
					t.rollback();
					System.out.println("update chi tiet hoa hoa don that bai");
				} finally {
					session.close();
				}
			}

		}
		return "redirect:/user/trangchu.htm";
	}
	// ============================== lich su dat hang ============================//
	@RequestMapping(value = "lichsudathang",method = RequestMethod.GET)
	public String lichsudathang(ModelMap model,HttpSession session2) {
		Users userslichsu = (Users) session2.getAttribute("users");
		Session session = factory.getCurrentSession();
		
		String hql = "from Order where status=1 AND users.userID='"+userslichsu.getUserID()+"'";
		Query query = session.createQuery(hql);
		List<Order> list = query.list();
		model.addAttribute("orders",list);
		
		return "user/lichsudathang";
	}
	@RequestMapping(value = "chitietlichsudathang/{orderID}",method = RequestMethod.GET)
	public String chitietdathang(ModelMap model,@PathVariable("orderID") Integer orderid) {
		Session session = factory.getCurrentSession();
		String hql = "from OrderDetails where order.orderID="+orderid+"";
		Query query = session.createQuery(hql);
		List<OrderDetails> or = query.list();
		model.addAttribute("orderdetails",or);
		
		String hql2 = "from Order where orderID="+orderid+"";
		Query query2 = session.createQuery(hql2);
		List<Order> listor = query2.list();
		model.addAttribute("orders",listor);
		System.out.println("ssssssssssssssssssssss     "+orderid  );
		return "user/lichsudathang";
	}
	
}

















