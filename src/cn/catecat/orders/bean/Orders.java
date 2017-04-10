package cn.catecat.orders.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cn.catecat.status.bean.Status;
import cn.catecat.user.address.bean.UserAddress;
import cn.catecat.user.bean.User;

/**
 * 订单模块实体类对象
 * @author 钟思平
 *
 */
public class Orders implements Serializable{
	/**
	 * 
	 */
	/**
	 * 	ID 				id				String/varchar		 40
		总计				total				float/number
		商品个数			cartItemCount		Integer/number  250
		外送地址			address				String/varchar		250
		送餐时间			sendTime			String/varchar		40
		订单状态			status				int/number		 2	外键status
		订单项			orderItem			Set<OrderItem>  	外键 多对一
		留言				leaveWords			String/varchar		 250
		订单生成时间		createOrderstime	String/varchar		40
		交易完成时间		receiveTime			String/varchar		40
		预支付			prepayment			float/number		 (3,2)
		用户ID			user									外键User
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private float total;
	private int cartItemCount;
	private String leaveWords;
	private String createOrdersTime;
	private String receiveTime;
	private String sendTime;
	private float prepayment;
	//外送地址
	private String address;
	//订单状态：外键
	private Status status;
	//用户ID:外键
	private User user;
	//订单中所属的多个订单项：
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeaveWords() {
		return leaveWords;
	}
	public void setLeaveWords(String leaveWords) {
		this.leaveWords = leaveWords;
	}
	public float getPrepayment() {
		return prepayment;
	}
	public void setPrepayment(float prepayment) {
		this.prepayment = prepayment;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public int getCartItemCount() {
		return cartItemCount;
	}
	public void setCartItemCount(int cartItemCount) {
		this.cartItemCount = cartItemCount;
	}
	public String getCreateOrdersTime() {
		return createOrdersTime;
	}
	public void setCreateOrdersTime(String createOrdersTime) {
		this.createOrdersTime = createOrdersTime;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	
}
