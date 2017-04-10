package cn.catecat.status.service.dao;

import java.util.List;

import cn.catecat.status.bean.Status;

public interface StatusService {
	/**
	 * 根据关键字查找
	 * @param keyword
	 * @param size
	 * @return
	 */
	List<Object> findType(String keyword,int size);
	/**
	 * 根据类弄获取所有状态
	 * @param type
	 * @return
	 */
	List<Status> getStatusByType(String type);
}
