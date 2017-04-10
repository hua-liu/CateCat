package cn.catecat.user.address.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.catecat.user.address.dao.UserAddressDao;
import cn.catecat.user.address.service.dao.UserAddressService;

/**
 * 用户地址模块Service层实现类
 * @author 钟思平
 *
 */
@Component
public class UserAddressServiceImpl implements UserAddressService{
	//注入UserAddressDao
	@Autowired
	private UserAddressDao userAddressDao;
	public void setUserAddressDao(UserAddressDao userAddressDao) {
		this.userAddressDao = userAddressDao;
	}
}
