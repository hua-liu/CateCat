package cn.catecat.jurisdiction.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.global.service.dao.GlobalService;
import cn.catecat.global.util.Conversion;
import cn.catecat.jurisdiction.bean.Permission;
import cn.catecat.jurisdiction.bean.Role;
import cn.catecat.jurisdiction.service.dao.JurisdictionService;
import cn.catecat.user.bean.User;


public class JurisdictionAction implements ModelDriven<DataRequest>{
	@Autowired
	private GlobalService globalservice;
	@Autowired
	private JurisdictionService service;
	private DataRequest dataRequest = new DataRequest();
	private Object list;
	private Role role = new Role();
	private Integer[] permissions;
	private String result;
	private String id;
	private String name;
	private String member;
	private String roleId;
	private int level;
	public void setMember(String member) {
		this.member = member;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setPermissions(Integer[] permissions) {
		this.permissions = permissions;
	}
	public Object getList() {
		return list;
	}
	public void setDataRequest(DataRequest dataRequest) {
		this.dataRequest = dataRequest;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	/**
	 * 返回角色查看视图
	 * @return
	 */
	@cn.catecat.annotation.Jurisdiction("BackgroundLogin")
	public String role(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		ServletActionContext.getRequest().setAttribute("permissions", user.getRole()!=null?user.getRole().getPermissions():null);
		return "role";
	}
	/**
	 * 返回管理员查看视图
	 * @return
	 */
	@cn.catecat.annotation.Jurisdiction("BackgroundLogin")
	public String manager(){
		DataRequest dr = new DataRequest();
		dr.setPage(1);
		dr.setRows(100);
		ServletActionContext.getRequest().setAttribute("roles", globalservice.list(Role.class,dr).getRows());
		return "manager";
	}
	//获取权限分页列表
   @cn.catecat.annotation.Jurisdiction("BackgroundLogin")
	public String listpermission(){
		list = globalservice.list(Permission.class,dataRequest);
		return "all";
	}
	//获取角色分页列表
	@cn.catecat.annotation.Jurisdiction("BackgroundLogin")
	public String listrole(){
		list =  globalservice.list(Role.class,dataRequest);
		return "all";
	}
	//获取管理员分页列表
	@cn.catecat.annotation.Jurisdiction("BackgroundLogin")
	public String listmanager(){
		list =  service.getSpecialUser(dataRequest,true);
		return "all";
	}
	//获取非管理员分页列表
	@cn.catecat.annotation.Jurisdiction("BackgroundLogin")
	public String getNoRoleUsers(){
		list =   service.getSpecialUser(dataRequest,false);
		return "all";
	}
	/**
	 * 添加角色
	 * @param role
	 * @param result
	 * @param session
	 * @param permissions
	 * @return
	 */
	@cn.catecat.annotation.Jurisdiction({"BackgroundLogin","AddRole","UpdateRole"})
	public String addRole(){
		if("1".equals(role.getId()))return Conversion.stringToJson("message,false,cause,无权修改超级管理员");
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		//根据当前角色与选择权限进行赋值
		List<Permission> ps = new ArrayList<Permission>(user.getRole().getPermissions());
		for(int i=0;i<permissions.length;i++){
			for(int j=0;j<ps.size();j++){
				if(ps.get(j).getId()==permissions[i]){
					role.getPermissions().add(ps.get(j));
					break;
				}
			}
		}
		try{
			role.setLevel(level);
			role.setId(id);
			role.setLevel(role.getLevel()<0?0:role.getLevel()+user.getRole().getLevel()+1);
			role.setParentLevel(user.getRole().getLevel());
			globalservice.saveOrUpdate(role);
			this.result = Conversion.stringToJson("message,true");
			return "success";
		}catch(Exception e){
			if(e.getMessage().contains("ConstraintViolationException")){
				this.result =  Conversion.stringToJson("message,false,cause,角色名已经被使用");
				return "success";
			}
			this.result =  Conversion.stringToJson("message,false,cause,未知原因");
		}
		return "success";
	}
	/**
	 * 更新角色
	 * @param member
	 * @param roleId
	 * @param session
	 * @return
	 */
	@cn.catecat.annotation.Jurisdiction({"BackgroundLogin","GiveRole"})
	public String updateRoleForUser(){
		try{
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
			Role role = service.findRoleById(roleId);
			if(role==null)return Conversion.stringToJson("message,false,cause,没有选择的角色");
			if(role.getLevel()<=user.getRole().getLevel()){
				return Conversion.stringToJson("message,false,cause,权限不足");
			}
			String[] members = member.split(",");
			if(members.length<1)return Conversion.stringToJson("message,false,cause,没有可操作的用户");
			for(int i=0;i<members.length;i++){
				members[i] = members[i].trim();
			}
			List<User> list = globalservice.findManyByIds(User.class,members);
			String ids[] = new String[list.size()];int i=0;
			for(User temp : list){
				if(temp.getRole()!=null&&temp.getRole().getLevel()<=user.getRole().getLevel()){
					this.result = Conversion.stringToJson("message,false,cause,不能操作控制级别比您高的用户");
					return "success";
				}
				ids[i++] = temp.getId();
			}
			int result = globalservice.updateManyByFields(User.class, ids, new String[]{"role_id"}, new String[]{roleId});
			this.result =  Conversion.stringToJson("message,true,result,"+result);
		}catch(Exception e){
			this.result =  Conversion.stringToJson("message,false,cause,发生错误");
		}
		return "success";
	}
	/**
	 * 验证角色名是否存在
	 * @param name
	 * @return
	 */
	public String verifyRoleName(){
		Role role = globalservice.getByType(Role.class, "name", name);
		if(role==null){
			this.result = Conversion.stringToJson("message,false");
		}else{
			this.result = Conversion.stringToJson("message,true");
		}
		return "success";
	}
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@cn.catecat.annotation.Jurisdiction({"BackgroundLogin","DeleteRole"})
	public String deleteRole(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(id==null||id!=null&&id.trim().equals(""))return Conversion.stringToJson("message,false");
		try{
			int level = user.getRole().getLevel();
			List<Role> list = globalservice.findManyByIds(Role.class, id.split(","));
			for(Role role : list){
				if(role.getLevel()<=level){
					this.result = Conversion.stringToJson("message,false,cause,权限不足");
				}
			}
			int num  = globalservice.deleteManyByIds(Role.class, id);
			this.result = Conversion.stringToJson("message,true,num,"+num);
			return "success";
		}catch(Exception e){
			if(e.getCause().toString().contains("ConstraintViolationException")){
				this.result =  Conversion.stringToJson("message,false,cause,角色正在使用");
				return "success";
			}
		}
		this.result =  Conversion.stringToJson("message,false,cause,未知原因");
		return "success";
	}
	/**
	 * 撤消用户角色
	 * @param id
	 * @return
	 */
	@cn.catecat.annotation.Jurisdiction({"BackgroundLogin","CancelRole"})
	public String cancelRoleForUser(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(id==null||id!=null&&id.trim().equals(""))return Conversion.stringToJson("message,false,cause,没有要删除的管理员");
		try{
			int level = user.getRole().getLevel();
			List<User> list = globalservice.findManyByIds(User.class, id.split(","));
			String[] ids = new String[list.size()];int i=0;
			for(User temp : list){
				if(temp.getRole()!=null&&temp.getRole().getLevel()<=level){
					this.result = Conversion.stringToJson("message,false,cause,权限不足");
					return "success";
				}
				ids[i++]=temp.getId();
			}
			Integer result = globalservice.updateManyByFields(User.class, ids, new String[]{"role_id"}, new String[]{null});
			this.result =  Conversion.stringToJson("message,true,result,"+result);
		}catch(Exception e){
			e.printStackTrace();
			this.result =  Conversion.stringToJson("message,false,cause,未知原因");
		}
		return "success";
		
	}
	@cn.catecat.annotation.Jurisdiction({"BackgroundLogin"})
	public String admin(){
		return "success";
	}
	@Override
	public DataRequest getModel() {
		return dataRequest;
	}
}
