package cn.catecat.category.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.annotation.Jurisdiction;
import cn.catecat.category.bean.Category;
import cn.catecat.category.dto.CategoryDto;
import cn.catecat.category.service.dao.CategoryService;
import cn.catecat.global.util.Conversion;
public class CategoryAction extends ActionSupport implements RequestAware,ModelDriven<Category>{

	/**
	 * 美食分类Action
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CategoryService categoryService;
	private Map<String,Object> request;
	private String result;
	private List<CategoryDto> list;
	private Category category;
	public List<CategoryDto> getList() {
		return list;
	}
	public String getResult() {
		return result;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	/**
	 * 获取所有分类
	 * @return
	 */
	public String category(){
		List<CategoryDto> list = categoryService.getAllCategory();
		ActionContext.getContext().getApplication().put("categorys", list);
		request.put("categorys",list);
		return SUCCESS;
	}
	/**
	 * 获取所有分类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String all(){
		list = (List<CategoryDto>)ActionContext.getContext().getApplication().get("categorys");
		if(list==null||list!=null&&list.size()<1){
			list = categoryService.getAllCategory();
			ActionContext.getContext().getApplication().put("categorys", list);
		}
		return "all";
	}
	/**
	 * 添加分类
	 */
	@Jurisdiction({"BackgroundLogin","AddCategory"})
	public String add(){
		try{
			category.setId(System.currentTimeMillis()+"");
			categoryService.addCategory(category);
			this.result = Conversion.stringToJson("result,true,id,"+category.getId());
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 删除分类
	 */
	@Jurisdiction({"BackgroundLogin","DeleteCategory"})
	public String delete(){
		try{
			if(category.getId()==null){
				this.result = Conversion.stringToJson("result,false,cause,没有获取到分类ID");
				return SUCCESS;
			}
			categoryService.removeCategory(category.getId());
			this.result = Conversion.stringToJson("result,true,id,"+category.getId());
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 更新分类
	 */
	@Jurisdiction({"BackgroundLogin","UpdateCategory"})
	public String update(){
		try{
			categoryService.updateCategory(category);
			this.result = Conversion.stringToJson("result,true,id,"+category.getId());
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	@Override
	public Category getModel() {
		category = new Category();
		return category;
	}
	
}
