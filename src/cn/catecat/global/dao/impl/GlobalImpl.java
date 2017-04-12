package cn.catecat.global.dao.impl;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.catecat.global.dao.GlobalDao;
@Repository
public class GlobalImpl implements GlobalDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public <T> void save(T t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public <T> void update(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	@Override
	public <T> void delete(T t) {
		sessionFactory.getCurrentSession().delete(t);
	}

	@Override
	public <T> T getById(Class<T> cls, Serializable id) {
		return sessionFactory.getCurrentSession().get(cls, id);
	}
	
	@Override
	public <T> List<T> list(Class<T> cls, String criteria, int start, int size,String ... fetch) {
		String join_fetch ="";
		if(fetch!=null&&fetch.length>0){join_fetch +=" left outer join fetch t."+fetch[0];}
		return sessionFactory.getCurrentSession().createQuery("from "+cls.getName()+" as t "+join_fetch+" "+criteria,cls)
				.setFirstResult(start).setMaxResults(size).getResultList();
	}

	@Override
	public <T> int count(Class<T> cls, String criteria) {
		Table table = cls.getAnnotation(Table.class);
		Object obj = sessionFactory.getCurrentSession().createNativeQuery("select count(*) from "+table.name()+" as t "+criteria).getSingleResult();
		return Integer.parseInt(obj.toString());
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> int deleteManyByIds(Class<T> cls, Object[] ids) {
		return sessionFactory.getCurrentSession().createQuery("delete from "+cls.getName()+" where id in(:id)").setParameterList("id", ids).executeUpdate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> List<T> findManyByIds(Class<T> cls, Object[] ids,String ... fetch) {
		String join_fetch ="";
		if(fetch!=null&&fetch.length>0){join_fetch +=" left outer join fetch t."+fetch[0];}
		return sessionFactory.getCurrentSession().createQuery("select distinct t from "+cls.getName()+" as t "+join_fetch+" where t.id in(:id)",cls).setParameterList("id", ids).getResultList();
	}

	@Override
	public <T> int[] saveOrUpdateMany(List<T> list) {
		int[] num=new int[list.size()];
		Session session = sessionFactory.getCurrentSession();
		int i=0;
		try{
			for(T t : list){
				session.saveOrUpdate(t);
				num[i++]=1;
			}
			session.flush();
		}catch(Exception e){
			num[i++]=0;
		}
		return num;
	}

	@Override
	public <T> List<T> getAll(Class<T> cls) {
		return sessionFactory.getCurrentSession().createQuery("from "+ cls.getName(),cls).getResultList();
	}

	@Override
	public <T> T getByType(Class<T> cls,String type, Serializable value) {
		List<T> list = sessionFactory.getCurrentSession().createQuery("from "+ cls.getName()+" where "+type+"=:value",cls).setParameter("value", value).getResultList();
		if(list!=null&&list.size()>0)return list.get(0);
		return null;
	}

	@Override
	public <T> T getByType(Class<T> cls, String name, String value, String id) {
		List<T> list = sessionFactory.getCurrentSession().createQuery("from "+ cls.getName()+" where "+name+"=:value and id!=:id",cls).setParameter("value", value).setParameter("id", id).getResultList();
		if(list!=null&&list.size()>0)return list.get(0);
		return null;
	}

	@Override
	public <T> void saveOrUpdate(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public <T> void updateMany(List<T> ts) {
		for(T t : ts){
			sessionFactory.getCurrentSession().update(t);
		}
		sessionFactory.getCurrentSession().flush();
	}
	@Override
	public <T> Integer updateManyByFields(Class<T> cls,String criteria,String[] ids,Object[] value) {
		Table table = cls.getAnnotation(Table.class);
		NativeQuery nq = sessionFactory.getCurrentSession().createNativeQuery("update "+table.name()+" set "+criteria+" where id in(:id)");
		for(int i=0;i<value.length;i++){
			nq.setParameter(++i, value[--i]);
		}
		return nq.setParameterList("id", ids).executeUpdate();
	}
}
