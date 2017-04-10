package cn.catecat.cate.bean;

import java.io.Serializable;

import javax.persistence.Table;

import cn.catecat.image.bean.Image;
import cn.catecat.status.bean.Status;
@Table(name="specialshowcate")
public class SpecialShowCate implements Serializable{
	/**
	 * 特殊美食
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Status type;
	private Cate cate;
	private Image img;
	private String decript;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Status getType() {
		return type;
	}
	public void setType(Status type) {
		this.type = type;
	}
	public Cate getCate() {
		return cate;
	}
	public void setCate(Cate cate) {
		this.cate = cate;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public String getDecript() {
		return decript;
	}
	public void setDecript(String decript) {
		this.decript = decript;
	}
	
}
