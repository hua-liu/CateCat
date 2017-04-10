package cn.catecat.orders.service.dao;

import java.util.List;

import cn.catecat.cate.bean.Cate;
import cn.catecat.orders.bean.OrderItem;
import cn.catecat.orders.bean.Orders;
import cn.catecat.orders.util.PageBean;
import cn.catecat.status.bean.Status;

/**
 * 订单模块Service层接口
 * @author 钟思平
 *
 */
public interface OrdersService {
	
	/**
	 * 添加购物车
	 * @param cart
	 */
	void saveCart(Orders cart);

	/**
	 * 更新购物车
	 * @param cart
	 */
	void updateCart(Orders cart);
	
	/**
	 * 通过用户ID和状态ID查询购物车
	 * @param userId
	 * @param statusId
	 * @return
	 */
	Orders findCartByUserIdStatusId(String userId, String statusId);
	
	/**
	 * 通过订单ID查询订单
	 * @param userId
	 * @param statusId
	 * @param cartId
	 * @return
	 */
	Orders findOrdersByOrdersId(String ordersId);

	/**
	 * 添加购物车，返回新的购物车
	 * @param cart
	 * @param cate
	 * @param count
	 * @return
	 */
	Orders addCarItem(Orders cart, Cate cate, int count);
	
	/**
	 * 删除单个购物项
	 * @param cartItemId
	 */
	void removeCartItemById(String cartItemId);

	/**
	 * 清空购物车
	 * @param cartItem
	 */
	void deleteCart(Orders cart);

	/**
	 * 更改购物项
	 * @param cartItem
	 */
	void updateCartItem(OrderItem cartItem);

	/**
	 * 购物项数量的更改
	 * @param cart
	 * @param cartItem 
	 * @param count
	 * @return
	 */
	Orders revCartItem(Orders cart, OrderItem cartItem, int count);

	/**
	 * 把商品数据转化为购物项  
	 * @param cate
	 * @param count
	 * @return
	 */
    public OrderItem CateToCartItem(Cate cate,int count); 
    
    /**
	 * 通过ID查询购物项
	 * @param cartItemId
	 * @param userId 
	 * @return
	 */
	OrderItem findCartItemById(String cartItemId);

	/**
	 * 计算购物总价格
	 * @param cart
	 * @return
	 */
	float CartTotal(Orders cart);
	
	/**
	 * 通过传一个状态name值查询数据库
	 * @param i
	 * @return
	 */
	Status findByStatus(String Cartstatus);
	/**
	 * 通过传一个状态id值查询数据库
	 * @param status
	 * @return
	 */
	Status findStatusById(Status status);
	
	/*****************************我的订单处理************************************/
	
	/**
	 * 根据用户的id和状态类型type查询订单:
	 * @param id
	 * @param list
	 * @param page
	 * @return
	 */
	PageBean<Orders> findByPageUidStatusType(String userId, List<String> list, int page);

	/**
	 * 通过状态type查询数据库
	 * @param statusType
	 * @return
	 */
	List<String> findByStatusTypeToId(String statusType);

	/**
	 * 更新订单配送地址
	 * @param ordersId
	 * @param userAddress 
	 * @return 
	 */
	void updateAddress(String ordersId, String userAddress);

	/**
	 * 获取订单信息到后台修改订单列表中
	 * @param id
	 * @return
	 */
	Orders getOrders(String id);

	/**
	 * 发货修改订单状态
	 * @param orders
	 */
	void updateOrdersStatus(Orders orders);

	/**
	 * 添加收藏项
	 * @param fav
	 * @param cate
	 * @return
	 */
	Orders addFavItem(Orders fav, Cate cate);

	/**
	 * 根据商品id查询购物项
	 * @param favItemId
	 * @return
	 */
	OrderItem findFavItemById(String favItemId);
	/**
	 * 根据id删除这个收藏
	 * @param favItemId
	 */
	void removeFavItemById(String favItemId);

	
	

	

	

	

	
	

	











}
