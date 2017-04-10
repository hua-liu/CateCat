package cn.catecat.user.service.dao;

import cn.catecat.user.bean.User;
import cn.catecat.user.log.bean.UserLog;

/**
 * 用户模块Service层接口
 * @author 钟思平
 *
 */
public interface UserService {

	/**
	 * 后台进行校验
	 * @param type 类型
	 * @param value 值
	 * @return
	 */
	boolean checkout(String type, String value);

	/**
	 * 用户注册
	 * @param user 值对象
	 * @return 
	 */
	void save(User user);

	/**
	 * 通过ID去查询用户信息
	 * @param id
	 * @return
	 */
	User findById(String id);
	
	/**
	 * 更改用户日志信息
	 * @param existUser
	 */
	void updateUserLog(UserLog userLog);

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	User login(String username, String password);
	
	/**
	 * 修改用户信息
	 * @param sessionUser
	 * @param modelUser
	 * @return
	 */
	User updateUserMessage(User sessionUser, User modelUser);

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
