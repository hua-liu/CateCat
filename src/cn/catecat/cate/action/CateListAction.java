package cn.catecat.cate.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.annotation.Jurisdiction;
import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.bean.SpecialShowCate;
import cn.catecat.cate.dto.CateSearchRequest;
import cn.catecat.cate.service.dao.CateService;
import cn.catecat.category.bean.Category;
import cn.catecat.category.dto.CategoryDto;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.global.service.dao.GlobalService;

public class CateListAction extends ActionSupport implements ModelDriven<DataRequest>{
	/**
	 *获取分布美食数据
	 */
	private static final long serialVersionUID = 1L;
	private DataRequest dataRequest;
	private DataResponse<Cate> dataResponse;
	private DataResponse<SpecialShowCate> specialResponse;
	private CateSearchRequest cateRequest=new CateSearchRequest();
	@Autowired private CateService cateService;
	@Autowired private GlobalService globalService;
	public DataResponse<SpecialShowCate> getSpecialResponse() {
		return specialResponse;
	}
	public void setSpecialResponse(DataResponse<SpecialShowCate> specialResponse) {
		this.specialResponse = specialResponse;
	}
	public void setDataRequest(DataRequest dataRequest) {
		this.dataRequest = dataRequest;
	}
	public DataRequest getDataRequest() {
		return dataRequest;
	}
	public DataResponse<Cate> getDataResponse() {
		return dataResponse;
	}
	public CateSearchRequest getCateRequest() {
		return cateRequest;
	}
	public void setCateRequest(CateSearchRequest cateRequest) {
		this.cateRequest = cateRequest;
	}
	/**
	 * 后台美食数据列表
	 * @return
	 */
	@Jurisdiction({"BackgroundLogin"})
	public String bgm(){
		dataResponse = cateService.list_bgm(dataRequest);
		return "list";
	}
	public String frontSearchList(){
		@SuppressWarnings("unchecked")
		List<CategoryDto> categorys = (List<CategoryDto>) ActionContext.getContext().getApplication().get("categorys");
		List<Category> kind = null;
		for(CategoryDto cateDto : categorys){
			if(cateDto.getName().equals("类别")){
				kind = new ArrayList<Category>(cateDto.getChilds());
			}
		}
		dataResponse = cateService.searchList(cateRequest,kind);
		ActionContext.getContext().getSession().put("search", cateRequest);
		return "list";
	}
	public String specialShow(){
		specialResponse = globalService.list(SpecialShowCate.class, dataRequest);
		return "specialResponse";
	}
	@Override
	public DataRequest getModel() {
		dataRequest = new DataRequest();
		return dataRequest;
	}
}
