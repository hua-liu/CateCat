package cn.catecat.user.log.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import cn.catecat.user.log.dao.UserLogDao;

/**
 * 用户日志模块Dao层实现类
 * @author 钟思平
 *
 */
@Component
public class UserLogDaoImpl extends HibernateDaoSupport implements UserLogDao{
	//注入SessionFactory
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){  
		super.setSessionFactory(sessionFactory);  
	} 
}
