package cn.catecat.status.bean;

import java.io.Serializable;

import javax.persistence.Table;
@Table(name="status")
public class Status implements Serializable {

	/**
	 * 保存状态
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private String name;
	private String decription;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDecription() {
		return decription;
	}
	public void setDecription(String decription) {
		this.decription = decription;
	}
	
}
