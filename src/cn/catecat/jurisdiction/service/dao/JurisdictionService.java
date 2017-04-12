package cn.catecat.jurisdiction.service.dao;

import java.util.List;

import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.jurisdiction.bean.Permission;
import cn.catecat.jurisdiction.bean.Role;
import cn.catecat.user.bean.User;

public interface JurisdictionService{
	Role findRoleByName(String name);
	Role findRoleById(String id);
	List<Permission> getPermissions();
	void addRole(Role role);
	Permission findPermissionByName(String name);
	Permission findPermissionById(int id);
	void updateRole(Role role);
	String deleteManyRole(String id);
	DataResponse<User> getSpecialUser(DataRequest request,boolean isManager);
}
