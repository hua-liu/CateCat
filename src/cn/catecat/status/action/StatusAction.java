package cn.catecat.status.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.annotation.Jurisdiction;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.global.service.dao.GlobalService;
import cn.catecat.global.util.Conversion;
import cn.catecat.status.bean.Status;
import cn.catecat.status.service.dao.StatusService;

public class StatusAction extends ActionSupport implements ModelDriven<DataRequest>{

	/**
	 * 状态处理
	 */
	private static final long serialVersionUID = 1L;
	private Status status = new Status();
	private DataRequest dataRequest;
	private DataResponse<Status> dataResponse;
	private String result;
	private String key;
	@Autowired private StatusService statusService;
	private List<Object> list;
	
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public DataResponse<Status> getDataResponse() {
		return dataResponse;
	}
	public void setDataResponse(DataResponse<Status> dataResponse) {
		this.dataResponse = dataResponse;
	}
	public DataRequest getDataRequest() {
		return dataRequest;
	}
	public void setDataRequest(DataRequest dataRequest) {
		this.dataRequest = dataRequest;
	}
	public String getResult() {
		return result;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Autowired private GlobalService globalService;
	@Jurisdiction({"BackgroundLogin","SelectState","StateManage"})
	public String delete(){
		try{
			if(status.getId()==null){
				this.result = Conversion.stringToJson("result,false,cause,没有要删除数据ID");return SUCCESS;
			}
			int num = globalService.deleteManyByIds(Status.class, status.getId());
			this.result = Conversion.stringToJson("result,true,num,"+num);
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	@Jurisdiction({"BackgroundLogin","SelectState"})
	public String list(){
		dataResponse = globalService.list(Status.class, dataRequest);
		return "list";
	}
	@Jurisdiction({"BackgroundLogin","SelectState","StateManage"})
	public String update(){
		try{
			if(status.getId()==null||status.getId()!=null&&status.getId().trim().equals("")){
				status.setId(System.currentTimeMillis()+"");
				globalService.save(status);
			}
			else globalService.update(status);
			this.result = Conversion.stringToJson("result,true");
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	public String findType(){
		list = statusService.findType(key,7);
		return "all";
	}
	@Override
	public DataRequest getModel() {
		dataRequest = new DataRequest();
		return dataRequest;
	}
}
