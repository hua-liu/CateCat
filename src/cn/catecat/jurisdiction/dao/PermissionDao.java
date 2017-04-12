package cn.catecat.jurisdiction.dao;

import java.util.List;

import cn.catecat.jurisdiction.bean.Permission;

public interface PermissionDao{
	public List<Permission> getPermissions();
	public Permission findByName(String name);
	public Permission findById(int name);
	/*List<Permission> myPermission(User user);*/
}
