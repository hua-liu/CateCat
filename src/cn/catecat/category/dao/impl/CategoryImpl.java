package cn.catecat.category.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.catecat.category.bean.Category;
import cn.catecat.category.dao.CategoryDao;
@Repository
public class CategoryImpl implements CategoryDao {
	@Autowired private SessionFactory sessionFactory;
	@Override
	public List<Category> getRootCategory() {
		return sessionFactory.getCurrentSession().createQuery("from "+Category.class.getName()+" where parent is null order by id",Category.class).getResultList();
	}
	@Override
	public Category getCategoryByName(String name) {
		List<Category> list = sessionFactory.getCurrentSession().createQuery("from "+Category.class.getName()+" where name=:name",Category.class).setParameter("name", name).getResultList();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public Category getCategoryByName_other(String name) {
		List<Category> list = sessionFactory.getCurrentSession().createQuery("from "+Category.class.getName()+" where name='其它' and parent.id=(select id from "+Category.class.getName()+" where name=:name)",Category.class).setParameter("name", name).getResultList();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public List<Category> getChilds(String parent_id) {
		return sessionFactory.getCurrentSession().createQuery("from "+Category.class.getName()+" where parent.id=:pid order by id",Category.class).setParameter("pid", parent_id).getResultList();
	}

}
