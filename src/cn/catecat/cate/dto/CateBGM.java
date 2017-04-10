package cn.catecat.cate.dto;

import java.util.HashSet;
import java.util.Set;

import cn.catecat.category.bean.Category;

public class CateBGM {
	private String id;
	private String name;
	private float marketPrice;
	private int isOnline;
	private String about;
	private Set<Category> categorys=new HashSet<Category>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public Set<Category> getCategorys() {
		return categorys;
	}
	public void setCategorys(Set<Category> categorys) {
		this.categorys = categorys;
	}
	
	
}
