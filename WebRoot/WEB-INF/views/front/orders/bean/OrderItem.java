package cn.catecat.orders.bean;

import java.io.Serializable;

import cn.catecat.cate.bean.Cate;
import cn.catecat.user.bean.User;

/**
 * 订单模块：订单项实体类对象
 * @author 钟思平
 *
 */
public class OrderItem implements Serializable{
	/**
	 * 	ID					id				String/varchar 	 40
	 	美食名字			name			String/varchar   	 40
		美食数量			count   			int/number	     10
		美食单价			price			float/number
		小计				subtotal			float/number	
		美食ID				cate			   	外键cate
		订单ID				orders			外键orders

	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int count;
	private float price;
	private float subtotal;
	private Cate cate;	  //美食ID：外键
	private Orders orders;//订单ID:外键
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Cate getCate() {
		return cate;
	}
	public void setCate(Cate cate) {
		this.cate = cate;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getPrice() {
		return price;
	}
}
