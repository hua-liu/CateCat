package cn.catecat.category.service.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.catecat.category.bean.Category;
import cn.catecat.category.dao.CategoryDao;
import cn.catecat.category.dto.CategoryDto;
import cn.catecat.category.service.dao.CategoryService;
import cn.catecat.global.dao.GlobalDao;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired private GlobalDao global;
	@Autowired private CategoryDao category;
	@Override
	public void addCategory(Category cate) {
		global.save(cate);
	}
	@Override
	public void removeCategory(String id) {
		global.delete(new Category(id));
	}
	@Override
	public void updateCategory(Category cate) {
		global.update(cate);
	}

	@Override
	public Category getCategory(String id) {
		return global.getById(Category.class, id);
	}
	public List<Category> getChilds(String parent_id){
		return category.getChilds(parent_id);
	}
	@Override
	public List<CategoryDto> getAllCategory() {
		try{
		List<Category> category_list =  category.getRootCategory();
		if(category_list!=null){
			 List<CategoryDto> categoryDto_list = new ArrayList<CategoryDto>();
			 for(Category category : category_list){
				 CategoryDto categoryDto = new CategoryDto();
				BeanUtils.copyProperties(categoryDto, category);
				categoryDto.setChilds(new TreeSet<Category>(getChilds(category.getId())));
				categoryDto_list.add(categoryDto);
			 }
			 return categoryDto_list;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
