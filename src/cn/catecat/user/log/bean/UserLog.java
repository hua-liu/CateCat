package cn.catecat.user.log.bean;

import java.io.Serializable;

import cn.catecat.user.bean.User;
/**
 * 用户日志模块实体类
 * @author 钟思平
 *
 */
public class UserLog implements Serializable{

	/**
	 * 用户日志记录类
	 * money			用户余额
	 * experience		用户经验
	 * payPassword		用户支付密码
	 * lastLoginTime	最后登陆时间
	 * lastSignTime		最后签到时间
	 * signCount		签到次数
	 * forumExp			今日论坛获取经验
	 * sendCount		今日发帖数
	 * user   			用户
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private float money;
	private Long experience;
	private String lastLoginTime;
	private String lastSignTime;
	private int signCount;
	private int forumExp;
	private int sendCount;
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getExperience() {
		return experience;
	}
	public void setExperience(Long experience) {
		this.experience = experience;
	}
	public int getSignCount() {
		return signCount;
	}
	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}
	public int getForumExp() {
		return forumExp;
	}
	public void setForumExp(int forumExp) {
		this.forumExp = forumExp;
	}
	public int getSendCount() {
		return sendCount;
	}
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastSignTime() {
		return lastSignTime;
	}
	public void setLastSignTime(String lastSignTime) {
		this.lastSignTime = lastSignTime;
	}
	
}
