package cn.catecat.status.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.catecat.status.bean.Status;
import cn.catecat.status.dao.StatusDao;
@Repository
public class StatusImpl implements StatusDao{
	@Autowired private SessionFactory sessionFactory;
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findType(String keyword,int size) {
		return sessionFactory.getCurrentSession().createNativeQuery("select type from status where type like :keyword group by type").setParameter("keyword", "%"+keyword+"%").setMaxResults(size).getResultList();
	}
	@Override
	public List<Status> getStatusByType(String type) {
		return sessionFactory.getCurrentSession().createQuery("from "+Status.class.getName()+" where type=:type",Status.class).setParameter("type", type).getResultList();
	}

}
