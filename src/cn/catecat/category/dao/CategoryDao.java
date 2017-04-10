package cn.catecat.category.dao;

import java.util.List;

import cn.catecat.category.bean.Category;
/**
 * 分类Dao接口
 * @author 刘华
 *
 */
public interface CategoryDao {
	/**
	 * 获取要分类
	 * @return
	 */
	List<Category> getRootCategory();
	/**
	 * 根据分类名称获得分类
	 * @param name
	 * @return
	 */
	Category getCategoryByName(String name);
	/**
	 * 根据父名称获取其它
	 * @param string
	 * @return 
	 */
	Category getCategoryByName_other(String string);
	/**
	 * 通过父级获取所有子级
	 * @param parent_id
	 * @return
	 */
	List<Category> getChilds(String parent_id);
}
