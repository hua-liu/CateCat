package cn.catecat.cate.dto;

import java.util.ArrayList;
import java.util.List;

import cn.catecat.cate.bean.Cate;

public class CateDetails extends Cate {

	/**
	 * 用于美食详情页扩展
	 */
	private static final long serialVersionUID = 1L;
	private List<String> categoryName = new ArrayList<String>();	//类别
	private List<String> materialName = new ArrayList<String>();	//食材
	private List<String> tasteName = new ArrayList<String>();	//口味
	private List<String> effectName = new ArrayList<String>();	//功效
	public List<String> getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(List<String> categoryName) {
		this.categoryName = categoryName;
	}
	public List<String> getMaterialName() {
		return materialName;
	}
	public void setMaterialName(List<String> materialName) {
		this.materialName = materialName;
	}
	public List<String> getTasteName() {
		return tasteName;
	}
	public void setTasteName(List<String> tasteName) {
		this.tasteName = tasteName;
	}
	public List<String> getEffectName() {
		return effectName;
	}
	public void setEffectName(List<String> effectName) {
		this.effectName = effectName;
	}
	
}
