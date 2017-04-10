package cn.catecat.cate.log.bean;

import java.io.Serializable;

import javax.persistence.Table;
@Table(name="catelog")
public class CateLog implements Serializable{
	/**
	 * 美食日志记录
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private long viewCount;
	private long buyCount;
	private String grade;
	public CateLog(){}
	public CateLog(long viewCount, long buyCount, String grade) {
		this.viewCount = viewCount;
		this.buyCount = buyCount;
		this.grade = grade;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getViewCount() {
		return viewCount;
	}
	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}
	public long getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(long buyCount) {
		this.buyCount = buyCount;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
