package cn.catecat.cate.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.annotation.Jurisdiction;
import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.bean.SpecialShowCate;
import cn.catecat.cate.dto.CateDetails;
import cn.catecat.cate.dto.CateSearchRequest;
import cn.catecat.cate.service.dao.CateService;
import cn.catecat.cate.util.Validation;
import cn.catecat.category.bean.Category;
import cn.catecat.category.dto.CategoryDto;
import cn.catecat.category.service.dao.CategoryService;
import cn.catecat.global.service.dao.GlobalService;
import cn.catecat.global.util.Conversion;
import cn.catecat.status.bean.Status;
import cn.catecat.status.service.dao.StatusService;

public class CateAction extends ActionSupport implements ModelDriven<Cate>{
	/**
	 * 美食Action
	 */
	private static final long serialVersionUID = 1L;
	@Autowired private CateService cateService;
	@Autowired private CategoryService categoryService;
	@Autowired private StatusService statusService;
	@Autowired private GlobalService globalService;
	private String result;
	private Cate cate;
	private String key;
	private int type;
	private CateDetails detail;
	private Map<String,String> list;
	private List<Status> status;
	private SpecialShowCate ssCate = new SpecialShowCate();
	private String sidx;
	private String reload;
	public void setReload(String reload) {
		this.reload = reload;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public SpecialShowCate getSsCate() {
		return ssCate;
	}
	public void setSsCate(SpecialShowCate ssCate) {
		this.ssCate = ssCate;
	}
	public List<Status> getStatus() {
		return status;
	}
	public void setStatus(List<Status> status) {
		this.status = status;
	}
	public Map<String, String> getList() {
		return list;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Cate getCate() {
		return cate;
	}
	public String getResult() {
		return result;
	}
	public void setCate(Cate cate) {
		this.cate = cate;
	}
	public CateDetails getDetail() {
		return detail;
	}
	public void setDetail(CateDetails detail) {
		this.detail = detail;
	}
	/**
	 * 添加美食
	 * @return
	 */
	@Jurisdiction({"BackgroundLogin","AddCate","UpdateCate"})
	public String addOrUpdate(){
		String skey = (String) ActionContext.getContext().getSession().get("key");
		try{
			/**
			 * key值匹配
			 */
			if(skey==null||skey!=null&&!skey.equals(key)){
				this.result = Conversion.stringToJson("result,false,type,key,cause,当前提交美食Key不匹配");
			}else{
				/**
				 * 字段验证
				 */
				String error = Validation.valid(cate);
				if(error!=null){
					this.result = Conversion.stringToJson("result,false,cause,"+error);
				}else{//验证通过
					ActionContext.getContext().getSession().remove("key");
					cateService.addOrUpdateCate(cate);
					this.result = Conversion.stringToJson("result,true,id,"+cate.getId());
				}
			}
		}catch(Exception e){
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 删除美食
	 * @return
	 */
	@Jurisdiction({"BackgroundLogin","DeleteCate"})
	public String delete(){
		try{
			int num = cateService.deleteCate(cate.getId());
			this.result = Conversion.stringToJson("result,true,num,"+num);
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 更新美食界面
	 * @return
	 */
	public String updateCateUi(){
		if(cate.getId()!=null){
			this.cate = cateService.getCate(cate.getId());
		}
		return SUCCESS;
	}
	/**
	 * 强制上线与下线
	 * @return
	 */
	@Jurisdiction({"BackgroundLogin","UpdateCate"})
	public String onlineOrOffline(){
		try{
			int num = cateService.onOrOffCate(cate.getId(),type);
			this.result = Conversion.stringToJson("result,true,num,"+num);
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 获得防表单重复提交Key
	 * @return
	 */
	public String getKey(){
		String id = UUID.randomUUID().toString();
		ActionContext.getContext().getSession().put("key", id);
		this.result = Conversion.stringToJson("result,true,id,"+id);
		return SUCCESS;
	}
	/**
	 * 检查名是否已存在
	 * @return	存在返回true，不存在返回false
	 */
	public String checkname(){
		boolean isExit = cateService.checkname(cate.getName(),cate.getId());
		this.result = Conversion.stringToJson("result,"+isExit);
		return SUCCESS;
	}
	/**
	 * 前台分类显示页面
	 * @return
	 */
	
	public String list(){
		@SuppressWarnings("unchecked")
		List<CategoryDto> categorys = (List<CategoryDto>) ActionContext.getContext().getApplication().get("categorys");
		if(categorys==null||categorys!=null&&categorys.size()<1){
			categorys = categoryService.getAllCategory();
			ActionContext.getContext().getApplication().put("categorys", categorys);
		}
		CateSearchRequest request = (CateSearchRequest) ActionContext.getContext().getSession().get("search");
		if(request==null){
			request = new CateSearchRequest();
			request.setPage(1);request.setPriceMax(999);request.setPriceMin(0);
			request.setRows(15);request.setSidx("onLineTime");request.setSort("desc");
			ActionContext.getContext().getSession().put("search", request);
		}
		return SUCCESS;
	}
	/**
	 * 获取各分类下美食的数量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String categoryOfCateNum(){
		list = (Map<String,String>) ActionContext.getContext().getApplication().get("categoryOfCateNum");
		if(list==null||list!=null&&list.size()<1){
			list = cateService.categoryOfCateNum();
			ActionContext.getContext().getApplication().put("categoryOfCateNum", list);
		}
		return "all";
	}
	/**
	 * 从详情细重定向到列表页
	 * @return
	 */
	public String detailToList(){
		if(cate.getCategory()!=null){
			CateSearchRequest request = new CateSearchRequest();
			request.setCategory(cate.getCategory());
			request.setPage(1);request.setPriceMax(999);request.setPriceMin(0);
			request.setRows(15);request.setSidx("onLineTime");request.setSort("desc");
			ActionContext.getContext().getSession().put("search", request);
		}
		if(sidx!=null){
			CateSearchRequest request = new CateSearchRequest();
			request.setPage(1);request.setPriceMax(999);request.setPriceMin(0);
			request.setRows(15);request.setSidx(sidx);request.setSort("desc");
			ActionContext.getContext().getSession().put("search", request);
		}
		return SUCCESS;
	}
	/**
	 * 特殊展示美食管理
	 * @return
	 */
	public String specialShowMange(){
		status = statusService.getStatusByType("HOMESHOW");
		return SUCCESS;
	}
	//根据关键字查询名字
	public String findNameByKey(){
		list = cateService.findNameByKey(key);
		return "all";
	}
	/**
	 * 添加与更新特殊展示美食
	 * @return
	 */
	@Jurisdiction({"BackgroundLogin","UpdateCate"})
	public String updateSpecial(){
		try{
			globalService.saveOrUpdate(ssCate);
			this.result = Conversion.stringToJson("result,true,id,"+ssCate.getId());
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 删除特殊展示美食
	 * @return
	 */
	
	@Jurisdiction({"BackgroundLogin","DeleteCate"})
	public String deleteSpecial(){
		try{
			int num = cateService.deleteSpecialCate(ssCate.getId());
			this.result = Conversion.stringToJson("result,true,num,"+num);
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 美食详细描述
	 * @return
	 */
	public String details(){
		@SuppressWarnings("unchecked")
		List<CategoryDto> categorys = (List<CategoryDto>) ActionContext.getContext().getApplication().get("categorys");
		if(categorys==null||categorys!=null&&categorys.size()<1){
			categorys = categoryService.getAllCategory();
			ActionContext.getContext().getApplication().put("categorys", categorys);
		}
		if(cate!=null&&cate.getId()!=null){
			cate = cateService.getCate(cate.getId());
			if(cate!=null){
				String searchCategory = "";
				//当前美食操作
				detail = new CateDetails();
				BeanUtils.copyProperties(cate, detail);
				if(detail.getCategorys()!=null)
				for(Category cateCategroy : detail.getCategorys()){
					if(categorys!=null)
					for(CategoryDto cDto : categorys){
						if(cDto.getChilds().contains(cateCategroy)){
							if("类别".equals(cDto.getName())){
								detail.getCategoryName().add(cateCategroy.getName());
								searchCategory += cateCategroy.getId()+",";
							}else if("主成".equals(cDto.getName())){
								detail.getMaterialName().add(cateCategroy.getName());
								searchCategory += cateCategroy.getId()+",";
							}else if("口味".equals(cDto.getName())){
								detail.getTasteName().add(cateCategroy.getName());
								searchCategory += cateCategroy.getId()+",";
							}else if("功效".equals(cDto.getName())){
								detail.getEffectName().add(cateCategroy.getName());
								searchCategory += cateCategroy.getId()+",";
							}
						}
					}
				}
				//查询推荐
				List<Cate> recommend = cateService.getManyByCategoryIds(searchCategory,5);
				ServletActionContext.getRequest().setAttribute("recommend", recommend);
			}
			
		}
		return SUCCESS;
	}
	/**
	 * 主页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String index(){
		List<CategoryDto> categorys = (List<CategoryDto>) ActionContext.getContext().getApplication().get("categorys");
		if(categorys==null||categorys!=null&&categorys.size()<1||reload!=null){
			categorys = categoryService.getAllCategory();
			ActionContext.getContext().getApplication().put("categorys", categorys);
		}
		//加载主页特殊展示
		List<SpecialShowCate> cates = (List<SpecialShowCate>) ActionContext.getContext().getApplication().get("specialCate");
		if(cates==null||cates!=null&&cates.size()<1||reload!=null){
			cates = cateService.getSpecialCates();
			ActionContext.getContext().getApplication().put("specialCate", cates);
		}
		//加载倒计时商品（倒计时一天，每天0点重置商品）
		Cate countdownCate = (Cate) ActionContext.getContext().getApplication().get("countdownCate");
		Date countdownCateDate = (Date) ActionContext.getContext().getApplication().get("countdownCateDate");
		SimpleDateFormat df = new SimpleDateFormat("dd");
		String currentDate = df.format(new Date());
		String countdownDate =null;
		if(countdownCateDate!=null) countdownDate = df.format(countdownCateDate);
		if(countdownCate==null||!currentDate.equals(countdownDate)||reload!=null){
			List<Cate> list = cateService.getRandCate(1);
			if(list!=null&&list.size()>0)countdownCate = list.get(0);
			ActionContext.getContext().getApplication().put("countdownCate", countdownCate);
			ActionContext.getContext().getApplication().put("countdownCateDate", new Date());
		}
		//畅销商品(每小时更新一次)
		List<Cate> activeCate = (List<Cate>) ActionContext.getContext().getApplication().get("activeCate");
		Date cateDate = (Date) ActionContext.getContext().getApplication().get("cateDate");
		df = new SimpleDateFormat("dd:hh");
		currentDate = df.format(new Date());
		String updateDate =null;
		if(cateDate!=null) updateDate = df.format(cateDate);
		if(activeCate==null||activeCate!=null&&activeCate.size()<1||!currentDate.equals(updateDate)||reload!=null){
			activeCate = cateService.getActiveCate(8);
			ActionContext.getContext().getApplication().put("activeCate", activeCate);
			ActionContext.getContext().getApplication().put("cateDate", new Date());
		}
		//最新商品(每小时更新一次)
		List<Cate> newCate = (List<Cate>) ActionContext.getContext().getApplication().get("newCate");
		if(newCate==null||activeCate!=null&&activeCate.size()<1||!currentDate.equals(updateDate)||reload!=null){
			newCate = cateService.getNewCate(2);
			ActionContext.getContext().getApplication().put("newCate", newCate);
			ActionContext.getContext().getApplication().put("cateDate", new Date());
		}
		//本店推荐随机8个(每小时更新一次)
		List<Cate> recCate = (List<Cate>) ActionContext.getContext().getApplication().get("recCate");
		if(recCate==null||recCate!=null&&recCate.size()<1||!currentDate.equals(updateDate)||reload!=null){
			recCate = cateService.getRandCate(8);
			ActionContext.getContext().getApplication().put("recCate", recCate);
			ActionContext.getContext().getApplication().put("cateDate", new Date());
		}
		return SUCCESS;
	}
	@Override
	public Cate getModel() {
		cate = new Cate();
		return cate;
	}

}
