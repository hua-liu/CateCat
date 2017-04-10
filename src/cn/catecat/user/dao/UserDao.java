package cn.catecat.user.dao;

import cn.catecat.user.bean.User;
import cn.catecat.user.log.bean.UserLog;

/**
 * 用户模块Dao层接口
 * @author 钟思平
 *
 */
public interface UserDao {

	/**
	 * 后台进行校验
	 * @param type 类型
	 * @param value 值
	 * @return
	 */
	int checkout(String type, String value);

	/**
	 * 用户注册
	 * @param user 用户集合
	 */
	void save(User user);

	/**
	 * 通过用户ID查询用户信息
	 * @param id
	 * @return
	 */
	User findById(String id);

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	User login(String username, String password);

	/**
	 * 通过用户名查询用户
	 * @param username
	 * @return
	 */
	boolean findByName(String username);

	/**
	 * cookie值中的用户名和密码去数据库查询
	 * @param username
	 * @param password
	 * @return
	 */
	User checkUser(String username, String password);

	/**
	 * 通过用户ID查询用户日志
	 * @param id
	 * @return
	 */
	UserLog findUserLogById(String id);
	


		
}
