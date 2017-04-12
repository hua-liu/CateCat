package cn.catecat.jurisdiction.dao;

import java.util.List;

import cn.catecat.jurisdiction.bean.Role;

public interface RoleDao{
	Role getPermissions(Role role);
	public List<Role> getAllRoles();
	public Role findByName(String name);
}
