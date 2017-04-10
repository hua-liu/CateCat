package cn.catecat.user.log.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.catecat.user.log.dao.UserLogDao;
import cn.catecat.user.log.service.dao.UserLogService;

/**
 * 用户日志模块Service层实现类
 * @author 钟思平
 *
 */
@Component
public class UserLogServiceImpl implements UserLogService{
	//注入UserLogDao
	@Autowired
	private UserLogDao userLogDao;
	public void setUserLogDao(UserLogDao userLogDao) {
		this.userLogDao = userLogDao;
	}
}
