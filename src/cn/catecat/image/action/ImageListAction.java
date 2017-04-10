package cn.catecat.image.action;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.image.bean.Image;
import cn.catecat.image.service.dao.ImageService;

public class ImageListAction extends ActionSupport implements ModelDriven<DataRequest>{
	/**
	 *获取分页图数据
	 */
	private static final long serialVersionUID = 1L;
	private DataRequest dataRequest;
	private DataResponse<Image> dataResponse;
	@Autowired private ImageService imageService;
	public void setDataRequest(DataRequest dataRequest) {
		this.dataRequest = dataRequest;
	}
	public DataResponse<Image> getDataResponse() {
		return dataResponse;
	}
	/**
	 * 后台默认头像数据列表
	 * @return
	 */
	public String defaultHeadPortrait(){
		dataRequest.setSearch(true);
		dataRequest.setSearchField(new String[]{"type","isSystem"});
		dataRequest.setSearchOper(new String[]{"eq","eq"});
		dataRequest.setSearchString(new String[]{"AVATAR","1"});
		dataResponse = imageService.defaultList(dataRequest);
		return "list";
	}

	@Override
	public DataRequest getModel() {
		dataRequest = new DataRequest();
		return dataRequest;
	}
}
