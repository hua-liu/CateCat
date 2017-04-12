package cn.catecat.user.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Table;

import cn.catecat.image.bean.Image;
import cn.catecat.jurisdiction.bean.Role;
import cn.catecat.user.address.bean.UserAddress;
import cn.catecat.user.log.bean.UserLog;

/**
 * 实体类
 * @author 钟思平
 *
 */
@Table(name="user")
public class User implements Serializable{

	/**
	 * 用户类
	 * id			ID
	 * username		用户名
	 * password		密码
	 * phoneNo		手机号码
	 * email		邮箱地址
	 * name			真实姓名
	 * registerTime	注册时间
	 * question 	安全问题
	 * presentation 自我介绍
	 * payPassword	用户支付密码
	 * hobby		爱好
	 * phoneVerify	手机是否验证
	 * emailVerify	邮箱是否验证
	 * image		用户头像
	 * log			用户日志记录
	 * role			用户当前角色
	 * userAddresses用户地址
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String password;
	private String phoneNo;
	private String email;
	private String name;
	private String registerTime;
	private String question;
	private String presentation;
	private String payPassword;
	private String hobby;
	private int phoneVerify;
	private int emailVerify;
	private Image avatar;
	private UserLog log;
	private Role role;
	//用户存放多个地址的集合
	private Set<UserAddress> userAddresses = new HashSet<UserAddress>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getPresentation() {
		return presentation;
	}
	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}
	public int getPhoneVerify() {
		return phoneVerify;
	}
	public void setPhoneVerify(int phoneVerify) {
		this.phoneVerify = phoneVerify;
	}
	public int getEmailVerify() {
		return emailVerify;
	}
	public void setEmailVerify(int emailVerify) {
		this.emailVerify = emailVerify;
	}
	public UserLog getLog() {
		return log;
	}
	public void setLog(UserLog log) {
		this.log = log;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Set<UserAddress> getUserAddresses() {
		return userAddresses;
	}
	public void setUserAddresses(Set<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}
	public Image getAvatar() {
		return avatar;
	}
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
}
