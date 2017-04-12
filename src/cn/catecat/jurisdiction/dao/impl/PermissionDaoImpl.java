package cn.catecat.jurisdiction.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import cn.catecat.jurisdiction.bean.Permission;
import cn.catecat.jurisdiction.dao.PermissionDao;
@Component
public class PermissionDaoImpl implements PermissionDao{
	private HibernateTemplate hibernateTemplate;

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> getPermissions() {
		return (List<Permission>) hibernateTemplate.find("from Permission");
	}
	@Override
	public Permission findByName(String name) {
		List<?> list = hibernateTemplate.find("from Permission where name='"+name+"'");
		if(list!=null&&list.size()>0){
			return (Permission) list.get(0);
		}
		return null;
	}
	@Override
	public Permission findById(int id) {
		return hibernateTemplate.get(Permission.class, id);
	}
	/*public List<Permission> myPermission(User user){
		hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("from Permission")
		return null;
	}*/
}
