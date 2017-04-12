package cn.catecat.global.dao;

import java.io.Serializable;
import java.util.List;
/**
 * 通用DAO接口，可满足大多数需求
 * @author 刘华
 *
 */
public interface GlobalDao {
	/**
	 * 保存对象
	 * @param t
	 * @return
	 */
	<T> void save(T t);
	/**
	 * 更新对象
	 * @param t
	 * @return
	 */
	<T> void update(T t);
	/**
	 * 删除对象
	 * @param t
	 * @return
	 */
	<T> void delete(T t);
	/**
	 * 通过对象ID查找对象
	 * @param cls
	 * @param id
	 * @return
	 */
	<T> T getById(Class<T> cls,Serializable id);
	/**
	 * 根据条件查找分页数据
	 * @param cls
	 * @param criteria	条件
	 * @param start		开始下标
	 * @param size		查询总数
	 * @param fetch		需要查询为lazy的属性
	 * @return
	 */
	<T> List<T> list(Class<T> cls,String criteria,int start,int size,String ... fetch);
	/**
	 * 根据条件查询结果有多少条
	 * @param cls
	 * @param criteria	条件
	 * @return
	 */
	<T> int count(Class<T> cls,String criteria);
	/**
	 * 通过多个ID删除多条记录
	 * @param cls
	 * @param ids	id数组
	 * @return
	 */
	<T> int deleteManyByIds(Class<T> cls,Object[] ids);
	/**
	 * 根据多个ID查询多个记录
	 * @param cls
	 * @param ids
	 * @param fetch		需要查询为lazy的属性
	 * @return
	 */
	<T> List<T> findManyByIds(Class<T> cls, Object[] ids,String ... fetch);
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	<T> int[] saveOrUpdateMany(List<T> list);
	/**
	 * 添加或更新
	 * @param t
	 */
	<T> void saveOrUpdate(T t);
	/**
	 * 获取全部
	 * @return
	 */
	<T> List<T> getAll(Class<T> cls);
	/**
	 * 根据不同字段查询
	 * @param type
	 * @param value
	 * @return
	 */
	<T> T getByType(Class<T> cls,String type,Serializable value);
	/**
	 * 查询字段并排除其ID
	 * @param class1
	 * @param name
	 * @param id
	 * @return
	 */
	<T> T getByType(Class<T> class1,String name,String value, String id);
	/**
	 * 更新多对象
	 * @param <T>
	 * @param layouts
	 */
	<T> void updateMany(List<T> ts);
	/**
	 * 更新多个对象字段
	 * @param cls
	 * @param criteria
	 * @param ids
	 * @param value
	 * @return
	 */
	<T> Integer updateManyByFields(Class<T> cls,String criteria,String[] ids,Object[] value);
}
