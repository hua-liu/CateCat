package cn.catecat.user.action;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.global.service.dao.GlobalService;
import cn.catecat.global.util.Conversion;
import cn.catecat.image.service.dao.ImageService;
import cn.catecat.orders.bean.Orders;
import cn.catecat.orders.service.dao.OrdersService;
import cn.catecat.orders.util.DateTool;
import cn.catecat.status.bean.Status;
import cn.catecat.user.bean.User;
import cn.catecat.user.log.bean.UserLog;
import cn.catecat.user.service.dao.UserService;
import cn.catecat.user.userUtil.Encryption;

/**
 * 用户模块Action类
 * @author 钟思平
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{
	private static final long serialVersionUID = 1L;
	@Autowired private ImageService imageService;
	@Autowired private GlobalService globalService;
	
	//模型驱动使用的对象
	private User user = new User();
	private String type;
	private String value;
	private String result;
	private String autologin;
	public void setAutologin(String autologin) {
		this.autologin = autologin;
	}
	public String getResult() {
		return result;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public User getModel() {
		return user;
	}
	//注入Service
	@Autowired
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	private OrdersService ordersService;
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	//跳转到用户注册界面
	public String registPage(){
		return "registPage";
	}
	
	//后台进行校验
	public String checkoutExist(){
		//调用Service进行查询
		boolean isExist = userService.checkout(type,value);
		this.result = Conversion.stringToJson("result,"+isExist);
		return SUCCESS;
	}
	
	//用户注册
	public String regist(){
		if(userService.findByName(user.getUsername())){
			userService.save(user);
			//保存用户信息到Session中
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
			return "registSuccess";
		}else{
			ServletActionContext.getRequest().setAttribute("error", "该用户已被注册!");
			return "registPage";
		}
	}
	
	//用户添加注册信息
	public String registMessage(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		existUser = userService.updateUserMessage(existUser, user);
		if(existUser!=null){
			ServletActionContext.getRequest().getSession().setAttribute("user", existUser);
			return "indexPage";
		}else{
			return "login";
		}
	}
	
	
	//跳转到用户登录界面
	public String loginPage(){
		return "loginPage";
	}
	
	//用户登录
	public String login(){
		HttpServletResponse response = ServletActionContext.getResponse();
		User existUser = userService.login(user.getUsername(), user.getPassword());
		if(existUser == null){
			//用户不存在
			ServletActionContext.getRequest().setAttribute("error", "用户名或密码错误");
			return "loginPage";
		}else{
			//将用户放到session中
			ServletActionContext.getRequest().getSession().setAttribute("user", existUser);
	        //如果useCookie不为空
	        if(autologin != null){
	            //新建一个Cookie，存放用户名和密码，用&做分隔符
	            Cookie cookie = new Cookie("autologin", user.getUsername()+"&"+Encryption.encryption(user.getPassword()));
	            //设置Cookie的存活时间（这里是两周）
	            cookie.setMaxAge(2*7*24*60*60);
	            //设置Cookie的路径
	            cookie.setPath("/");
	            //添加Cookie
	            response.addCookie(cookie);
	        }
	        if(ServletActionContext.getRequest().getSession().getAttribute("cart") == null){
	        	Status status = ordersService.findByStatus("CART");
	        	Orders cart = null;
	        	if(status!=null)
	        		cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
	        	if(cart==null){
	        		cart = new Orders();
	        		globalService.saveOrUpdate(cart);
	        	}
	        	ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
			}
	        if(ServletActionContext.getRequest().getSession().getAttribute("fav") == null){
	        	Status status = ordersService.findByStatus("MYWISH");
	        	Orders fav = null;
	        	if(status!=null)
	        		fav = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
	        	if(fav==null){
	        		fav = new Orders();
	        		globalService.saveOrUpdate(fav);
	        	}
	        	ServletActionContext.getRequest().getSession().setAttribute("cart", fav);
			}
	        if(null==existUser.getAvatar()){	//判断为没有初始化头像，让其去继续补充资料
	        	return "registSuccess";
	        }
	        //this.result = Conversion.stringToJson("result,true");
	        //保存用户最后登录时间
	        UserLog userLog = userService.findUserLogById(existUser.getId());
	        if(userLog==null)userLog = new UserLog();
	        userLog.setLastLoginTime(DateTool.dateUtil(System.currentTimeMillis()));
	        globalService.saveOrUpdate(userLog);
	        //this.result = Conversion.stringToJson("result,true");
	        return "indexPage";
		}
	}
	//用户登录
	public String loginAdmin(){
		HttpServletResponse response = ServletActionContext.getResponse();
		User existUser = userService.login(user.getUsername(), user.getPassword());
		if(existUser == null){
			//用户不存在
			ServletActionContext.getRequest().setAttribute("error", "用户名或密码错误");
			return "login";
		}else{
			//将用户放到session中
			ServletActionContext.getRequest().getSession().setAttribute("user", existUser);
			//如果useCookie不为空
			if(autologin != null){
				//新建一个Cookie，存放用户名和密码，用&做分隔符
				Cookie cookie = new Cookie("autologin", user.getUsername()+"&"+Encryption.encryption(user.getPassword()));
				//设置Cookie的存活时间（这里是两周）
				cookie.setMaxAge(2*7*24*60*60);
				//设置Cookie的路径
				cookie.setPath("/");
				//添加Cookie
				response.addCookie(cookie);
			}
			if(ServletActionContext.getRequest().getSession().getAttribute("cart") == null){
				Status status = ordersService.findByStatus("CART");
				Orders cart = null;
				if(status!=null)
					cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
				if(cart==null){
					cart = new Orders();
					globalService.saveOrUpdate(cart);
				}
				ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
			}
			//保存用户最后登录时间
			UserLog userLog = userService.findUserLogById(existUser.getId());
			if(userLog==null)userLog = new UserLog();
			userLog.setLastLoginTime(DateTool.dateUtil(System.currentTimeMillis()));
			globalService.saveOrUpdate(userLog);
			//this.result = Conversion.stringToJson("result,true");
			return "admin";
		}
	}
	public String registSuccess(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser==null)return "login";
		ServletActionContext.getRequest().setAttribute("defaultHeadPortrait", imageService.getDefaultHeadPortrait());
		return "success";
	}
	//用户退出
	public String logoutAdmin(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Cookie cookies[] = request.getCookies();  
		if (cookies != null){  
			for (int i = 0; i < cookies.length; i++){  
				if (cookies[i].getName().equals("autologin")){  
					Cookie cookie = new Cookie("autologin","");//这边得用"",不能用null  
					cookie.setPath("/");//设置成跟写入cookies一样的  
					cookie.setMaxAge(0);  
					response.addCookie(cookie);  
				}  
			}  
		}
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	//用户退出
	public String quit(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Cookie cookies[] = request.getCookies();  
        if (cookies != null){  
        	for (int i = 0; i < cookies.length; i++){  
        		if (cookies[i].getName().equals("autologin")){  
        			Cookie cookie = new Cookie("autologin","");//这边得用"",不能用null  
        			cookie.setPath("/");//设置成跟写入cookies一样的  
        			cookie.setMaxAge(0);  
                 	response.addCookie(cookie);  
        		}  
         	}  
        }
        ServletActionContext.getRequest().getSession().invalidate();
        return "quit";
	}
}
