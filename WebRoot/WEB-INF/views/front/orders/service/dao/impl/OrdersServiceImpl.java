package cn.catecat.orders.service.dao.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.catecat.cate.bean.Cate;
import cn.catecat.global.dao.GlobalDao;
import cn.catecat.orders.bean.OrderItem;
import cn.catecat.orders.bean.Orders;
import cn.catecat.orders.dao.OrdersDao;
import cn.catecat.orders.service.dao.OrdersService;
import cn.catecat.orders.util.PageBean;
import cn.catecat.status.bean.Status;

/**
 * 订单模块Service层实现类
 * @author 钟思平
 *
 */
@Component
public class OrdersServiceImpl implements OrdersService{
	@Autowired
	private OrdersDao ordersDao;
	@Autowired private GlobalDao globalDao;
	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	
	//添加购物车
	@Override
	public void saveCart(Orders cart) {
		globalDao.save(cart);
	}
	
	//更新购物车
	@Override
	public void updateCart(Orders cart) {
		globalDao.update(cart);
	}
	
	//清空购物车
	@Override
	public void deleteCart(Orders cart) {
		globalDao.delete(cart);
	}
	
	//通过用户ID和状态ID查询购物车
	@Override
	public Orders findCartByUserIdStatusId(String userId, String statusId) {
		return ordersDao.findCartByUserIdStatusId(userId,statusId);
	}
	/**
	 * 通过订单ID查询订单
	 */
	@Override
	public Orders findOrdersByOrdersId(String ordersId) {
		return ordersDao.findOrdersByOrdersId(ordersId);
	}
	
	//添加购物车，返回新的购物车
	@Override
	public Orders addCarItem(Orders cart, Cate cate,int count) {
		//boolean isCartItem = false;//用来标记是否有重复的购物项
		//拿到当前的购物项
		OrderItem cartItem = CateToCartItem(cate,count);
		//判断当前购物项是否重复，如果重复，则添加数量
		for(OrderItem old : cart.getOrderItems()){
			//购物项有重复，添加数量
			if(old.getCate().getId().equals(cartItem.getCate().getId())){
				old.setCount(old.getCount()+cartItem.getCount());
				old.setSubtotal(old.getSubtotal()+cartItem.getSubtotal());
				ordersDao.updateCartItem(old);
				return cart;
			}
		}
		//当前购物项在购物车中不存在，新添加
		cartItem.setOrders(cart);
		ordersDao.addCartItem(cartItem);
		cart.setCartItemCount(cart.getCartItemCount()+1);
		cart.getOrderItems().add(cartItem);
		return cart;
	}
	
	//更改购物项
	@Override
	public void updateCartItem(OrderItem cartItem) {
		ordersDao.updateCartItem(cartItem);
	}

	//删除单个购物项
	@Override
	public void removeCartItemById(String cartItemId) {
		ordersDao.removeCartItemById(cartItemId);
	}
	
	//通过ID查询购物项
	@Override
	public OrderItem findCartItemById(String cartItemId) {
		return ordersDao.findCartItemById(cartItemId);
	}
	
	//购物项数量的更改
	@Override
	public Orders revCartItem(Orders cart,OrderItem cartItem,int count) {
		for(OrderItem old : cart.getOrderItems()){
			if(old.getCate().getId().equals(cartItem.getCate().getId())){
				old.setCount(count);
				old.setSubtotal(old.getPrice()*count);
				ordersDao.updateCartItem(old);
				return cart;
			}
		}
		return cart;
	}
	
	//将美食添加到购物项中
	@Override
	public OrderItem CateToCartItem(Cate cate,int count) {
		OrderItem cartItem = new OrderItem();
		cartItem.setId(cate.getId());
		cartItem.setName(cate.getName());
		cartItem.setCount(count);
		cartItem.setCate(cate);
		cartItem.setPrice(cate.getMarketPrice());
		cartItem.setSubtotal(cartItem.getCount()*cartItem.getPrice());
		return cartItem;
	}
	

	//计算购物总价格
	@Override
	public float CartTotal(Orders cart) {
		float total = 0;
		for (OrderItem cartItem : cart.getOrderItems()) {
			total += cartItem.getCount()*cartItem.getPrice();
		}
		return total;
	}
	
	//通过传一个状态name值查询数据库
	@Override
	public Status findByStatus(String CartStatus) {
		return ordersDao.findByStatus(CartStatus);
	}
	
	//通过传一个状态id值查询数据库
	@Override
	public Status findStatusById(Status status) {
		return ordersDao.findBStatuById(status);
	}
	
	/*********************************我的订单处理***********************************************/
	
	//通过状态类型type查询数据库
	@Override
	public List<String> findByStatusTypeToId(String statusType) {
		return ordersDao.findByStatusTypeToId(statusType);
	}
	
	//根据用户的id和状态类型type查询订单
	@Override
	public PageBean<Orders> findByPageUidStatusType(String userId, List<String> list, int page) {
		PageBean<Orders> pageBean = new PageBean<Orders>();
		//设置当前页数
		pageBean.setPage(page);
		//设置每页显示的记录数
		Integer limit = 3;
		pageBean.setLimit(limit);
		//设置总记录数
		Integer totalCount = null;
		totalCount = ordersDao.findByCountUidStatusType(userId);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		Integer totalPage = null;
		if(totalCount % limit == 0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		//设置每页显示数据集合
		Integer begin = (page-1)*limit;
		List<Orders> listPage = ordersDao.findByPageUidStatusType(userId,list,begin,limit);
		pageBean.setList(listPage);
		return pageBean;
	}


	//获取订单信息到后台修改配送地址
	@Override
	public Orders getOrders(String id) {
		Orders orders = globalDao.getById(Orders.class, id);
		return orders;
	}

	//更新订单配送地址
	@Override
	public void updateAddress(String ordersId, String userAddress) {
		Orders orders = ordersDao.findOrdersByOrdersId(ordersId);
		orders.setAddress(userAddress);
		globalDao.update(orders);
	}

	//发货修改订单状态
	@Override
	public void updateOrdersStatus(Orders orders) {
		globalDao.update(orders);
	}

	//添加收藏项
	@Override
	public Orders addFavItem(Orders fav, Cate cate) {
		OrderItem favItem = CateToCartItem(cate,1);
		for(OrderItem old : fav.getOrderItems()){
			if(old.getCate().getId().equals(favItem.getCate().getId())){
				return null;
			}
		}
		favItem.setOrders(fav);
		ordersDao.addCartItem(favItem);
		fav.setCartItemCount(fav.getCartItemCount()+1);
		fav.getOrderItems().add(favItem);
		return fav;
	}

	//根据id查询收藏
	@Override
	public OrderItem findFavItemById(String favItemId) {
		return ordersDao.findFavItemById(favItemId);
	}

	//根据id删除收藏
	@Override
	public void removeFavItemById(String favItemId) {
		ordersDao.removFavItemById(favItemId);
	}

		
	
}
