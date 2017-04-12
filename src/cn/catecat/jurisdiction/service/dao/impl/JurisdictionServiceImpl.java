package cn.catecat.jurisdiction.service.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.catecat.global.dao.GlobalDao;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.global.util.Conversion;
import cn.catecat.jurisdiction.bean.Permission;
import cn.catecat.jurisdiction.bean.Role;
import cn.catecat.jurisdiction.dao.PermissionDao;
import cn.catecat.jurisdiction.dao.RoleDao;
import cn.catecat.jurisdiction.service.dao.JurisdictionService;
import cn.catecat.user.bean.User;
@Service
public class JurisdictionServiceImpl implements JurisdictionService{
	@Autowired
	private GlobalDao globalDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;
	@Override
	public Role findRoleByName(String name) {
		return roleDao.findByName(name);
	}
	@Override
	public List<Permission> getPermissions() {
		return permissionDao.getPermissions();
	}
	@Override
	public Role findRoleById(String id) {
		return globalDao.getById(Role.class, id);
	}
	@Override
	public void addRole(Role role) {
		globalDao.save(role);
	}
	@Override
	public Permission findPermissionByName(String name) {
		return permissionDao.findByName(name);
	}
	@Override
	public Permission findPermissionById(int id) {
		return permissionDao.findById(id);
	}
	@Override
	public void updateRole(Role role) {
		globalDao.update(role);
	}
	@Override
	public String deleteManyRole(String ids) {
		String[] arr=null;
		if(ids!=null)arr = ids.split(",");
		int num = globalDao.deleteManyByIds(Role.class, arr);
		return Conversion.stringToJson("message,true,num,"+num);
	}
	@Override
	public DataResponse<User> getSpecialUser(DataRequest request,boolean isManager) {
		DataResponse<User> response = new DataResponse<User>();  
        int count;//总记录数  
        int size = request.getRows() <= 0 ? 20 : request.getRows();//每页显示数量  
        int totalPages;//总页数  
        int page = request.getPage() <= 0 ? 1 : request.getPage();//当前显示页码  
        List<User> list;
        String criteria = Conversion.initSearchCondition(request);
        String countCriteria = criteria.equals("")?" where role_id"+(isManager?" is not ":" is ")+"null":criteria+" and role_id"+(isManager?" is not ":" is ")+"null";
        criteria = criteria.equals("")?" where role"+(isManager?"!=":"=")+"null":criteria+" and role"+(isManager?"!=":"=")+"null";
        count = globalDao.count(User.class,countCriteria);  
        totalPages = count / size;  
        if (count % size != 0) {  
            totalPages++;  
        }  
        int currPage = Math.min(totalPages, page);  
        int start = currPage * size - size;  
        start = start < 0 ? 0 : start;  
        
        list = globalDao.list(User.class, criteria+Conversion.sortCriteria(request.getSidx(), request.getSord()), start, size); 
        response.setRecords(count);  
        response.setTotal(totalPages);  
        response.setPage(currPage);  
        response.setRows(list);  
        return response;  
	}
}

