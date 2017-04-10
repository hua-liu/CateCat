package cn.catecat.orders.dao.impl;


import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import cn.catecat.orders.bean.OrderItem;
import cn.catecat.orders.bean.Orders;
import cn.catecat.orders.dao.OrdersDao;
import cn.catecat.status.bean.Status;

/**
 * 订单模块Dao层实现类
 * @author 钟思平
 *
 */
@Component
public class OrdersDaoImpl extends HibernateDaoSupport implements OrdersDao{
	//注入SessionFactory
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){  
		super.setSessionFactory(sessionFactory);  
	}

	//修改购物车中的购物项
	@Override
	public void updateCartItem(OrderItem cartItem) {
		this.getSessionFactory().getCurrentSession().update(cartItem);
	}

	//添加购物车
	@Override
	public void addCart(Orders cart) {
		this.getSessionFactory().getCurrentSession().save(cart);
	}

	//通过传一个状态name值查询数据库
	@Override
	public Status findByStatus(String CartStatus) {
		String hql = "from Status where name = ?";
		List<Status> list = this.getSessionFactory().getCurrentSession().createQuery(hql,Status.class).setParameter(0, CartStatus).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	//通过传一个状态id值查询数据库
	@Override
	public Status findBStatuById(Status status) {
		List<Status> list = this.getSessionFactory().getCurrentSession()
				.createQuery("from Status where id = ?",Status.class).setParameter(0, status).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	//保存购物项
	@Override
	public void addCartItem(OrderItem cartItem) {
		this.getSessionFactory().getCurrentSession().save(cartItem);
	}

	//通过用户ID和状态ID查询购物车
	@Override
	public Orders findCartByUserIdStatusId(String userId, String statusId) {
		List<Orders> list = this.getSessionFactory().getCurrentSession()
				.createQuery("from Orders where user.id=? and status.id=?",Orders.class)
				.setParameter(0, userId).setParameter(1, statusId).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	//通过订单ID查询订单
	@Override
	public Orders findOrdersByOrdersId(String ordersId) {
		List<Orders> list = this.getSessionFactory().getCurrentSession()
				.createQuery("from Orders where id=?",Orders.class)
				.setParameter(0, ordersId).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	//通过ID查询购物项
	@Override
	public OrderItem findCartItemById(String cartItemId) {
		List<OrderItem> list = this.getSessionFactory().getCurrentSession()
				.createQuery("from OrderItem where id = ?",OrderItem.class).setParameter(0, cartItemId).getResultList();
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	
	//删除单个购物项
	@Override
	public void removeCartItemById(String cartItemId) {
		String hql = "delete from OrderItem where id = ?";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, cartItemId);
		query.executeUpdate();
	}

	
	/************************我的订单处理****************************/
	
	//通过状态类型type查询数据库
	@Override
	public List<String> findByStatusTypeToId(String statusType) {
		String hql = "select id from Status where type = ?";
		List<String> list = this.getSessionFactory().getCurrentSession().createQuery(hql,String.class)
				.setParameter(0, statusType).getResultList();
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	//根据用户的id查询订单数量
	@Override
	public Integer findByCountUidStatusType(String userId) {
		String hql = "select count(o.id) from Orders as o,Status as s where o.status.id = s.id and o.user.id = ?";
		List<Long> list = this.getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, userId).getResultList();
		if(list !=null && list.size()>0){
			return list.get(0).intValue();
		}
		return null;
	}

	//根据用户的id和状态类型type获取到数据
	@Override
	public List<Orders> findByPageUidStatusType(String userId, List<String> list, Integer begin, Integer limit) {
		String hql = "from Orders as o where o.user.id = ? and o.status.id in(:list) order by createOrdersTime desc";
		List<Orders> listPage = this.getSessionFactory().getCurrentSession().createQuery(hql,Orders.class)
				.setParameter(0, userId).setParameterList("list", list).setFirstResult(begin).setMaxResults(limit).getResultList();
		if(listPage!=null && listPage.size()>0){
			return listPage;
		}
		return null;
	}
	
	//根据id查购物项
	@Override
	public OrderItem findFavItemById(String favItemId) {
		List<OrderItem> list = this.getSessionFactory().getCurrentSession()
				.createQuery("from OrderItem where id = ?",OrderItem.class).setParameter(0, favItemId).getResultList();
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	
	//根据id删除收藏
	@Override
	public void removFavItemById(String favItemId) {
		String hql = "delete from OrderItem where id = ?";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, favItemId);
		query.executeUpdate();
	}

	

	
}
