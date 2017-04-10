package cn.catecat.cate.dto;

public class ScanForm {
	private String socketid;
	private String path;
	private int level;
	private int norm;
	private String norm_infor;
	private String norm_img;
	private String norm_property;
	private Float price_discount;
	private String norm_category;
	public String getSocketid() {
		return socketid;
	}
	public void setSocketid(String socketid) {
		this.socketid = socketid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getNorm_infor() {
		return norm_infor;
	}
	public void setNorm_infor(String norm_infor) {
		this.norm_infor = norm_infor;
	}
	public String getNorm_img() {
		return norm_img;
	}
	public void setNorm_img(String norm_img) {
		this.norm_img = norm_img;
	}
	public String getNorm_property() {
		return norm_property;
	}
	public void setNorm_property(String norm_property) {
		this.norm_property = norm_property;
	}
	public int getNorm() {
		return norm;
	}
	public void setNorm(int norm) {
		this.norm = norm;
	}
	public Float getPrice_discount() {
		return price_discount;
	}
	public void setPrice_discount(Float price_discount) {
		this.price_discount = price_discount;
	}
	public String getNorm_category() {
		return norm_category;
	}
	public void setNorm_category(String norm_category) {
		this.norm_category = norm_category;
	}
	
}
