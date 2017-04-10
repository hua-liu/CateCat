package cn.catecat.user.address.dao.impl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import cn.catecat.user.address.dao.UserAddressDao;

/**
 * 用户地址模块Dao层实现类
 * @author 钟思平
 *
 */
@Component
public class UserAddressDaoImpl extends HibernateDaoSupport implements UserAddressDao{
	//注入SessionFactory
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){  
		super.setSessionFactory(sessionFactory);  
	} 

}
