package cn.catecat.orders.dao;

import java.util.List;

import cn.catecat.orders.bean.OrderItem;
import cn.catecat.orders.bean.Orders;
import cn.catecat.status.bean.Status;

/**
 * 订单模块Dao层接口
 * @author 钟思平
 *
 */
public interface OrdersDao {

	/**
	 * 修改购物车中的购物项
	 * @param cartItem
	 */
	void updateCartItem(OrderItem cartItem);

	/**
	 * 添加购物车
	 * @param cart
	 */
	void addCart(Orders cart);
	/**
	 * 通过用户ID和状态ID查询购物车
	 * @param userId
	 * @param statusId
	 * @return
	 */
	Orders findCartByUserIdStatusId(String userId, String statusId);
	
	/**
	 * 通过订单ID查询订单
	 * @param cartId
	 * @param userId
	 * @param statusId
	 * @return
	 */
	Orders findOrdersByOrdersId(String ordersId);
	/**
	 * 通过传一个状态name值查询数据库
	 * @param CartStatus
	 * @return
	 */
	Status findByStatus(String CartStatus);
	/**
	 * 通过传一个状态id值查询数据库
	 * @param status
	 * @return
	 */
	Status findBStatuById(Status status);

	/**
	 * 保存购物项
	 * @param cartItem
	 */
	void addCartItem(OrderItem cartItem);
	/**

	/**
	 * 通过ID查询购物项
	 * @param cartItemId
	 * @param userId 
	 * @return
	 */
	OrderItem findCartItemById(String cartItemId);

	/**
	 * 删除单个购物项
	 * @param cartItemId
	 */
	void removeCartItemById(String cartItemId);

	
	/******************************我的订单处理***********************************/
	/**
	 * 通过状态类型type查询数据库
	 * @param statusType
	 * @return
	 */
	List<String> findByStatusTypeToId(String statusType);
	/**
	 * 根据用户的id查询订单数量
	 * @param userId
	 * @return
	 */
	Integer findByCountUidStatusType(String userId);

	/**
	 * 通过用户id和状态类型type查询订单数据
	 * @param userId
	 * @param list
	 * @param begin
	 * @param limit
	 * @return
	 */
	List<Orders> findByPageUidStatusType(String userId, List<String> list, Integer begin, Integer limit);

	/**
	 * 根据id查询收藏
	 * @param favItemId
	 * @return
	 */
	OrderItem findFavItemById(String favItemId);
	
	/**
	 * 根据id删除收藏
	 * @param favItemId
	 */
	void removFavItemById(String favItemId);







}
