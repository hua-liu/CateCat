package cn.catecat.orders.action;



import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.annotation.Jurisdiction;
import cn.catecat.global.util.Conversion;
import cn.catecat.orders.bean.Orders;
import cn.catecat.orders.service.dao.OrdersService;
import cn.catecat.orders.util.DateTool;
import cn.catecat.status.bean.Status;
import cn.catecat.user.bean.User;
import cn.catecat.user.log.bean.UserLog;
import cn.catecat.user.service.dao.UserService;
import cn.catecat.user.userUtil.Encryption;

/**
 * 订单模块Action类
 * @author 钟思平
 *
 */
public class OrdersAction extends ActionSupport implements ModelDriven<Orders>{
	private static final long serialVersionUID = 1L;
	//模型驱动使用的对象
	private Orders orders = new Orders();
	//接收支付密码
	private String payPassword;
	//接收购物项ID
	private String cartItemId;
	//接收订单ID
	private String ordersId;
	//接收用户配送地址
	private String userAddress;
	//接收数量
	private int count;
	//接收result
	private String result;
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}
	@Override
	public Orders getModel() {
		return orders;
	}
	@Autowired
	private OrdersService ordersService;
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	@Autowired
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	//跳转到确认订单页面判断是否存在用户
	public String ordersPage(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			Orders cart = ordersService.findOrdersByOrdersId(ordersId);
			Status status = ordersService.findByStatus("NOPAID");
			cart.setCreateOrdersTime(DateTool.dateUtil(System.currentTimeMillis()));
			cart.setStatus(status);
			ordersService.updateCart(cart);
			status = ordersService.findByStatus("CART");
			cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
			ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
			status = ordersService.findByStatus("NOPAID");
			Orders orders = ordersService.findOrdersByOrdersId(ordersId);
			ServletActionContext.getRequest().getSession().setAttribute("orders", orders);
			this.result = Conversion.stringToJson("result,true");
			return SUCCESS;
		}else{
			this.result = Conversion.stringToJson("result,nologin,cause,未登录用户，请去登录！");
			return SUCCESS;
		}
	}
	
	//跳转确认订单页面
	public String ordersToPage(){
		return "ordersPage";
	}
	
	//取消订单
	public String ordersToCart(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			Orders cart = ordersService.findOrdersByOrdersId(ordersId);
			ordersService.deleteCart(cart);
			Status status = ordersService.findByStatus("CART");
			cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
			ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
			this.result = Conversion.stringToJson("result,true");
			return SUCCESS;
		}else{
			this.result = Conversion.stringToJson("result,nologin,cause,未登录用户，请去登录！");
			return SUCCESS;
		}
	}
	
	//确认订单验证支付密码正确
	public String confirmPayPassword(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			if(userAddress.equals("") || userAddress.equals(" ")){
				this.result = Conversion.stringToJson("result,false,cause,密码错误或者地址为空，清注意查看！");
				return SUCCESS;
			}else{
				if(existUser.getPayPassword().equals(Encryption.encryption(payPassword))){
					Orders cart = ordersService.findOrdersByOrdersId(ordersId);
					Status status = ordersService.findByStatus("PAID");
					UserLog userLog = userService.findUserLogById(existUser.getId());
					userLog.setMoney(userLog.getMoney()-cart.getTotal());
					cart.setStatus(status);
					cart.setAddress(userAddress);
					cart.setCreateOrdersTime(DateTool.dateUtil(System.currentTimeMillis()));
					userService.updateUserLog(userLog);
					ordersService.updateCart(cart);
					this.result = Conversion.stringToJson("result,true");
					return SUCCESS;
				}else{
					this.result = Conversion.stringToJson("result,false,cause,密码错误或者地址为空，清注意查看！");
					return SUCCESS;
				}
			}
		}else{
			this.result = Conversion.stringToJson("result,nologin,cause,未登录用户，请去登录！");
			return SUCCESS;
		}
	}
	
	//我的订单付款验证支付密码是否正确
	public String myOrdersConfirmPayPassword(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			if(existUser.getPayPassword().equals(Encryption.encryption(payPassword))){
				Orders cart = ordersService.findOrdersByOrdersId(ordersId);
				Status status = ordersService.findByStatus("PAID");
				UserLog userLog = userService.findUserLogById(existUser.getId());
				userLog.setMoney(userLog.getMoney()-cart.getTotal());
				cart.setStatus(status);
				cart.setCreateOrdersTime(DateTool.dateUtil(System.currentTimeMillis()));
				userService.updateUserLog(userLog);
				ordersService.updateCart(cart);
				this.result = Conversion.stringToJson("result,true");
				return SUCCESS;
			}else{
				this.result = Conversion.stringToJson("result,false,cause,密码错误，清注意验证！");
				return SUCCESS;
			}
		}else{
			this.result = Conversion.stringToJson("result,nologin,cause,未登录用户，请去登录！");
			return SUCCESS;
		}
	}
	
	//获取订单信息到后台修改配送地址
	public String updateOrdersAddress(){
		if(ordersId!=null){
			Orders ordersManage = ordersService.getOrders(ordersId);
			ServletActionContext.getRequest().getSession().setAttribute("ordersManage", ordersManage);
		}
		return SUCCESS;
	}

	//更新订单配送地址
	public String updateOrdersAddressUi(){
		if(ordersId!=null){
			ordersService.updateAddress(ordersId,userAddress);
			this.result = Conversion.stringToJson("result,true");
			return SUCCESS;
		}else{
			this.result = Conversion.stringToJson("result,false,cause,无法获取订单ID,修改失败！");
			return SUCCESS;
		}
		
	}
	
	//后台发货处理
	@Jurisdiction({"BackgroundLogin","SelectOrder","OrderManage"})
	public String backstageDeliverOrders(){
		if(ordersId != null){
			Orders orders = ordersService.findOrdersByOrdersId(ordersId);
			Status NOPAID = ordersService.findByStatus("NOPAID");//未付款
			Status PAID = ordersService.findByStatus("PAID");//已付款
			Status NOSEND = ordersService.findByStatus("NOSEND");//未发货
			Status SEND = ordersService.findByStatus("SEND");//已发货
			Status NORECEIVE = ordersService.findByStatus("NORECEIVE");//未确认收货
			Status RECEIVE = ordersService.findByStatus("RECEIVE");//已确认收货
			if(orders.getStatus().getId().equals(NOPAID.getId())){
				this.result = Conversion.stringToJson("result,NOPAID,cause,未付款的订单!");
				return SUCCESS;
			}else if(orders.getStatus().getId().equals(NORECEIVE.getId())){
				this.result = Conversion.stringToJson("result,NORECEIVE,cause,未确认收货的订单!");
				return SUCCESS;
			}else if(orders.getStatus().getId().equals(RECEIVE.getId())){
				this.result = Conversion.stringToJson("result,RECEIVE,cause,确认收货的订单!");
				return SUCCESS;
			}else{
				orders.setStatus(SEND);
				ordersService.updateOrdersStatus(orders);
				this.result = Conversion.stringToJson("result,true");
				return SUCCESS;
			}
		}else{
			this.result = Conversion.stringToJson("result,false,cause,无效的订单ID!");
			return SUCCESS;
		}
	}
	


}
