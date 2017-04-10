package cn.catecat.user.address.bean;

import java.io.Serializable;


import cn.catecat.user.bean.User;
public class UserAddress implements Serializable{

	/**
	 * 用户地址类
	 * userId		用户ID
	 * address		地址（省市区）
	 * moreAddress	详细的地址
	 * name			接收外买人
	 * phoneNo		接收人的手机号
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String address;
	private String moreAddress;
	private String name;
	private String phoneNo;
	//用户id
	private User user;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMoreAddress() {
		return moreAddress;
	}

	public void setMoreAddress(String moreAddress) {
		this.moreAddress = moreAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
