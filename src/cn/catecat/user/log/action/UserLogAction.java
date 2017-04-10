package cn.catecat.user.log.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.user.log.bean.UserLog;
import cn.catecat.user.log.service.dao.UserLogService;

/**
 * 用户日志模块Action类
 * @author 钟思平
 *
 */
public class UserLogAction extends ActionSupport implements ModelDriven<UserLog>{
	private static final long serialVersionUID = 1L;
	//模型驱动使用的对象
	private UserLog log;
	@Override
	public UserLog getModel() {
		return log;
	}
	//注入UserLogService
	@Autowired
	private UserLogService userLogService;
	public void setUserLogService(UserLogService userLogService) {
		this.userLogService = userLogService;
	}
}
