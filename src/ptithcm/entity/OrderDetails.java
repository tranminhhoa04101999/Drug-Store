package ptithcm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OrderDetails")
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailsID;
	
	@ManyToOne
	@JoinColumn(name = "OrderID")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "ProductID")
	private Products products;

	private Integer quatity;
	

	public Integer getOrderDetailsID() {
		return orderDetailsID;
	}

	public void setOrderDetailsID(Integer orderDetailsID) {
		this.orderDetailsID = orderDetailsID;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Integer getQuatity() {
		return quatity;
	}

	public void setQuatity(Integer quatity) {
		this.quatity = quatity;
	}
	
	
}
