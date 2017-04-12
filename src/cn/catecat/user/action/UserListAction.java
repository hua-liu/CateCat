package cn.catecat.user.action;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.annotation.Jurisdiction;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.global.service.dao.GlobalService;
import cn.catecat.global.util.Conversion;
import cn.catecat.user.bean.User;
import cn.catecat.user.log.bean.UserLog;
import cn.catecat.user.service.dao.UserService;

public class UserListAction extends ActionSupport implements ModelDriven<DataRequest>{
	/**
	 *获取分页图数据
	 */
	private static final long serialVersionUID = 1L;
	private DataRequest dataRequest;
	private DataResponse<User> dataResponse;
	@Autowired private GlobalService globalService;
	@Autowired private UserService userService;
	private String id;
	private String result;
	public String getResult() {
		return result;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDataRequest(DataRequest dataRequest) {
		this.dataRequest = dataRequest;
	}
	public DataResponse<User> getDataResponse() {
		return dataResponse;
	}
	/**
	 * 后台默认头像数据列表
	 * @return
	 */
	@Jurisdiction({"BackgroundLogin","SelectUser"})
	public String list(){
		dataResponse = globalService.list(User.class, dataRequest);
		return "list";
	}
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@Jurisdiction({"BackgroundLogin","DeleteUser"})
	public String deleteUser(){
		if(id==null||id!=null&&id.trim().equals(""))return Conversion.stringToJson("message,false");
		try{
			String[] ids = id.split(",");
			for(int i=0;i<ids.length;i++){
				UserLog log = userService.findUserLogById(id);
				if(log!=null)globalService.delete(log);
			}
			int num = globalService.deleteManyByIds(User.class, id);
			this.result =  Conversion.stringToJson("message,true,num,"+num);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
		}
		this.result = Conversion.stringToJson("message,false,cause,未知原因");
		return "success";
	}
	@Override
	public DataRequest getModel() {
		dataRequest = new DataRequest();
		return dataRequest;
	}
}
