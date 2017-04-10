package cn.catecat.user.address.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.user.address.bean.UserAddress;
import cn.catecat.user.address.service.dao.UserAddressService;

public class UserAddressAction extends ActionSupport implements ModelDriven<UserAddress> {
	private static final long serialVersionUID = 1L;
	//驱动模块使用的对象
	private UserAddress userAddress;
	@Override
	public UserAddress getModel() {
		return userAddress;
	}
	//注入UserAddressService
	@Autowired
	private UserAddressService userAddressService;
	public void setUserAddressService(UserAddressService userAddressService) {
		this.userAddressService = userAddressService;
	}
	
}
