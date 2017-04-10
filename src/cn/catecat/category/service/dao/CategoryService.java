package cn.catecat.category.service.dao;

import java.util.List;

import cn.catecat.category.bean.Category;
import cn.catecat.category.dto.CategoryDto;

public interface CategoryService {
	void addCategory(Category cate);
	void removeCategory(String id);
	void updateCategory(Category cate);
	Category getCategory(String id);
	List<Category> getChilds(String parent_id);
	List<CategoryDto> getAllCategory();
}
