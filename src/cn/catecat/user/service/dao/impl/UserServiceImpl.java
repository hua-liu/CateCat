package cn.catecat.user.service.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.catecat.global.dao.GlobalDao;
import cn.catecat.orders.util.DateTool;
import cn.catecat.user.bean.User;
import cn.catecat.user.dao.UserDao;
import cn.catecat.user.log.bean.UserLog;
import cn.catecat.user.service.dao.UserService;
import cn.catecat.user.userUtil.Encryption;

/**
 * 用户模块Service层实现类
 * @author 钟思平
 *
 */
@Component
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao	userDao;
	@Autowired private GlobalDao globalDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	//Service层后台进行校验操作
	@Override
	public boolean checkout(String type,String value) {
		int count = userDao.checkout(type,value);
		return count>0?true:false;
	}

	//用户注册
	@Override
	public void save(User user) {
		//保存用户注册时间
		user.setRegisterTime(DateTool.dateUtil(System.currentTimeMillis()));
		UserLog userLog = new UserLog();
		
		//保存用户最后登录时间
		userLog.setLastLoginTime(DateTool.dateUtil(System.currentTimeMillis()));
		//注册用户即可获得的金额(模拟用)
		userLog.setMoney(1000000);
		userLog.setUser(user);
		//保存用户登录时间
		user.setLog(userLog);
		user.setPassword(Encryption.encryption(user.getPassword()));
		userDao.save(user);
	}

	//通过用户ID查询用户信息
	@Override
	public User findById(String id) {
		return userDao.findById(id);
	}
	
	//更改用户日志信息
	@Override
	public void updateUserLog(UserLog userLog) {
		globalDao.update(userLog);
	}

	//用户登录
	@Override
	public User login(String username, String password) {
		password = Encryption.encryption(password);
		return userDao.login(username,password);
	}

	//修改用户信息
	@Override
	public User updateUserMessage(User sessionUser, User modelUser) {
		if(sessionUser==null){
			return null;
		}
		User user = userDao.findById(sessionUser.getId());
		if(null!=modelUser.getPhoneNo()&&!"".equals(modelUser.getPhoneNo()))
			user.setPhoneNo(modelUser.getPhoneNo());
		if(null!=modelUser.getEmail()&&!"".equals(modelUser.getEmail()))
			user.setEmail(modelUser.getEmail());
		if(null!=modelUser.getQuestion()&&!"".equals(modelUser.getQuestion()))
			user.setQuestion(modelUser.getQuestion());
		if(null!=modelUser.getName()&&!"".equals(modelUser.getName()))
			user.setName(modelUser.getName());
		if(null!=modelUser.getPresentation()&&!"".equals(modelUser.getPresentation()))
			user.setPresentation(modelUser.getPresentation());
		if(null!=modelUser.getAvatar()&&!"".equals(modelUser.getAvatar().getId()))
			user.setAvatar(modelUser.getAvatar());
		if(null!=modelUser.getPayPassword()&&!"".equals(modelUser.getPayPassword()))
			user.setPayPassword(Encryption.encryption(modelUser.getPayPassword()));
		return user;
	}
	
	//通过用户名查询用户
	@Override
	public boolean findByName(String username) {
		if(userDao.findByName(username)){
			return false;
		}
		return true;
	}


	//cookie值中的用户名和密码去数据库查询
	@Override
	public User checkUser(String username, String password) {
		return userDao.checkUser(username, password);
	}
	
	//通过用户ID查询用户日志
	@Override
	public UserLog findUserLogById(String id) {
		return userDao.findUserLogById(id);
	}

	

	



}
