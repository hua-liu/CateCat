package cn.catecat.orders.action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.service.dao.CateService;
import cn.catecat.global.util.Conversion;
import cn.catecat.orders.bean.OrderItem;
import cn.catecat.orders.bean.Orders;
import cn.catecat.orders.service.dao.OrdersService;
import cn.catecat.status.bean.Status;
import cn.catecat.user.bean.User;

/**
 * 购物车Action 
 */
public class CartAction extends ActionSupport implements ModelDriven<Orders>{
	private static final long serialVersionUID = 1L;
	//模型驱动使用的对象
	private Orders orders = new Orders();
	//接收id      
	private String cateId;
	//接收购物车ID
	private String cartId;
	//接收购物项ID
	private String cartItemId;
	//接收数量
	private int count;
	//接收是否为加减
	private boolean change;
	
	private String result;
	
	@Override
	public Orders getModel() {
		return orders;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}
	
	//注入OrdersService
	@Autowired
	private OrdersService ordersService;
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	//注入CateService
	@Autowired
	private CateService cateService;
	public void setCateService(CateService cateService) {
		this.cateService = cateService;
	}
	//购物车修改商品信息存入session中
	public String changeCarItem(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			Orders cart = (Orders) ServletActionContext.getRequest().getSession().getAttribute("cart");
			if(cart == null){
				Status status = ordersService.findByStatus("CART");
				cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
				ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
			}
			OrderItem cartItem = ordersService.findCartItemById(cartItemId);
			cart = ordersService.revCartItem(cart,cartItem,count);  
			cart.setTotal(ordersService.CartTotal(cart));
	        ordersService.updateCart(cart);
	        ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
			this.result = Conversion.stringToJson("result,true");
			return SUCCESS;
		}else{
			this.result = Conversion.stringToJson("result,false,cause,未登录用户，请去登录!");
			return SUCCESS;
		}
	}
	//跳转购物车页面判断用户是否存在
	public String cartPage(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser!=null){
			Orders cart = (Orders) ServletActionContext.getRequest().getSession().getAttribute("cart");
			if(cart == null){
				Status status = ordersService.findByStatus("CART");
				cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
				ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
			}
			this.result = Conversion.stringToJson("result,true");
			return SUCCESS;
			
		}else{
			this.result = Conversion.stringToJson("result,false,cause,未登录用户，请去登录!");
			return SUCCESS;
		}
	}
	//跳转购物车页面
	public String cartToPage(){
		ServletActionContext.getRequest().getSession().getAttribute("cart");
		return "cartPage";
	}
	//将购物项添加到购物车中
	public String addCart(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			//根据美食ID获取相应的美食数据
			Cate cate = cateService.findCateById(cateId);
			//判断当前session中是否有购物车，如果没有则创建
			if(ServletActionContext.getRequest().getSession().getAttribute("cart") == null){
				Status status = ordersService.findByStatus("CART"); 
				Orders cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
				if(cart==null) {
					cart = new Orders();
					cart.setUser(existUser);
					status = ordersService.findByStatus("CART");
					cart.setStatus(status);
					ordersService.saveCart(cart);
				}
				//创建新的购物车，存储到Session中
				ServletActionContext.getRequest()
				.getSession().setAttribute("cart", cart);
			}
			
			// 把商品信息转化为OrderItem,并且添加到购物车中（判断购物项是否重复）  
			Orders cart = (Orders) ServletActionContext.getRequest().getSession().getAttribute("cart");
			
	        cart = ordersService.addCarItem(cart,cate,count);  
	        cart.setTotal(ordersService.CartTotal(cart));
	        ordersService.updateCart(cart);
	        //把新的购物车存储到session中  
	        ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
	        this.result = Conversion.stringToJson("result,true");
	        return SUCCESS;
		}else{
			this.result = Conversion.stringToJson("result,false,cause,未登录用户，请去登录!");
			return SUCCESS;
		}
	}
	
	//移除单个购物项
	public String removeCartItem(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			OrderItem cartItem = ordersService.findCartItemById(cartItemId);
			if(cartItem != null){
				ordersService.removeCartItemById(cartItemId);
				Status status = ordersService.findByStatus("CART");
				Orders cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
				cart.setCartItemCount(cart.getCartItemCount()-1);
				cart.setTotal(ordersService.CartTotal(cart));
				ordersService.updateCart(cart);
				ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
				this.result = Conversion.stringToJson("result,true");
				return SUCCESS;
			}else{
				this.result = Conversion.stringToJson("result,false,cause,删除操作失败!");
				return SUCCESS;
			}
		}else{
			this.result = Conversion.stringToJson("result,false,cause,未登录用户，请去登录!");
			return SUCCESS;
		}
		
	}
	
	//清空购物车
	public String removeAllCartItem(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser!=null){
			Status status = ordersService.findByStatus("CART");
			Orders cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
			if(cart != null){
				ordersService.deleteCart(cart);
				cart = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
				if(cart==null) {
					cart = new Orders();
					cart.setUser(existUser);
					status = ordersService.findByStatus("CART");
					cart.setStatus(status);
					ordersService.saveCart(cart);
				}
				ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
				this.result = Conversion.stringToJson("result,true");
				return SUCCESS;
			}else{
				this.result = Conversion.stringToJson("result,false,cause,清空操作失败!");
				return SUCCESS;
			}
		}else{
			this.result = Conversion.stringToJson("result,false,cause,未登录用户，请去登录!");
			return SUCCESS;
		}
	}
	
}
