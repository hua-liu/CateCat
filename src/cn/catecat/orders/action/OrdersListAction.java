package cn.catecat.orders.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.cate.dto.CateSearchRequest;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.util.Conversion;
import cn.catecat.orders.bean.Orders;
import cn.catecat.orders.service.dao.OrdersService;
import cn.catecat.orders.util.DateTool;
import cn.catecat.orders.util.PageBean;
import cn.catecat.status.bean.Status;
import cn.catecat.user.bean.User;
import cn.catecat.user.service.dao.UserService;
import cn.catecat.user.userUtil.Encryption;

/**
 * 我的订单模块Action类
 * @author 钟思平
 *
 */
public class OrdersListAction extends ActionSupport implements ModelDriven<Orders>{
	private static final long serialVersionUID = 1L;
	//模型驱动使用的对象
	private Orders orders = new Orders();
	//接收支付密码
	private String payPassword;
	//接收购物项ID
	private String cartItemId;
	//接收订单ID
	private String ordersId;
	//接收数量
	private int count;
	//接收page
	private int page;
	//接收result
	private String result;
	private DataRequest dataRequest;
	public void setDataRequest(DataRequest dataRequest) {
		this.dataRequest = dataRequest;
	}
	public DataRequest getDataRequest() {
		return dataRequest;
	}
	private PageBean<Orders> pageBean;
	public void setPageBean(PageBean<Orders> pageBean) {
		this.pageBean = pageBean;
	}
	public PageBean<Orders> getPageBean() {
		return pageBean;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public void setPage(int page) {
		this.page = page;
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
	//我的订单详情列表判断是否存在用户
	public String myOrdersPage(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			// 根据用户的id和状态类型type查询订单:
			String statusType = "ORDERS";
			List<String> list = (List<String>) ordersService.findByStatusTypeToId(statusType);
			PageBean<Orders> pageBean = ordersService.findByPageUidStatusType(existUser.getId(),list,page);
			//将分页数据显示到页面上：
			ActionContext.getContext().getValueStack().set("pageBean", pageBean);
			this.result = Conversion.stringToJson("result,true");
			return SUCCESS;
		}else{
			this.result = Conversion.stringToJson("result,false,cause,未登录用户，请去登录！");
			return SUCCESS;
		}
	}
	
	//跳转我的订单页面
	public String myOrdersToPage(){
		return "myOrdersPage";
	}
	
	//确认收货修改状态
	public String Confirmreceipt(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			if(existUser.getPayPassword().equals(Encryption.encryption(payPassword))){
				Orders cart = ordersService.findOrdersByOrdersId(ordersId);
				Status status = ordersService.findByStatus("RECEIVE");
				cart.setStatus(status);
				cart.setReceiveTime(DateTool.dateUtil(System.currentTimeMillis()));
				ordersService.updateCart(cart);
				this.result = Conversion.stringToJson("result,true");
				return SUCCESS;
			}else{
				this.result = Conversion.stringToJson("result,false,cause,支付密码错误，请重新输入！");
				return SUCCESS;
			}
		}else{
			this.result = Conversion.stringToJson("result,nologin,cause,未登录用户，请去登录！");
			return SUCCESS;
		}
	}
	
	//后台订单管理列表
	public String ordersManage(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		// 根据用户的id和状态类型type查询订单:
		String statusType = "ORDERS";
		List<String> list = (List<String>) ordersService.findByStatusTypeToId(statusType);
		this.pageBean = ordersService.findByPageUidStatusType(existUser.getId(),list,page);
		return "ordersManagePageBean";
	}
	
}
