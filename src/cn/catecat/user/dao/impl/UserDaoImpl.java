package cn.catecat.user.dao.impl;


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.ResourceTransactionManager;

import cn.catecat.user.bean.User;
import cn.catecat.user.dao.UserDao;
import cn.catecat.user.log.bean.UserLog;
import sun.print.resources.serviceui;

/**
 * 用户模块Dao层实现类
 * @author 钟思平
 *
 */
@Component
public class UserDaoImpl extends HibernateDaoSupport implements UserDao{
	//注入SessionFactory
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){  
		super.setSessionFactory(sessionFactory);  
	}

	//后台进行校验
	@Override
	public int checkout(String type, String value) {
		String sql = "select count(*) from user where "+type+"=:value";
		Object object = this.getSessionFactory().getCurrentSession().createNativeQuery(sql).setParameter("value", value).getSingleResult();
		return Integer.parseInt(object.toString());
	}

	//用户注册
	@Override
	public void save(User user) {
		this.getHibernateTemplate().save(user);
	}

	//通过用户ID查询用户信息
	@Override
	public User findById(String id) {
		String hql = "from User where id = ?";
		List<User> list = this.getSessionFactory().getCurrentSession().createQuery(hql,User.class).setParameter(0, id).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	//用户登录
	@Override
	public User login(String username,String password) {
		String hql = "from User where username = ? and password = ?";
		List<User> list = this.getSessionFactory().getCurrentSession().createQuery(hql,User.class).setParameter(0, username).setParameter(1, password).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	//通过用户名查询用户
	@Override
	public boolean findByName(String username) {
		String hql = "from User where username = ?";
		List<User> list = this.getSessionFactory().getCurrentSession().createQuery(hql,User.class).setParameter(0, username).getResultList();
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}

	//cookie值中的用户名和密码去数据库查询
	@Override
	public User checkUser(String username, String password) {
		String hql = "from User where username = ? and password = ?";
		List<User> list = this.getSessionFactory().getCurrentSession()
				.createQuery(hql,User.class).setParameter(0, username).setParameter(1, password).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	//通过用户ID查询用户日志
	@Override
	public UserLog findUserLogById(String id) {
		String hql = "from UserLog where user.id = ?";
		List<UserLog> list = this.getSessionFactory().getCurrentSession().createQuery(hql,UserLog.class).setParameter(0, id).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	
}
