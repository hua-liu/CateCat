package cn.catecat.global.service.dao;

import java.io.Serializable;
import java.util.List;

import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;

public interface GlobalService {
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
	<T> DataResponse<T> list(Class<T> cls,DataRequest dataRequest);
	/**
	 * 通过多个ID删除多条记录
	 * @param class1
	 * @param ids	id数组
	 * @return
	 */
	<T> int deleteManyByIds(Class<T> class1,String ids);
	<T> int deleteManyByIds(Class<T> cls, Object[] id);
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
	/***
	 * 更新多个对象
	 * @param layouts
	 */
	<T> void updateMany(List<T> ts);
	/**
	 * 更新字段
	 * @param class1
	 * @param ids
	 * @param name
	 * @param value
	 * @return
	 */
	<T> Integer updateManyByFields(Class<T> class1,String[] ids,String[] name,Object[] value);
}
