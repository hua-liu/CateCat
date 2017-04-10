package cn.catecat.cate.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import cn.catecat.cate.log.bean.CateLog;
import cn.catecat.category.bean.Category;
import cn.catecat.image.bean.Image;
@Table(name="cate")
public class Cate implements Serializable {

	/**
	 * 美食类
	 * name		菜名
	 * price	价格
	 * isOnline	是否上线
	 * type		属于什么菜系
	 * material	材料
	 * modus	做法
	 * decription 描述
	 * imgs		菜品图片
	 * log		菜品日志记录
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private float shopPrice;
	private float marketPrice;
	private int isOnline;
	private Date onLineTime;
	private String about;
	private String introduce;
	private Set<Category> categorys=new HashSet<Category>();
	private String category;
	private Set<Image> images=new HashSet<Image>();
	private CateLog log;
	public Cate(){}
	public Cate(String id) {
		this.id = id;
	}
	public Cate(String id, String name, float marketPrice, int isOnline, String about) {
		this.id = id;
		this.name = name;
		this.marketPrice = marketPrice;
		this.isOnline = isOnline;
		this.about = about;
	}
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
	public float getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(float shopPrice) {
		this.shopPrice = shopPrice;
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
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	@JSON(serialize=false)
	public Set<Category> getCategorys() {
		return categorys;
	}
	public void setCategorys(Set<Category> categorys) {
		this.categorys = categorys;
	}
	public CateLog getLog() {
		return log;
	}
	public void setLog(CateLog log) {
		this.log = log;
	}
	@JSON(serialize=false)
	public Set<Image> getImages() {
		return images;
	}
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getOnLineTime() {
		return onLineTime;
	}
	public void setOnLineTime(Date onLineTime) {
		this.onLineTime = onLineTime;
	}
}
