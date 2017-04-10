package cn.catecat.cate.dao;

import java.io.Serializable;
import java.util.List;

import cn.catecat.cate.bean.Cate;

public interface CateDao {
	/**
	 * 更新美食部分字段
	 * @param ids
	 * @param name
	 * @param value
	 * @return
	 */
	int updateCateByField(String[] ids, String name, Serializable value);
	/**
	 * 查询所有上线或下线
	 * @param type
	 * @return
	 */
	List<Cate> getCateByOnOrOff(int type);
	/**
	 * 查询每个分类美食数量
	 * @return
	 */
	List<Object> categoryOfCateNum();
	/**
	 * 随机查询
	 * @param ids 如果没有id,则全部随机
	 * @param i
	 * @return
	 */
	List<Cate> getManyByHql(String sql, int i);
	/**
	 * 根据key查询名称
	 * @param key
	 * @return
	 */
	List<Object[]> findNameByKey(String key,int size);
	/**
	 * 通过美食ID查询数据库
	 * @param cateId
	 * @return
	 */
	Cate findCateById(String cateId);
}
