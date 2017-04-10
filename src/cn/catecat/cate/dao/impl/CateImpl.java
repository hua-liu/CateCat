package cn.catecat.cate.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.dao.CateDao;
@Repository
public class CateImpl implements CateDao{
	@Autowired private SessionFactory sessionFactory;

	@Override
	public int updateCateByField(String[] ids,String name, Serializable value) {
		return sessionFactory.getCurrentSession().createNativeQuery("update Cate set "+name+"=:value where id in(:id)").setParameter("value", value).setParameterList("id", ids).executeUpdate();
	}

	@Override
	public List<Cate> getCateByOnOrOff(int type) {
		return sessionFactory.getCurrentSession().createQuery("from Cate where isOnline=:type",Cate.class).setParameter("type", type).getResultList();
	}

	@Override
	public List categoryOfCateNum() {
		return sessionFactory.getCurrentSession().createNativeQuery("select category_id as id,count(cate_id) as num from cate_categorys where cate_id not in(select id from cate where isOnline=0) group by category_id").getResultList();
	}

	@Override
	public List<Cate> getManyByHql(String sql, int size) {
		return sessionFactory.getCurrentSession().createQuery(sql,Cate.class).setMaxResults(size).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findNameByKey(String key,int size) {
		return sessionFactory.getCurrentSession().createNativeQuery("select id,name from cate where name like :name group by name").setParameter("name", "%"+key+"%").setMaxResults(size).getResultList();
	}

	//通过美食ID查询数据库
	@Override
	public Cate findCateById(String cateId) {
		String hql = "from Cate where id=?";
		List<Cate> list = this.sessionFactory.getCurrentSession()
				.createQuery(hql,Cate.class).setParameter(0, cateId).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
}
