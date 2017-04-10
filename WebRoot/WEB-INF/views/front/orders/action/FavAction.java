package cn.catecat.orders.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.service.dao.CateService;
import cn.catecat.global.util.Conversion;
import cn.catecat.orders.bean.OrderItem;
import cn.catecat.orders.bean.Orders;
import cn.catecat.orders.service.dao.OrdersService;
import cn.catecat.status.bean.Status;
import cn.catecat.user.bean.User;

import com.opensymphony.xwork2.ActionSupport;

public class FavAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	// 接收id
	private String cateId;
	private String favItemId;
	// 用于json的接收与返回
	private String result;

	private Cate cate = new Cate();

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public void setFavItemId(String favItemId) {
		this.favItemId = favItemId;
	}

	// 注入OrdersService
	@Autowired
	private OrdersService ordersService;

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	// 注入CateService
	@Autowired
	private CateService cateService;

	public void setCateService(CateService cateService) {
		this.cateService = cateService;
	}

	// 跳转到我的收藏页面
	public String favlist() {
		return SUCCESS;
	}

	// 添加到我的收藏
	public String addFav() {
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		// 判断用户是否登陆
		if (existUser != null) {
			// 获取到此商品信息
			Cate cate = cateService.findCateById(cateId);
			// 判断session中是否拥有收藏
			if (ServletActionContext.getRequest().getSession()
					.getAttribute("fav") == null) {
				// 查询状态
				Status status = ordersService.findByStatus("MYWISH");
				// 查询是否有收藏项，如果没有则创建
				Orders fav = ordersService.findCartByUserIdStatusId(
						existUser.getId(), status.getId());
				if (fav == null) {
					fav = new Orders();
					fav.setUser(existUser);
					status = ordersService.findByStatus("MYWISH");
					fav.setStatus(status);
					ordersService.saveCart(fav);

					ServletActionContext.getRequest().getSession()
							.setAttribute("fav", fav);
				}
				ServletActionContext.getRequest().getSession()
						.setAttribute("fav", fav);
			}
			// 添加进收藏项
			Orders fav = (Orders) ServletActionContext.getRequest()
					.getSession().getAttribute("fav");
			fav = ordersService.addFavItem(fav, cate);
			// 如果没有，则添加，如果有，则报警告，如果未登陆，则报错误
			if (fav != null) {
				ordersService.updateCart(fav);
				
				ServletActionContext.getRequest().getSession()
						.setAttribute("fav", fav);
				this.result = Conversion.stringToJson("result,true");
				return SUCCESS;
			} else {
				this.result = Conversion
						.stringToJson("result,warning,cause,请勿重复添加");
				return SUCCESS;
			}
		} else {
			this.result = Conversion
					.stringToJson("result,false,cause,未登录用户，请去登录!");
			return SUCCESS;
		}
	}

	public String removeFavtItem(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(existUser != null){
			OrderItem favtItem = ordersService.findFavItemById(favItemId);
			if(favtItem != null){
				ordersService.removeFavItemById(favItemId);
				Status status = ordersService.findByStatus("MYWISH");
				Orders fav = ordersService.findCartByUserIdStatusId(existUser.getId(),status.getId());
				ordersService.updateCart(fav);
				ServletActionContext.getRequest().getSession().setAttribute("fav", fav);
				this.result = Conversion.stringToJson("result,true");
				return SUCCESS;
			}else{
				this.result = Conversion.stringToJson("result,false,cause,移除操作失败!");
				return SUCCESS;
			}
		}else{
			this.result = Conversion.stringToJson("result,false,cause,未登录用户，请去登录!");
			return SUCCESS;
		}
	}
}
