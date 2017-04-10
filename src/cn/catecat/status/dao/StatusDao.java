package cn.catecat.status.dao;

import java.util.List;

import cn.catecat.status.bean.Status;

public interface StatusDao {
	List<Object> findType(String keyword,int size);

	List<Status> getStatusByType(String type);
}
