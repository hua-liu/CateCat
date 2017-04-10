package cn.catecat.status.service.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.catecat.status.bean.Status;
import cn.catecat.status.dao.StatusDao;
import cn.catecat.status.service.dao.StatusService;
@Service
public class StatusServiceImpl implements StatusService {
	@Autowired private StatusDao statusDao;
	@Override
	public List<Object> findType(String keyword, int size) {
		return statusDao.findType(keyword, size);
	}
	@Override
	public List<Status> getStatusByType(String type) {
		return statusDao.getStatusByType(type);
	}

}
