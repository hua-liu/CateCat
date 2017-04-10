package cn.catecat.category.dto;

import java.util.Set;
import java.util.TreeSet;

import cn.catecat.category.bean.Category;

public class CategoryDto extends Category {
	/**
	 * 分类扩展
	 */
	private static final long serialVersionUID = 1L;
	private Set<Category> childs = new TreeSet<Category>();
	public Set<Category> getChilds() {
		return childs;
	}
	public void setChilds(Set<Category> childs) {
		this.childs = childs;
	}
	
}
