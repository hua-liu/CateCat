package cn.catecat.image.bean;

import java.io.Serializable;

import javax.persistence.Table;
@Table(name="image")
public class Image implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 图片保存信息类
	 * md5		图片md5
	 * type		保存的类型【GREENS, FORUM,AVATAR
						 菜		论坛	        头像】
	 *path		保存路径
	 */
	private String id;
	private String type;
	private String md5;
	private String path;
	private int isSystem;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public int getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(int isSystem) {
		this.isSystem = isSystem;
	}
	public Image() {
	}
	
	public Image(String id) {
		this.id = id;
	}
	public Image(String type, String md5, String path, int isSystem) {
		this.type = type;
		this.md5 = md5;
		this.path = path;
		this.isSystem = isSystem;
	}
	
}
